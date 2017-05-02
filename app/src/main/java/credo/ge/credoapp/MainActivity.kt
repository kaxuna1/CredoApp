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


class MainActivity : AppCompatActivity() {

    internal var layout: LinearLayout?=null
    internal var button: Button?=null
    internal var familyBtn: Button?=null

    internal var buttonLoans: Button?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        layout = findViewById(R.id.mainlinear) as LinearLayout
        button = findViewById(R.id.button) as Button
        buttonLoans = findViewById(R.id.loans) as Button
        familyBtn = findViewById(R.id.familyTypes) as Button
        button!!.text="ახალი სესხი"
        StaticData.data.put(Currency::class.java.name,SugarRecord.listAll(Currency::class.java))
        StaticData.data.put(Person::class.java.name,SugarRecord.listAll(Person::class.java))
        button!!.setOnClickListener {
            val intent = Intent(this, DataFillActivity::class.java)
            intent.putExtra("class",Loan::class.java)
            intent.putExtra("autosave",true)
            startActivity(intent)
        }
        buttonLoans!!.setOnClickListener {
            val intent = Intent(this, data_list_activity::class.java)
            intent.putExtra("class",Loan::class.java)
            intent.putExtra("nameFieldMethodName","getName")
            startActivity(intent)
        }
        familyBtn!!.setOnClickListener {
            val intent = Intent(this, DataFillActivity::class.java)
            intent.putExtra("class", FamilyMemberType::class.java)
            intent.putExtra("autosave",false)
            startActivity(intent)
        }


    }
}
