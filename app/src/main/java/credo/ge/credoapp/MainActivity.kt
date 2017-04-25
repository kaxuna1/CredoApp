package credo.ge.credoapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout

import credo.ge.credoapp.anotations.ViewAnnotationParser
import credo.ge.credoapp.models.TestModel

class MainActivity : AppCompatActivity() {

    internal var layout: LinearLayout?=null
    internal var button: Button?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        layout = findViewById(R.id.mainlinear) as LinearLayout
        button = findViewById(R.id.button) as Button

        var bindObject=TestModel();

        try {
            ViewAnnotationParser().parse(TestModel::class.java, layout!!,bindObject)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        button!!.setOnClickListener {
            Log.d("kaxaaaa","${bindObject.name},${bindObject.surname}")
        }

    }
}
