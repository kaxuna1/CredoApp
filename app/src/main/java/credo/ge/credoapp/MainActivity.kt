package credo.ge.credoapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import com.orm.SugarRecord

import credo.ge.credoapp.anotations.ViewAnnotationParser
import com.orm.SugarRecord.listAll
import credo.ge.credoapp.models.*
import credo.ge.credoapp.views.CredoExtendActivity
import kotlinx.android.synthetic.main.activity_main.*




class MainActivity : CredoExtendActivity() {

    internal var layout: LinearLayout?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        layout = findViewById(R.id.mainlinear) as LinearLayout

        StaticData.data.put(Currency::class.java.name,SugarRecord.listAll(Currency::class.java))
        StaticData.data.put(Person::class.java.name,SugarRecord.listAll(Person::class.java))
        addLoanBtn!!.setOnClickListener {
            val intent = Intent(this, DataFillActivity::class.java)
            Loan().save()
            intent.putExtra("class",Loan::class.java)
            intent.putExtra("autosave",true)
            startActivity(intent)
        }
        loans!!.setOnClickListener {
            val intent = Intent(this, data_list_activity::class.java)
            intent.putExtra("class",Loan::class.java)
            intent.putExtra("nameFieldMethodName","getName")
            startActivity(intent)
        }
        persons!!.setOnClickListener {
            val intent = Intent(this, data_list_activity::class.java)
            intent.putExtra("class",Person::class.java)
            intent.putExtra("nameFieldMethodName","fullName")
            startActivity(intent)
        }
        buttonAddPerson.setOnClickListener {
            val intent = Intent(this, DataFillActivity::class.java)
            intent.putExtra("class", Person::class.java)
            intent.putExtra("autosave",true)
            startActivity(intent)
        }


    }
}
