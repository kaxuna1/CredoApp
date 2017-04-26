package credo.ge.credoapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import com.orm.SugarRecord

import credo.ge.credoapp.anotations.ViewAnnotationParser
import credo.ge.credoapp.models.TestModel
import com.orm.SugarRecord.listAll
import credo.ge.credoapp.models.Currency
import credo.ge.credoapp.models.Loan
import credo.ge.credoapp.models.Person


class MainActivity : AppCompatActivity() {

    internal var layout: LinearLayout?=null
    internal var button: Button?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        layout = findViewById(R.id.mainlinear) as LinearLayout
        button = findViewById(R.id.button) as Button
        button!!.text="სესხი"
        StaticData.data.put(Currency::class.java.name,SugarRecord.listAll(Currency::class.java))
        StaticData.data.put(Person::class.java.name,SugarRecord.listAll(Person::class.java))
        button!!.setOnClickListener {
            val intent = Intent(this, DataFillActivity::class.java)
            intent.putExtra("class",Loan::class.java)
            startActivity(intent)
        }


    }
}
