package credo.ge.credoapp

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.LinearLayout
import com.orm.SugarRecord

import credo.ge.credoapp.anotations.ViewAnnotationParser
import com.orm.SugarRecord.listAll
import credo.ge.credoapp.models.*
import credo.ge.credoapp.online.OnlineData
import credo.ge.credoapp.views.CredoExtendActivity
import kotlinx.android.synthetic.main.activity_main.*
import rx.functions.Action1


class MainActivity : CredoExtendActivity() {

    internal var layout: LinearLayout? = null
    private var progressDialog: ProgressDialog? = null

    internal var activity: CredoExtendActivity? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        layout = findViewById(R.id.mainlinear) as LinearLayout
        progressDialog = ProgressDialog(this)
        var activity = this


        makeDrawer();


        addLoanBtn!!.setOnClickListener {
            val intent = Intent(this, DataFillActivity::class.java)
            Loan().save()
            intent.putExtra("class", Loan::class.java)
            intent.putExtra("autosave", true)
            startActivity(intent)
        }
        loans!!.setOnClickListener {
            val intent = Intent(this, data_list_activity::class.java)
            intent.putExtra("class", Loan::class.java)
            intent.putExtra("nameFieldMethodName", "getName")
            startActivity(intent)
        }
        persons!!.setOnClickListener {
            val intent = Intent(this, data_list_activity::class.java)
            intent.putExtra("class", Person::class.java)
            intent.putExtra("nameFieldMethodName", "fullName")
            startActivity(intent)
        }
        buttonAddPerson.setOnClickListener {
            val intent = Intent(this, DataFillActivity::class.java)
            intent.putExtra("class", Person::class.java)
            intent.putExtra("autosave", true)
            startActivity(intent)
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.main, menu);
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.refresh -> {
                progressDialog!!.setMessage("მიმდინარეობს განახლება!")
                progressDialog!!.setCancelable(false)
                progressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
                progressDialog!!.show()
                OnlineData.syncData(StaticData.loginData!!.access_token, Action1 {

                    var ka=VilageCounsel.findAll(VilageCounsel::class.java)

                    Branch.deleteAll(Branch::class.java)
                    VilageCounsel.deleteAll(VilageCounsel::class.java)
                    Industry.deleteAll(Industry::class.java)
                    LoanOficer.deleteAll(LoanOficer::class.java)
                    Product.deleteAll(Product::class.java)
                    Purpose.deleteAll(Purpose::class.java)
                    Vilage.deleteAll(Vilage::class.java)
                    Dictionary.deleteAll(Dictionary::class.java)
                    var k = it

                    it.data.syncModel.branches.forEach {
                        Branch(it).save()
                    }
                    it.data.syncModel.consuls.forEach {
                        VilageCounsel(it).save()
                    }
                    it.data.syncModel.industries.forEach {
                        Industry(it).save()
                    }
                    it.data.syncModel.loanOfficers.forEach {
                        LoanOficer(it).save();
                    }
                    it.data.syncModel.products.forEach {
                        Product(it).save()
                    }
                    it.data.syncModel.purposes.forEach {
                        Purpose(it).save()
                    }
                    it.data.syncModel.villages.forEach {
                        Vilage(it).save()
                    }
                    it.data.syncModel.dictionaries.forEach {
                        Dictionary(it).save()
                    }



                    progressDialog!!.hide()
                    /*  it.data.syncModel.branches.forEach {
                          Log.d("logKaxa",it.name)
                      }*/
                })

            }
        }


        return true;
    }
}
