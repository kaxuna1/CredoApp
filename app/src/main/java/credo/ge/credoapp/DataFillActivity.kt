package credo.ge.credoapp

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import credo.ge.credoapp.anotations.ViewAnnotationParser
import credo.ge.credoapp.views.CredoExtendActivity
import kotlinx.android.synthetic.main.activity_data_fill.*
import android.support.design.widget.Snackbar
import android.util.Log
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton


class DataFillActivity : CredoExtendActivity() {


    override fun onPause() {
        super.onPause()
        if (updaterUUID != null) {
            if (!updaterUUID!!.isEmpty()) {
                val func = StaticData.comboBoxUpdateFunctions.get(updaterUUID!!) as () -> Unit
                func()
            }
        }
    }
    var parser: ViewAnnotationParser?=null
    var updaterUUID: String? = ""

    var autoSave: Boolean = false
    var savedNotification:Snackbar? = null;

    internal var layout: LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_fill)
        //layout = findViewById(R.id.linearFormPlace) as LinearLayout
        val extras = intent.extras
        val classname: Class<*> = extras.getSerializable("class") as Class<*>

        val id = extras.getLong("id")
        val joinId = extras.getLong("joinId")

        updaterUUID = extras.getString("updaterUUID")


        autoSave = extras.getBoolean("autosave")

        val hideSave = extras.getBoolean("hideSave")


        val ctor = classname.getConstructor()


        var bindObject = ctor.newInstance()
        if (id > 0L) {
            bindObject = classname.getMethod("getById", Long::class.java).invoke(null, id)
        }
        if (joinId > 0L) {
            val joinClassName: Class<*> = extras.getSerializable("joinClass") as Class<*>
            val joinFieldName: String = extras.getString("joinField")
            var joinObject = joinClassName.getMethod("getById", Long::class.java).invoke(null, joinId)
            classname.getField(joinFieldName).set(bindObject, joinObject)
        }
        /*    if (autoSave)
                classname.getMethod("save").invoke(bindObject)*/



        tabs.setBackgroundColor(resources.getColor(R.color.colorPrimary));
        tabs.setTextColor(getResources().getColor(R.color.white));
        tabs.setUnderlineColor(getResources().getColor(R.color.white));
        tabs.setIndicatorColor(getResources().getColor(R.color.white));


        parser = ViewAnnotationParser()
        parser!!.parse(classname, bindObject, View.OnClickListener {

            classname.getMethod("save").invoke(bindObject)
            if (updaterUUID != null) {
                val func = StaticData.comboBoxUpdateFunctions.get(updaterUUID!!) as () -> Unit
                func()
            }
            finish()
        }, "შენახვა", autoSave, supportFragmentManager, pager, tabs, !hideSave)


        backBtn.setOnClickListener {
            finish()
        }


    }

    override fun onBackPressed() {
        if(parser!!.dataModel!!){
            if(parser!!.checkRequaredFieldsForSave()){
                alert("გსურთ გამოხვიდეთ და შეინახოთ ცვლილებები"){
                    yesButton {
                        parser!!.saveFun()
                        finish()
                    }
                    noButton {
                        toast("დახურვის გაუქმება")
                    }
                }.show()
            }else{
                alert("საველდებულო ველები არაა შევსებული. გამოსვლის შემთხვევაში არ მოხდება ჩანაწერის შენახვა. გსურთ გაგრძელება?"){
                    yesButton {
                        finish()
                    }
                    noButton {

                    }
                }.show()
            }
        }else{
            finish()
        }

    }
}
