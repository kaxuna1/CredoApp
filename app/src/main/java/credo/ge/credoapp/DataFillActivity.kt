package credo.ge.credoapp

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import credo.ge.credoapp.anotations.ViewAnnotationParser
import credo.ge.credoapp.views.CredoExtendActivity
import kotlinx.android.synthetic.main.activity_data_fill.*
import android.content.Context.LOCATION_SERVICE
import android.location.LocationManager
import android.location.LocationListener
import android.util.Log
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices




class DataFillActivity : CredoExtendActivity() {




    internal var layout: LinearLayout?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_fill)



















//        layout = findViewById(R.id.linearFormPlace) as LinearLayout
        val extras = intent.extras
        val classname:Class<*> = extras.getSerializable("class") as Class<*>

        val id=extras.getLong("id")
        val joinId = extras.getLong("joinId")
        val updaterUUID=extras.getString("updaterUUID")

        val autoSave=extras.getBoolean("autosave")


        val ctor = classname.getConstructor()


        var bindObject = ctor.newInstance()
        if (id > 0L) {
            bindObject = classname.getMethod("getById",Long::class.java).invoke(null,id)
        }
        if(joinId>0L){
            val joinClassName:Class<*> = extras.getSerializable("joinClass") as Class<*>
            val joinFieldName:String = extras.getString("joinField")
            var joinObject = joinClassName.getMethod("getById",Long::class.java).invoke(null,joinId)
            classname.getField(joinFieldName).set(bindObject,joinObject)
        }
        if(autoSave)
        classname.getMethod("save").invoke(bindObject)


        tabs.setBackgroundColor(resources.getColor(R.color.colorPrimary));
        tabs.setTextColor(getResources().getColor(R.color.white));
        tabs.setUnderlineColor(getResources().getColor(R.color.white));
        tabs.setIndicatorColor(getResources().getColor(R.color.white));

        ViewAnnotationParser().parse(classname,bindObject, View.OnClickListener {


            classname.getMethod("save").invoke(bindObject)
            if(updaterUUID!=null){
                val func=StaticData.comboBoxUpdateFunctions.get(updaterUUID) as ()->Unit
                func()
            }
            finish()
        },"შენახვა",true,supportFragmentManager,pager,tabs);


        backBtn.setOnClickListener {
            finish()
        }


    }
}
