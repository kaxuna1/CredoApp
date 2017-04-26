package credo.ge.credoapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import credo.ge.credoapp.anotations.ViewAnnotationParser
import credo.ge.credoapp.models.Loan

class DataFillActivity : AppCompatActivity() {


    internal var layout: LinearLayout?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_fill)
        layout = findViewById(R.id.linearFormPlace) as LinearLayout
        val extras = intent.extras
        val classname:Class<*> = extras.getSerializable("class") as Class<*>
        val ctor = classname.getConstructor()
        val bindObject = ctor.newInstance()
        ViewAnnotationParser().parse(classname, layout!!,bindObject, View.OnClickListener {
            classname.getMethod("save").invoke(bindObject)
            finish()
        })

    }
}
