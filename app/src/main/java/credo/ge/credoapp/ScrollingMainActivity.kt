package credo.ge.credoapp

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.orm.SugarRecord
import credo.ge.credoapp.models.*
import credo.ge.credoapp.online.OnlineData
import credo.ge.credoapp.views.CredoExtendActivity
import kotlinx.android.synthetic.main.activity_scrolling_main.*
import kotlinx.android.synthetic.main.content_scrolling_main.*
import rx.functions.Action1
import android.content.Context.LOCATION_SERVICE
import android.location.Location
import android.location.LocationManager



class ScrollingMainActivity : CredoExtendActivity() {

    internal var layout: LinearLayout? = null
    private var progressDialog: ProgressDialog? = null

    internal var activity: CredoExtendActivity? = null

    protected var mGoogleApiClient: GoogleApiClient? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling_main)


        val utilLocation: Location? = null
        val manager = applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager


        val location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER)





        if (mGoogleApiClient == null) {
            mGoogleApiClient = GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(object: GoogleApiClient.ConnectionCallbacks {
                        override fun onConnected(connectionHint: Bundle?) {
                            val mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                                    mGoogleApiClient)
                            if (mLastLocation != null) {
                                Log.d("locationX",(mLastLocation.latitude.toString()));
                                Log.d("locationY",(mLastLocation.longitude.toString()));
                            }
                        }

                        override fun onConnectionSuspended(p0: Int) {

                        }

                    })
                    .addOnConnectionFailedListener(object: GoogleApiClient.OnConnectionFailedListener{
                        override fun onConnectionFailed(p0: ConnectionResult) {
                            Log.d("location","cantConnect")
                        }

                    })
                    .addApi(LocationServices.API)
                    .build()
        }


        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            syncData(view)
        }
        layout = findViewById(R.id.mainlinear) as LinearLayout
        progressDialog = ProgressDialog(this)
        var activity = this

        addLoanBtn!!.setOnClickListener {
            if (checkSync()) {
                Snackbar.make(buttonAutoCheck, "სინქრონიზაცია აუცილებელია", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
                return@setOnClickListener
            }
            val intent = Intent(this, DataFillActivity::class.java)
            intent.putExtra("class", Loan::class.java)
            intent.putExtra("autosave", true)
            startActivity(intent)
        }
        loans!!.setOnClickListener {
            if (checkSync()) {
                Snackbar.make(buttonAutoCheck, "სინქრონიზაცია აუცილებელია", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
                return@setOnClickListener
            }
            val intent = Intent(this, data_list_activity::class.java)
            intent.putExtra("class", Loan::class.java)
            intent.putExtra("nameFieldMethodName", "getName")
            startActivity(intent)
        }
        persons!!.setOnClickListener {
            if(checkSync()){
                Snackbar.make(buttonAutoCheck, "სინქრონიზაცია აუცილებელია", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
                return@setOnClickListener
            }
            val intent = Intent(this, data_list_activity::class.java)
            intent.putExtra("class", Person::class.java)
            intent.putExtra("nameFieldMethodName", "fullName")
            startActivity(intent)
        }
        buttonAddPerson.setOnClickListener {
            if (checkSync()) {
                Snackbar.make(buttonAutoCheck, "სინქრონიზაცია აუცილებელია", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
                return@setOnClickListener
            }
            val intent = Intent(this, DataFillActivity::class.java)
            intent.putExtra("class", Person::class.java)
            intent.putExtra("autosave", true)
            startActivity(intent)
        }

        buttonAutoCheck.setOnClickListener {


            if (checkSync()) {
                Snackbar.make(buttonAutoCheck, "სინქრონიზაცია აუცილებელია", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
                return@setOnClickListener
            }

            val intent = Intent(this, AutoCheckActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.trans_left_in,
                    R.anim.trans_left_out)

        }
        loansComplete!!.setOnClickListener {
            Snackbar.make(buttonAutoCheck, "კოორდინატები ${location.longitude}:${location.latitude}", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()

        }

    }

    fun checkSync(): Boolean {
        return !Branch.findAll(Branch::class.java).hasNext()
    }

    fun syncData(view: View) {
        progressDialog!!.setMessage("მიმდინარეობს განახლება!")
        progressDialog!!.setCancelable(false)
        progressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialog!!.show()
        OnlineData.syncData(StaticData.loginData!!.access_token, Action1 {

            var ka = VilageCounsel.findAll(VilageCounsel::class.java)

            Branch.deleteAll(Branch::class.java)
            VilageCounsel.deleteAll(VilageCounsel::class.java)
            Industry.deleteAll(Industry::class.java)
            LoanOficer.deleteAll(LoanOficer::class.java)
            Product.deleteAll(Product::class.java)
            Purpose.deleteAll(Purpose::class.java)
            Vilage.deleteAll(Vilage::class.java)
            Dictionary.deleteAll(Dictionary::class.java)
            var k = it
            var branches = ArrayList<Branch>();
            val consuls = ArrayList<VilageCounsel>();
            val industries = ArrayList<Industry>();
            val oficers = ArrayList<LoanOficer>();
            val products = ArrayList<Product>();
            val purposes = ArrayList<Purpose>();
            val vilages = ArrayList<Vilage>();
            val dictionaries = ArrayList<Dictionary>();

            it.data.syncModel.branches.forEach {
                branches.add(Branch(it))//.save()
            }
            it.data.syncModel.consuls.forEach {
                consuls.add(VilageCounsel(it))//.save()
            }
            it.data.syncModel.industries.forEach {
                industries.add(Industry(it))//.save()
            }
            it.data.syncModel.loanOfficers.forEach {
                oficers.add(LoanOficer(it))//.save();
            }
            it.data.syncModel.products.forEach {
                products.add(Product(it))//.save()
            }
            it.data.syncModel.purposes.forEach {
                purposes.add(Purpose(it))//.save()
            }
            it.data.syncModel.villages.forEach {
                vilages.add(Vilage(it))//.save()
            }
            it.data.syncModel.dictionaries.forEach {
                dictionaries.add(Dictionary(it))//.save()
            }


            SugarRecord.saveInTx(branches)
            SugarRecord.saveInTx(consuls)
            SugarRecord.saveInTx(industries)
            SugarRecord.saveInTx(oficers)
            SugarRecord.saveInTx(products)
            SugarRecord.saveInTx(purposes)
            SugarRecord.saveInTx(vilages)
            SugarRecord.saveInTx(dictionaries)


            progressDialog!!.hide()
            Snackbar.make(view, "სინქრონიზაცია დასრულდა წარმატებით!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            /*  it.data.syncModel.branches.forEach {
                  Log.d("logKaxa",it.name)
              }*/
        })
    }
}
