package credo.ge.credoapp.anotations

import android.content.Context
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.ViewGroup

import java.lang.reflect.*

import credo.ge.credoapp.models.TestModel
import android.text.Editable
import android.widget.*
import com.jaredrummler.materialspinner.MaterialSpinner
import com.orm.SugarRecord
import credo.ge.credoapp.models.Person
import credo.ge.credoapp.views.ListAdapters.ComboboxAdapter
import com.shehabic.droppy.DroppyMenuPopup
import com.shehabic.droppy.DroppyClickCallbackInterface
import com.shehabic.droppy.DroppyMenuItem
import android.content.Context.LAYOUT_INFLATER_SERVICE
import credo.ge.credoapp.MainActivity
import android.view.LayoutInflater
import credo.ge.credoapp.R
import android.widget.PopupWindow
import android.content.Intent
import credo.ge.credoapp.DataFillActivity
import credo.ge.credoapp.StaticData


/**
 * Created by vakhtanggelashvili on 4/25/17.
 */

class ViewAnnotationParser {
    fun parse(clazz: Class<*>, viewGorup: ViewGroup, bindObject: Any,onSave:View.OnClickListener?) {
        var inflater = LayoutInflater.from(viewGorup.context)
        val fields = clazz.fields
        for (field in fields) {
            if (field.isAnnotationPresent(TextFieldTypeViewAnotation::class.java)) {
                val annotation = field.getAnnotation<TextFieldTypeViewAnotation>(TextFieldTypeViewAnotation::class.java)
                val name = annotation.name
                val type = annotation.type
                val deffaultValue = annotation.deffaultValue
                val editText = EditText(viewGorup.context)
                editText.setText(deffaultValue)

                var obj = clazz.cast(bindObject)

                viewGorup.addView(editText)
                editText.addTextChangedListener(object : TextWatcher {

                    override fun afterTextChanged(s: Editable) {}

                    override fun beforeTextChanged(s: CharSequence, start: Int,
                                                   count: Int, after: Int) {
                    }

                    override fun onTextChanged(s: CharSequence, start: Int,
                                               before: Int, count: Int) {
                        if (s.length != 0) {
                            field.set(bindObject, s.toString())
                        }
                    }
                })
            }
            if (field.isAnnotationPresent(DateFieldTypeViewAnotation::class.java)) {
                val annotation = field.getAnnotation<TextFieldTypeViewAnotation>(TextFieldTypeViewAnotation::class.java)
                val name = annotation.name
                var datePicker = DatePicker(viewGorup.context)
                viewGorup.addView(datePicker)
            }
            if (field.isAnnotationPresent(ObjectFieldTypeViewAnotation::class.java)) {
                val annotation = field.getAnnotation<ObjectFieldTypeViewAnotation>(ObjectFieldTypeViewAnotation::class.java)
                val name = annotation.name
                val displayField = annotation.displayField
                val isMethod = annotation.isMethod
                val type = annotation.type
                val sqlData = annotation.sqlData
                val canAddToDb = annotation.canAddToDb
                if (type == "comboBox") {
                    var view = inflater.inflate(R.layout.combobox_with_button,viewGorup)
                    //viewGorup.addView(view)




                    val spinner = view.findViewById(R.id.spinner) as Spinner

                    val anchor = view.findViewById(R.id.buttonActions) as Button
                   val droppyBuilder = DroppyMenuPopup.Builder(viewGorup.context, anchor)

                    droppyBuilder.addMenuItem(DroppyMenuItem("add"))
                            .addMenuItem(DroppyMenuItem("edit"))
                            .addSeparator()

                    droppyBuilder.setOnClick { v, id ->
                        if (id.toInt()==0){
                            val intent = Intent(viewGorup.context, DataFillActivity::class.java)
                            intent.putExtra("class",field.type)
                            viewGorup.context.startActivity(intent)
                        }
                    }

                    val droppyMenu = droppyBuilder.build()

                    val dataToLoad = StaticData.data.get(field.type.name)!!
                    spinner.setAdapter(ComboboxAdapter(dataToLoad, viewGorup.context, displayField, isMethod, field.type))

                }


            }
        }
        if(onSave!=null){
            val saveBtn=Button(viewGorup.context)
            saveBtn.text="save"
            saveBtn.setOnClickListener(onSave)
            viewGorup.addView(saveBtn)
        }else{

        }
    }
}

private fun Field.getTypeForThis(): Any {
    return type;
}

