package credo.ge.credoapp

import android.Manifest
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.TargetApi
import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.app.LoaderManager.LoaderCallbacks
import android.app.ProgressDialog
import android.content.*

import android.database.Cursor
import android.net.Uri

import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo

import java.util.ArrayList

import android.content.pm.PackageManager
import android.hardware.fingerprint.FingerprintManager
import android.os.AsyncTask
import android.preference.PreferenceManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.andrognito.pinlockview.IndicatorDots
import com.andrognito.pinlockview.PinLockListener
import com.andrognito.pinlockview.PinLockView
import com.mattprecious.swirl.SwirlView
import com.multidots.fingerprintauth.AuthErrorCodes
import com.multidots.fingerprintauth.FingerPrintAuthCallback
import com.multidots.fingerprintauth.FingerPrintAuthHelper
import com.orm.SugarApp
import com.orm.SugarContext
import credo.ge.credoapp.models.OnlineDataModels.LoginData
import credo.ge.credoapp.models.Person
import credo.ge.credoapp.online.OnlineData
import kotlinx.android.synthetic.main.pin_layout.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import rx.functions.Action1

/**
 * A login screen that offers login via email/password.
 */


class LoginActivity : AppCompatActivity(), LoaderCallbacks<Cursor> {

/*
    val mAuthTask: UserLoginTask? = null;

    internal val KEY_ALIAS = "sitepoint";
    internal val KEYSTORE = "AndroidKeyStore";
    internal val PREFERENCES_KEY_EMAIL = "email";
    internal val PREFERENCES_KEY_PASS = "pass";
    internal val PREFERENCES_KEY_IV = "iv";

    var keyStore: KeyStore? = null;
    var generator: KeyGenerator? = null;
    var cipher: Cipher? = null
    var fingerprintManager: FingerprintManager? = null
    var keyguardManager: KeyguardManager? = null
    var cryptoObject: FingerprintManager.CryptoObject? = null


    val encrypting = false*/


    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private var progressDialog: ProgressDialog? = null
    // UI references.
    private var mEmailView: AutoCompleteTextView? = null
    private var mPasswordView: EditText? = null
    private var mProgressView: View? = null
    private var mLoginFormView: View? = null
    private var mPinLockView: PinLockView? = null
    private var indicatorDots: IndicatorDots? = null
    internal var pref: SharedPreferences? = null
    internal var sharedPreferences: SharedPreferences? = null
    internal var editor: SharedPreferences.Editor? = null
    var mFingerPrintAuthHelper: FingerPrintAuthHelper? = null
    //internal var swirlView:SwirlView?=null
    var sessionId = 0L

    private var loginData: LoginData? = null
    val WRITE_EXST = 0x3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var fingerCallback: FingerPrintAuthCallbackCredo? = null

        progressDialog = ProgressDialog(this)
        progressDialog!!.setMessage("მიმდინარეობს განახლება!")
        progressDialog!!.setCancelable(false)
        progressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)

        SugarContext.init(applicationContext)

        Person.listAll(Person::class.java)

        askPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, WRITE_EXST)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            fingerCallback = FingerPrintAuthCallbackCredo()
            mFingerPrintAuthHelper = FingerPrintAuthHelper.getHelper(this, fingerCallback!!);
        }
        // Set up the login form.
        pref = this.applicationContext.getSharedPreferences(StaticData.preferencesName, StaticData.preferencesMode)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = pref!!.edit();
        //editor!!.putLong("session",0)
        //editor!!.commit();
        sessionId = pref!!.getLong("session", 0)
        if (sessionId > 0) {

            loginData = LoginData.findById(LoginData::class.java, sessionId)
            setContentView(R.layout.pin_layout)
            indicatorDots = findViewById(R.id.indicator_dots) as IndicatorDots

            indicatorDots!!.setIndicatorType(IndicatorDots.IndicatorType.FILL_WITH_ANIMATION);
            ///indicatorDots!!.setBackgroundColor(R.color.material_grey_800)
            var logoutBtn = findViewById(R.id.logoutBtn) as TextView
            var pinChooseView = findViewById(R.id.choosePin) as TextView
            pinChooseView.visibility = View.GONE
            logoutBtn.visibility = View.VISIBLE

            logoutBtn.setOnClickListener {
                editor!!.putLong("session", 0)
                editor!!.commit()
                initLoginPage()
            }


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


                fingerCallback!!.activity = this
                fingerCallback!!.loginData = loginData

            }
            //swirlView = findViewById(R.id.swirlView) as SwirlView;

            //swirlView!!.setState(SwirlView.State.ON)
            mPinLockView = findViewById(R.id.pin_lock_view) as PinLockView
            mPinLockView!!.attachIndicatorDots(indicatorDots);
            mPinLockView!!.setPinLockListener(object : PinLockListener {
                override fun onComplete(pin: String) {
                    Log.d("kkaaxxaa", "Pin complete: " + pin)
                    if (pin == loginData!!.pin) {
                        StaticData.loggedIn = true;
                        StaticData.loginData = loginData
                        finish()
                    } else {
                        //swirlView!!.setState(SwirlView.State.ERROR)
                    }
                }

                override fun onEmpty() {
                    Log.d("kkaaxxaa", "Pin empty")
                }

                override fun onPinChange(pinLength: Int, intermediatePin: String) {
                    //swirlView!!.setState(SwirlView.State.ON)
                    Log.d("kkaaxxaa", "Pin changed, new length $pinLength with intermediate pin $intermediatePin")
                }
            })

        } else {
            initLoginPage()
        }


    }

    fun initLoginPage() {
        setContentView(R.layout.activity_login)
        mEmailView = findViewById(R.id.email) as AutoCompleteTextView
//        populateAutoComplete()

        mPasswordView = findViewById(R.id.password) as EditText
        mPasswordView!!.setOnEditorActionListener(TextView.OnEditorActionListener { textView, id, keyEvent ->
            if (id == R.id.login || id == EditorInfo.IME_NULL) {
                attemptLogin()
                return@OnEditorActionListener true
            }
            false
        })


        val mEmailSignInButton = findViewById(R.id.email_sign_in_button) as Button
        mEmailSignInButton.setOnClickListener { attemptLogin() }

        mLoginFormView = findViewById(R.id.login_form)
        mProgressView = findViewById(R.id.login_progress)
    }


    /**
     * Callback received when a permissions request has been completed.
     */


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private fun attemptLogin() {

        var view = this.getCurrentFocus();
        if (view != null) {
            var imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager;
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }


        // Reset errors.
        mEmailView!!.error = null
        mPasswordView!!.error = null

        // Store values at the time of the login attempt.
        val email = mEmailView!!.text.toString()
        val password = mPasswordView!!.text.toString()

        var cancel = false
        var focusView: View? = null

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView!!.error = getString(R.string.error_invalid_password)
            focusView = mPasswordView
            cancel = true
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView!!.error = getString(R.string.error_field_required)
            focusView = mEmailView
            cancel = true
        }
        if (TextUtils.isEmpty(password)) {
            mPasswordView!!.error = getString(R.string.error_field_required)
            focusView = mPasswordView
            cancel = true
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView!!.requestFocus()
        } else {

            showProgress(true)
            try {
                OnlineData.login(email, password, Action1 {

                    var result = it;
                    if (result != null) {
                        if (result.data.expires_in != 0L) {


                            OnlineData.syncUserName(result.data.userId,result.data.access_token, Action1 {
                                var name = it.data.errorMessage
                                var position = it.data.position

                                setContentView(R.layout.pin_layout)
                                indicatorDots = findViewById(R.id.indicator_dots) as IndicatorDots
                                indicatorDots!!.setIndicatorType(IndicatorDots.IndicatorType.FILL_WITH_ANIMATION);

                                mPinLockView = findViewById(R.id.pin_lock_view) as PinLockView
                                mPinLockView!!.attachIndicatorDots(indicatorDots);
                                mPinLockView!!.setPinLockListener(object : PinLockListener {
                                    override fun onComplete(pin: String) {

                                        result.data.pin = pin
                                        result.data.name = name
                                        result.data.position = position
                                        result.data.save()
                                        editor!!.putLong("session", result.data.id)
                                        editor!!.commit()
                                        StaticData.loggedIn = true
                                        StaticData.loginData = result.data
                                        finish()
                                    }

                                    override fun onEmpty() {
                                        Log.d("kkaaxxaa", "Pin empty")
                                    }

                                    override fun onPinChange(pinLength: Int, intermediatePin: String) {
                                        Log.d("kkaaxxaa", "Pin changed, new length $pinLength with intermediate pin $intermediatePin")
                                    }
                                })


                            })





                            //finish()
                        } else {
                            showProgress(false)
                            toast("მომხმარებელი ან პაროლი არ არის სწორი!")
                        }

                    } else {
                        showProgress(false)

                    }
                })
            } catch (e: Exception) {
                e.printStackTrace()

            }

        }
    }

    private fun isPasswordValid(password: String): Boolean {
        //TODO: Replace this with your own logic
        return password.length > 1
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private fun showProgress(show: Boolean) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            val shortAnimTime = resources.getInteger(android.R.integer.config_shortAnimTime)

            mLoginFormView!!.visibility = if (show) View.GONE else View.VISIBLE
            mLoginFormView!!.animate().setDuration(shortAnimTime.toLong()).alpha(
                    (if (show) 0 else 1).toFloat()).setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    mLoginFormView!!.visibility = if (show) View.GONE else View.VISIBLE
                }
            })

            mProgressView!!.visibility = if (show) View.VISIBLE else View.GONE
            mProgressView!!.animate().setDuration(shortAnimTime.toLong()).alpha(
                    (if (show) 1 else 0).toFloat()).setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    mProgressView!!.visibility = if (show) View.VISIBLE else View.GONE
                }
            })
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView!!.visibility = if (show) View.VISIBLE else View.GONE
            mLoginFormView!!.visibility = if (show) View.GONE else View.VISIBLE
        }
    }

    override fun onCreateLoader(i: Int, bundle: Bundle): Loader<Cursor> {
        return CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE + " = ?", arrayOf(ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE),

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC")
    }

    override fun onLoadFinished(cursorLoader: Loader<Cursor>, cursor: Cursor) {
        val emails = ArrayList<String>()
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS))
            cursor.moveToNext()
        }

        addEmailsToAutoComplete(emails)
    }

    override fun onLoaderReset(cursorLoader: Loader<Cursor>) {

    }

    private fun addEmailsToAutoComplete(emailAddressCollection: List<String>) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        val adapter = ArrayAdapter(this@LoginActivity,
                android.R.layout.simple_dropdown_item_1line, emailAddressCollection)

        mEmailView!!.setAdapter(adapter)
    }


    private interface ProfileQuery {
        companion object {
            val PROJECTION = arrayOf(ContactsContract.CommonDataKinds.Email.ADDRESS, ContactsContract.CommonDataKinds.Email.IS_PRIMARY)

            val ADDRESS = 0
            val IS_PRIMARY = 1
        }
    }

    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && sessionId > 0) {
            mFingerPrintAuthHelper!!.startAuth()
        };
    }

    override fun onPause() {
        super.onPause()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mFingerPrintAuthHelper!!.stopAuth();
        }
    }

    override fun onBackPressed() {
        val startMain = Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME)
        startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startMain.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        startActivity(startMain)
    }


    public class FingerPrintAuthCallbackCredo() : FingerPrintAuthCallback {

        public var loginData: LoginData? = null

        public var activity: Activity? = null


        override public fun onNoFingerPrintHardwareFound() {
            //Device does not have finger print scanner.
        }

        override
        public fun onNoFingerPrintRegistered() {
            //There are no finger prints registered on this device.
        }


        override
        public fun onBelowMarshmallow() {
            //Device running below API 23 version of android that does not support finger print authentication.
        }

        override
        public fun onAuthSuccess(cryptoObject: FingerprintManager.CryptoObject) {
            //Authentication sucessful.
            StaticData.loggedIn = true;
            StaticData.loginData = loginData
            activity!!.finish()
            //activity!!.swirlView.setState(SwirlView.State.ON)
        }

        override
        public fun onAuthFailed(errorCode: Int, errorMessage: String?) {
            when (errorCode) {
            //Parse the error code for recoverable/non recoverable error.
                AuthErrorCodes.CANNOT_RECOGNIZE_ERROR -> {
                    Log.d("Finger", "Error 1")
                    //activity!!.swirlView.setState(SwirlView.State.ERROR)
                }
                AuthErrorCodes.NON_RECOVERABLE_ERROR -> {
                    Log.d("Finger", "Error 2")
                    //activity!!.swirlView.setState(SwirlView.State.ERROR)
                }
            //This is not recoverable error. Try other options for user authentication. like pin, password.
                AuthErrorCodes.RECOVERABLE_ERROR -> {
                    Log.d("Finger", "Error 3")
                    //activity!!.swirlView.setState(SwirlView.State.ERROR)
                }
            //Any recoverable error. Display message to the user.
            }
        }
    }


    fun askPermission(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf<String>(permission), requestCode);

        } else {
            Toast.makeText(this, "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
        }
    }


    /*@SuppressLint("NewApi") fun testFingerPrintSettings(): Boolean {
        print("Testing Fingerprint Settings");

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            print("This Android version does not support fingerprint authentication.");
            return false;
        }

        keyguardManager =  getSystemService (KEYGUARD_SERVICE) as KeyguardManager
        fingerprintManager =  getSystemService (FINGERPRINT_SERVICE) as FingerprintManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (!keyguardManager!!.isKeyguardSecure()) {
                print("User hasn't enabled Lock Screen");
                return false;
            }
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            print("User hasn't granted permission to use Fingerprint");
            return false;
        }

        if (!fingerprintManager!!.hasEnrolledFingerprints()) {
            print("User hasn't registered any fingerprints");
            return false;
        }

        print("Fingerprint authentication is set.\n");

        return true;

    }

    private fun usersRegistered(): Boolean {
        if (pref!!.getString(PREFERENCES_KEY_EMAIL, null) == null) {
            print("No user is registered")
            return false
        }

        return true
    }
    @TargetApi(Build.VERSION_CODES.M)
    class FingerprintHelper : FingerprintManager.AuthenticationCallback() {

    }

    interface FingerprintHelperListener{
        public fun authenticationFailed( error:String);
        public fun authenticationSucceeded( result:FingerprintManager.AuthenticationResult);

    }*/


}

