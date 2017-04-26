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
        val id=extras.getLong("id")
        val updaterUUID=extras.getString("updaterUUID")


        val ctor = classname.getConstructor()


        var bindObject = ctor.newInstance()
        if (id > 0L) {
            bindObject = classname.getMethod("getById",Long::class.java).invoke(null,id)
        }
        ViewAnnotationParser().parse(classname, layout!!,bindObject, View.OnClickListener {
            classname.getMethod("save").invoke(bindObject)
            if(updaterUUID!=null){
                val func=StaticData.comboBoxUpdateFunctions.get(updaterUUID) as ()->Unit
                func()
            }
            finish()
        })

    }
}
