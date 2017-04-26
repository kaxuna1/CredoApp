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
import java.util.*


/**
 * Created by vakhtanggelashvili on 4/25/17.
 */

class ViewAnnotationParser {
    fun parse(clazz: Class<*>, viewGorup: ViewGroup, bindObject: Any, onSave: View.OnClickListener?) {
        var inflater = LayoutInflater.from(viewGorup.context)
        val fields = clazz.fields
        for (field in fields) {
            if (field.isAnnotationPresent(TextFieldTypeViewAnotation::class.java)) {
                val annotation = field.getAnnotation<TextFieldTypeViewAnotation>(TextFieldTypeViewAnotation::class.java)
                val name = annotation.name
                val type = annotation.type
                val deffaultValue = annotation.deffaultValue

                val view = inflater.inflate(R.layout.text_field, null)

                val editText = view.findViewById(R.id.editor) as EditText
                val label = view.findViewById(R.id.label) as TextView
                label.text=name

                val fieldValue = field.get(bindObject)
                var valueToSet = deffaultValue;
                if (fieldValue != null)
                    valueToSet = fieldValue as String


                editText.setText(valueToSet)

                var obj = clazz.cast(bindObject)

                viewGorup.addView(view)
                editText.addTextChangedListener(object : TextWatcher {

                    override fun afterTextChanged(s: Editable) {}

                    override fun beforeTextChanged(s: CharSequence, start: Int,
                                                   count: Int, after: Int) {
                    }

                    override fun onTextChanged(s: CharSequence, start: Int,
                                               before: Int, count: Int) {

                        field.set(bindObject, s.toString())

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
                    var view = inflater.inflate(R.layout.combobox_with_button, null)
                    //

                    val spinner = view.findViewById(R.id.spinner) as Spinner
                    val updaterUUID = UUID.randomUUID().toString()
                    var adapter: ComboboxAdapter? = null
                    val func = fun() {
                        val method = field.type.getMethod("getData")
                        val dataToLoad = method.invoke(null) as List<Any>
                        val fieldValue = field.get(bindObject)






                        adapter = ComboboxAdapter(dataToLoad, viewGorup.context, displayField, isMethod, field.type)
                        spinner.setAdapter(adapter)

                        if(fieldValue!=null){
                            var objectInList = dataToLoad.find {
                                f->(
                                    field.type
                                            .getMethod("getId")

                                            .invoke(f) as Long) ==
                                    (field.type.getMethod("getId").invoke(fieldValue) as Long)
                            }
                            var indexOfObject=dataToLoad.indexOf(objectInList)
                            if (indexOfObject>=0){
                                spinner.setSelection(indexOfObject)
                            }
                        }
                        spinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                                field.set(bindObject, adapter!!.dataList.get(pos))
                            }

                            override fun onNothingSelected(parent: AdapterView<out Adapter>?) {

                            }
                        }
                    }
                    func()
                    StaticData.comboBoxUpdateFunctions.put(updaterUUID, func)

                    val anchor = view.findViewById(R.id.buttonActions) as Button
                    val label = view.findViewById(R.id.label) as TextView
                    label.text = "${name}:"
                    val droppyBuilder = DroppyMenuPopup.Builder(viewGorup.context, anchor)

                    droppyBuilder.addMenuItem(DroppyMenuItem("add"))
                            .addMenuItem(DroppyMenuItem("edit"))
                            .addMenuItem(DroppyMenuItem("delete"))
                            .addSeparator()


                    droppyBuilder.setOnClick { v, id ->
                        if (id == 0) {
                            val intent = Intent(viewGorup.context, DataFillActivity::class.java)
                            intent.putExtra("class", field.type)
                            intent.putExtra("updaterUUID", updaterUUID)
                            viewGorup.context.startActivity(intent)
                        }
                        if (id == 1) {
                            val intent = Intent(viewGorup.context, DataFillActivity::class.java)
                            intent.putExtra("class", field.type)
                            intent.putExtra("updaterUUID", updaterUUID)

                            var currentId = field.type.getMethod("getId").invoke(adapter!!.dataList.get(spinner.selectedItemPosition)) as Long
                            intent.putExtra("id", currentId)
                            viewGorup.context.startActivity(intent)
                        }
                        if (id == 2) {
                            field.type.getMethod("delete").invoke(adapter!!.dataList.get(spinner.selectedItemPosition))
                            func()
                        }
                    }
                    val droppyMenu = droppyBuilder.build()

                    viewGorup.addView(view)
                }


            }
        }
        if (onSave != null) {
            val saveBtn = Button(viewGorup.context)
            saveBtn.text = "save"
            saveBtn.setOnClickListener(onSave)
            viewGorup.addView(saveBtn)
        } else {

        }
    }
}

private fun Field.getTypeForThis(): Any {
    return type;
}

