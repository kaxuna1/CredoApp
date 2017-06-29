package credo.ge.credoapp

import android.Manifest
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
import credo.ge.credoapp.models.ViewModels.LoansViewer
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.support.v4.app.ActivityCompat
import android.content.DialogInterface
import android.R.string.ok
import android.R.string.yes
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.media.Image
import android.preference.PreferenceManager
import android.support.v4.content.ContextCompat
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetSequence
import com.getkeepsafe.taptargetview.TapTargetView
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.rey.material.app.BottomSheetDialog
import credo.ge.credoapp.views.SingleSectionHoverMenuService
import lecho.lib.hellocharts.model.Line
import lecho.lib.hellocharts.model.LineChartData
import lecho.lib.hellocharts.model.PointValue
import lecho.lib.hellocharts.view.LineChartView
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick


class ScrollingMainActivity : CredoExtendActivity() {
    val LOCATION = 0x1
    val WRITE_EXST = 0x3
    val READ_EXST = 0x4
    internal var layout: LinearLayout? = null
    private var progressDialog: ProgressDialog? = null
    var drawable: TextDrawable? = null
    internal var activity: CredoExtendActivity? = null

    protected var mGoogleApiClient: GoogleApiClient? = null


    var instruction = true;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling_main)





        askPermission(Manifest.permission.ACCESS_FINE_LOCATION, LOCATION)
        /*   askPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, WRITE_EXST)
           askPermission(Manifest.permission.READ_EXTERNAL_STORAGE, READ_EXST)
   */
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            val utilLocation: Location? = null
            val manager: LocationManager
            manager = applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        }







        if (mGoogleApiClient == null) {
            mGoogleApiClient = GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(object : GoogleApiClient.ConnectionCallbacks {
                        override fun onConnected(connectionHint: Bundle?) {
                            val mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                                    mGoogleApiClient)
                            if (mLastLocation != null) {
                                Log.d("locationX", (mLastLocation.latitude.toString()));
                                Log.d("locationY", (mLastLocation.longitude.toString()));
                            }
                        }

                        override fun onConnectionSuspended(p0: Int) {

                        }

                    })
                    .addOnConnectionFailedListener(object : GoogleApiClient.OnConnectionFailedListener {
                        override fun onConnectionFailed(p0: ConnectionResult) {
                            Log.d("location", "cantConnect")
                        }

                    })
                    .addApi(LocationServices.API)
                    .build()
        }


        //layout = findViewById(R.id.mainlinear) as LinearLayout
        progressDialog = ProgressDialog(this)
        var activity = this

        addloan!!.setOnClickListener {
            if (checkSync()) {
                Snackbar.make(buttonAutoCheck2, "სინქრონიზაცია აუცილებელია", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
                return@setOnClickListener
            }
            val intent = Intent(this, DataFillActivity::class.java)
            intent.putExtra("class", Loan::class.java)
            intent.putExtra("autosave", true)
            startActivity(intent)
        }
        addloan.setCustomTextFont("fonts/font1.otf")
        loans.setCustomTextFont("fonts/font1.otf")
        loans!!.setOnClickListener {
            if (checkSync()) {
                Snackbar.make(buttonAutoCheck2, "სინქრონიზაცია აუცილებელია", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
                return@setOnClickListener
            }
            val intent = Intent(this, data_list_activity::class.java)
            intent.putExtra("class", Loan::class.java)
            intent.putExtra("nameFieldMethodName", "getName")
            startActivity(intent)
        }
        clients2.setCustomTextFont("fonts/font1.otf")
        clients2!!.setOnClickListener {
            if (checkSync()) {
                Snackbar.make(buttonAutoCheck2, "სინქრონიზაცია აუცილებელია", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
                return@setOnClickListener
            }
            val intent = Intent(this, data_list_activity::class.java)
            intent.putExtra("class", Person::class.java)
            intent.putExtra("nameFieldMethodName", "fullName")
            startActivity(intent)
        }
        addClient.setCustomTextFont("fonts/font1.otf")
        addClient.setOnClickListener {
            if (checkSync()) {
                Snackbar.make(buttonAutoCheck2, "სინქრონიზაცია აუცილებელია", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
                return@setOnClickListener
            }
            val intent = Intent(this, DataFillActivity::class.java)
            intent.putExtra("class", Person::class.java)
            intent.putExtra("autosave", true)
            startActivity(intent)
        }
        buttonAutoCheck2.setCustomTextFont("fonts/font1.otf")
        buttonAutoCheck2.setOnClickListener {


            if (checkSync()) {
                Snackbar.make(buttonAutoCheck2, "სინქრონიზაცია აუცილებელია", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
                return@setOnClickListener
            }

            val intent = Intent(this, AutoCheckActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.trans_left_in,
                    R.anim.trans_left_out)

        }
        loans.setCustomTextFont("fonts/font1.otf")
        loans!!.setOnClickListener {
            /* Snackbar.make(buttonAutoCheck, "კოორდინატები ${location.longitude}:${location.latitude}", Snackbar.LENGTH_LONG)
                     .setAction("Action", null).show()*/


            /*           val intent = Intent(this, DataFillActivity::class.java)
                       intent.putExtra("class", LoansViewer::class.java)
                       intent.putExtra("autosave", false)
                       startActivity(intent)*/

            val intent = Intent(this, DataFillActivity::class.java)
            intent.putExtra("class", LoansViewer::class.java)
            intent.putExtra("autosave", false)
            intent.putExtra("hideSave", true)
            startActivity(intent)

        }
        files.setCustomTextFont("fonts/font1.otf")
        files.setOnClickListener {

        }


        var refresh = findViewById(R.id.refresh) as ImageView


        refresh.setOnClickListener {
            alert("სინქრონიზაციის შემთხვევაში წაიშლება ძველი ჩანაწერები. გსურთ გაგრძელება? ") {

                yesButton {
                    syncData(refresh)
                }
                noButton {

                }
            }.show()
        }
        nameImage.setOnClickListener {
            val countries = listOf("გამოსვლა", "დახურვა")
            selector("აირჩიეთ მოქმედება", countries) { dialogInterface, i ->
                if (i == 0) {
                    var pref = this.applicationContext.getSharedPreferences(StaticData.preferencesName, StaticData.preferencesMode)
                    val editor = pref!!.edit();
                    editor!!.putLong("session", 0)
                    editor!!.commit()
                    startActivity(intentFor<LoginActivity>().singleTop())

                }
            }
        }


    }

    fun tapTarget() {
        if (StaticData.loginData!!.isTutorial) {
            StaticData.loginData!!.isTutorial = false;
            StaticData.loginData!!.save()
            TapTargetSequence(this).targets(

                    TapTarget.forView(findViewById(R.id.refresh), "მუშაობის დასაწყებათ საჭიროა სინქრონიზაცია",
                            "სინქრონიზაციისთვის გჭირდებათ ინტერნეტთან კავშირი, გთხოვთ დააჭიროთ მითითებულ ღილაკს.")
                            .outerCircleColor(R.color.colorPrimary)
                            .outerCircleAlpha(0.96f)
                            .targetCircleColor(R.color.white)
                            .titleTextSize(20)
                            .titleTextColor(R.color.white)
                            .descriptionTextSize(13)
                            .descriptionTextColor(R.color.white)
                            .textColor(R.color.white)
                            .textTypeface(Typeface.SANS_SERIF)
                            .dimColor(R.color.material_blue_grey_950)
                            .drawShadow(true)
                            .cancelable(false)
                            .tintTarget(true)
                            .transparentTarget(false)
                            .targetRadius(60),
                    TapTarget.forView(findViewById(R.id.nameImage), "სისტემიდან გამოსასვლელად დააკლიკეთ პროფილის ღილაკს",
                            "")
                            .outerCircleColor(R.color.colorPrimary)
                            .outerCircleAlpha(0.96f)
                            .targetCircleColor(R.color.white)
                            .titleTextSize(20)
                            .titleTextColor(R.color.white)
                            .descriptionTextSize(13)
                            .descriptionTextColor(R.color.white)
                            .textColor(R.color.white)
                            .textTypeface(Typeface.SANS_SERIF)
                            .dimColor(R.color.material_blue_grey_950)
                            .drawShadow(true)
                            .cancelable(false)
                            .tintTarget(false)
                            .transparentTarget(true)
                            .targetRadius(60)).listener(object : TapTargetSequence.Listener {
                override fun onSequenceCanceled(lastTarget: TapTarget?) {

                }

                override fun onSequenceFinish() {
                    refresh.callOnClick()
                }

                override fun onSequenceStep(lastTarget: TapTarget?, targetClicked: Boolean) {

                }
            }).start()
        }

    }

    fun checkSync(): Boolean {
        return !Branch.findAll(Branch::class.java).hasNext()
    }

    fun syncData(view: View) {

        if (StaticData.isNetworkAvailable(this.applicationContext)) {
            progressDialog!!.setMessage("მიმდინარეობს განახლება!")
            progressDialog!!.setCancelable(false)
            progressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
            progressDialog!!.show()
            try {
                OnlineData.syncData(StaticData.loginData!!.access_token, Action1 {


                    if (it!=null){
                        var ka = VilageCounsel.findAll(VilageCounsel::class.java)

                        Branch.deleteAll(Branch::class.java)
                        VilageCounsel.deleteAll(VilageCounsel::class.java)
                        Industry.deleteAll(Industry::class.java)
                        LoanOficer.deleteAll(LoanOficer::class.java)
                        Product.deleteAll(Product::class.java)
                        Purpose.deleteAll(Purpose::class.java)
                        PurposeType.deleteAll(Purpose::class.java)
                        Vilage.deleteAll(Vilage::class.java)
                        Dictionary.deleteAll(Dictionary::class.java)
                        Loan.deleteAll(Loan::class.java)
                        Person.deleteAll(Person::class.java)
                        FamilyPerson.deleteAll(FamilyPerson::class.java)


                        var k = it
                        var branches = ArrayList<Branch>();
                        val consuls = ArrayList<VilageCounsel>();
                        val industries = ArrayList<Industry>();
                        val oficers = ArrayList<LoanOficer>();
                        val products = ArrayList<Product>();
                        val purposes = ArrayList<Purpose>();
                        val purposesTypes = ArrayList<PurposeType>();
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
                        it.data.syncModel.purposesTypes.forEach {
                            purposesTypes.add(PurposeType(it))//.save()
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
                        SugarRecord.saveInTx(purposesTypes)
                        SugarRecord.saveInTx(vilages)
                        SugarRecord.saveInTx(dictionaries)


                        progressDialog!!.hide()
                        Snackbar.make(view, "სინქრონიზაცია დასრულდა წარმატებით!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show()


                    }else{
                        progressDialog!!.hide()
                        Snackbar.make(view, "სინქრონიზაციის დროს მოხდა შეცდომა!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show()
                    }


                    /*  it.data.syncModel.branches.forEach {
                          Log.d("logKaxa",it.name)
                      }*/
                })
            } catch (e: Exception) {
                progressDialog!!.hide()
                Snackbar.make(view, "მოხდა შეცდომა", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
            }
        } else {
            Snackbar.make(view, "სინქრონიზაციისთვის საჭიროა ინტერნეტთან წვდომა", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

    }

    fun askPermission(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf<String>(permission), requestCode);

        } else {
            Toast.makeText(this, "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
        }
    }

    override fun onResume() {
        super.onResume()
        val font1 = Typeface.createFromAsset(applicationContext.getAssets(), "fonts/font1.ttf");
        var firstName = "";
        var lastName = "";
        if (StaticData.loginData != null) {
            val nameStrings = StaticData.loginData!!.name.split(" ")
            if (nameStrings.size == 2) {
                firstName = nameStrings[0].substring(0, 1)
                lastName = nameStrings[1].substring(0, 1)
            } else {
                firstName = StaticData.loginData!!.name.substring(0, 1)
            }
            updateCurrencies()
            val generator = ColorGenerator.MATERIAL;
            val color1 = generator.getRandomColor()

            drawable = TextDrawable.builder()
                    .beginConfig()
                    .textColor(Color.WHITE)
                    .useFont(font1)
                    .bold()
                    .toUpperCase()
                    .withBorder(12) /* thickness in px */
                    .endConfig()
                    .buildRound("${firstName}${lastName}", resources.getColor(R.color.colorAccent))
            nameImage.setImageDrawable(drawable)
            tapTarget();
        } else {

        }


    }

    fun drawCurrency() {
        currencyPlace.removeAllViews()

        var data = CurrencyData.listAll(CurrencyData::class.java)
        if (data.size > 0) {
            arrayOf("USD", "EUR", "RUB").forEach {
                var value = it
                var item = data.first { p -> p.code == value }
                var view = layoutInflater.inflate(R.layout.currencycard, null)
                var image = view.findViewById(R.id.flag) as ImageView
                var currencyText = view.findViewById(R.id.currency) as TextView

                var drawable = 0
                if (value == "USD")
                    drawable = R.drawable.usa
                if (value == "EUR")
                    drawable = R.drawable.euro
                if (value == "RUB")
                    drawable = R.drawable.rus
                image.setImageDrawable(getResources().getDrawable(drawable))
                currencyText.text = "${item.rate}"
                var chart = view.findViewById(R.id.chart) as LineChartView

                var values = ArrayList<PointValue>();

                var i = 0f
                data.filter { p -> p.code == value }.forEach {
                    values.add(PointValue(i, it.rate))
                    i++;
                }
                val generator = ColorGenerator.MATERIAL;
                val color1 = generator.getRandomColor()


                var line = Line(values).setColor(color1).setCubic(true).setFilled(true).setHasPoints(false).setHasLabels(true);
                var lines = ArrayList<Line>();
                lines.add(line);
                var chartData = LineChartData();
                chartData.setLines(lines);
                chart.setLineChartData(chartData);

                currencyPlace.addView(view);
            }
        } else {
            arrayOf("USD", "EUR", "RUB").forEach {
                var value = it

                var view = layoutInflater.inflate(R.layout.currencycard, null)
                var image = view.findViewById(R.id.flag) as ImageView
                var currencyText = view.findViewById(R.id.currency) as TextView

                var drawable = 0
                if (value == "USD")
                    drawable = R.drawable.usa
                if (value == "EUR")
                    drawable = R.drawable.euro
                if (value == "RUB")
                    drawable = R.drawable.rus
                image.setImageDrawable(getResources().getDrawable(drawable))
                currencyText.text = "არ მოიძებნა"
                var chart = view.findViewById(R.id.chart) as LineChartView

                var values = ArrayList<PointValue>();

                var i = 0f

                val generator = ColorGenerator.MATERIAL;
                val color1 = generator.getRandomColor()


                var line = Line(values).setColor(color1).setCubic(true).setFilled(true).setHasPoints(false).setHasLabels(true);
                var lines = ArrayList<Line>();
                lines.add(line);
                var chartData = LineChartData();
                chartData.setLines(lines);
                chart.setLineChartData(chartData);

                currencyPlace.addView(view);

                view.onClick {
                    var mDialog = BottomSheetDialog(view.context);

                    mDialog.contentView(verticalLayout {
                        val name = editText()
                        button("Say Hello") {
                            onClick { toast("Hello, ${name.text}!") }
                        }
                    })
                            .heightParam(ViewGroup.LayoutParams.WRAP_CONTENT)
                            .inDuration(500)
                            .cancelable(true)
                            .show();
                }

            }
        }


    }

    fun updateCurrencies() {
        try{
            if (StaticData.isInternetAvailable()) {
                OnlineData.syncCurrencies(Action1 {
                    if(it!=null){
                        CurrencyData.deleteAll(CurrencyData::class.java)
                        CurrencyData.saveInTx(it.data.currencyData);
                    }
                    drawCurrency()
                })
            } else {
                drawCurrency()
            }
        }catch (e:Exception){
            e.printStackTrace();
            toast("კურსების განახლების დროს მოხდა შეცდომა!")
        }


    }

}
