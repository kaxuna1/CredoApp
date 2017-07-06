package credo.ge.credoapp

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import credo.ge.credoapp.anotations.ViewAnnotationParser
import credo.ge.credoapp.views.CredoExtendActivity
import kotlinx.android.synthetic.main.activity_data_fill.*
import android.support.design.widget.Snackbar
import android.widget.EditText
import org.jetbrains.anko.*


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

    var parser: ViewAnnotationParser? = null
    var updaterUUID: String? = ""

    var autoSave: Boolean = false
    var savedNotification: Snackbar? = null;

    internal var layout: LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_fill)
        //layout = findViewById(R.id.linearFormPlace) as LinearLayout
        val font1 = Typeface.createFromAsset(pager.context.getAssets(), "fonts/font1.ttf");

        mainView2.applyRecursively { view ->
            when (view) {
                is EditText -> view.typeface = font1
            }
        }

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


        val dialog = indeterminateProgressDialog(message = "გთხოვთ დაიცადოთ", title = "ჩატვირთვა")

        parser = ViewAnnotationParser()
        parser!!.parse(classname, bindObject, View.OnClickListener {

            if (parser!!.checkRequaredFieldsForSave()) {

                parser!!.saveFun()
                parser!!.showSavedNotif()
                finish()
            }else{
                parser!!.showNotFilledNotification()
            }

            if (updaterUUID != null) {
                val func = StaticData.comboBoxUpdateFunctions.get(updaterUUID!!) as () -> Unit
                func()
            }
        }, "შენახვა", autoSave, supportFragmentManager, pager, tabs, !hideSave, this,dialog)


        backBtn.setOnClickListener {
            onBackPressed()
        }


    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        parser!!.listForActivityResult.forEach {
            it(requestCode,resultCode,data!!)
        }
    }

    override fun onBackPressed() {
        if (parser!!.dataModel!!) {
            if (parser!!.checkRequaredFieldsForSave()) {
                alert("გსურთ გამოხვიდეთ და შეინახოთ ცვლილებები") {
                    yesButton {
                        parser!!.saveFun()
                        finish()
                    }
                    noButton {
                        toast("დახურვის გაუქმება")
                    }
                }.show()
            } else {
                alert("საველდებულო ველები არაა შევსებული. გამოსვლის შემთხვევაში არ მოხდება ჩანაწერის შენახვა. გსურთ გაგრძელება?") {
                    yesButton {
                        finish()
                    }
                    noButton {

                    }
                }.show()
            }
        } else {
            finish()
        }

    }
}
