package credo.ge.credoapp.anotations

import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup

import java.lang.reflect.*

import android.text.Editable
import android.widget.*
import credo.ge.credoapp.views.ListAdapters.ReflectionAdapterAdapter
import com.shehabic.droppy.DroppyMenuPopup
import com.shehabic.droppy.DroppyMenuItem
import android.view.LayoutInflater
import credo.ge.credoapp.R
import android.content.Intent
import android.support.v7.app.AlertDialog
import android.text.InputType.TYPE_CLASS_NUMBER
import credo.ge.credoapp.DataFillActivity
import credo.ge.credoapp.StaticData
import java.util.*
import kotlin.collections.ArrayList
import android.view.MotionEvent
import br.com.sapereaude.maskedEditText.MaskedEditText
import credo.ge.credoapp.views.ViewFieldHolder
import kotlin.collections.HashMap


/**
 * Created by vakhtanggelashvili on 4/25/17.
 */

class ViewAnnotationParser {
    fun parse(clazz: Class<*>, viewGorup: ViewGroup, bindObject: Any, onSave: View.OnClickListener?, saveText: String) {
        var inflater = LayoutInflater.from(viewGorup.context)
        val fields = clazz.fields
        val fieldPaterns = ArrayList<ViewFieldHolder>()
        val fieldNameMap = HashMap<String, Field>()

        val viewList: HashMap<Int, View> = HashMap<Int, View>();


        for (field in fields) {
            fieldNameMap.put(field.name, field);

            if (field.isAnnotationPresent(TextFieldTypeViewAnotation::class.java)) {
                val annotation = field.getAnnotation<TextFieldTypeViewAnotation>(TextFieldTypeViewAnotation::class.java)
                val name = annotation.name
                val type = annotation.type
                val deffaultValue = annotation.defaultValue
                val mask = annotation.mask
                val allowedChars = annotation.allowed_chars
                val visibilityPater = annotation.visibilityPatern
                val view = inflater.inflate(R.layout.text_field, null)
                val editText = view.findViewById(R.id.editor) as MaskedEditText
                val label = view.findViewById(R.id.label) as TextView
                label.text = name
                if (!mask.isBlank())
                    editText.mask = mask
                val fieldValue = field.get(bindObject)
                var valueToSet = deffaultValue;
                if (fieldValue != null) {
                    if (type == "text")
                        valueToSet = fieldValue as String
                    if (type == "int")
                        valueToSet = (fieldValue as Int).toString()
                }
                editText.setText(valueToSet)
                if (type == "int") {
                    editText.inputType = TYPE_CLASS_NUMBER
                }
                var obj = clazz.cast(bindObject)
                editText.addTextChangedListener(object : TextWatcher {

                    override fun afterTextChanged(s: Editable) {}

                    override fun beforeTextChanged(s: CharSequence, start: Int,
                                                   count: Int, after: Int) {
                    }

                    override fun onTextChanged(s: CharSequence, start: Int,
                                               before: Int, count: Int) {


                        if (type == "text")
                            field.set(bindObject, editText.rawText.toString())
                        if (type == "int" && !s.isEmpty())
                            field.set(bindObject, editText.rawText.toString().toInt())


                    }
                })

                var viewFieldHolder = ViewFieldHolder();
                viewFieldHolder.bindObject = bindObject;
                viewFieldHolder.field = field;
                viewFieldHolder.view = view
                viewFieldHolder.paterns = visibilityPater
                fieldPaterns.add(viewFieldHolder)

                viewList.put(annotation.position, view);

            }
            if (field.isAnnotationPresent(DateFieldTypeViewAnotation::class.java)) {
                val annotation = field.getAnnotation<TextFieldTypeViewAnotation>(TextFieldTypeViewAnotation::class.java)
                val name = annotation.name
                var datePicker = DatePicker(viewGorup.context)
                viewList.put(annotation.position, datePicker)
            }
            if (field.isAnnotationPresent(ObjectFieldTypeViewAnotation::class.java)) {
                val annotation = field.getAnnotation<ObjectFieldTypeViewAnotation>(ObjectFieldTypeViewAnotation::class.java)
                val name = annotation.name
                val displayField = annotation.displayField
                val isMethod = annotation.isMethod
                val type = annotation.type
                val filter = annotation.filterWith;

                val sqlData = annotation.sqlData
                val canAddToDb = annotation.canAddToDb
                if (type == "comboBox") {
                    var view = inflater.inflate(R.layout.combobox_with_button, null)
                    //


                    val spinner = view.findViewById(R.id.spinner) as Spinner
                    val updaterUUID = UUID.randomUUID().toString()
                    var adapter: ReflectionAdapterAdapter? = null
                    val func = fun() {
                        var dataToLoad:List<Any>?=null;


                        val fieldValue = field.get(bindObject)
                        if (filter.isNullOrEmpty()) {
                            val method = field.type.getMethod("getData")
                            dataToLoad = method.invoke(null) as List<Any>
                        }else{

                            val method = field.type.getMethod("getData",String::class.java)
                            dataToLoad = method.invoke(null,filter) as List<Any>
                        }




                        adapter = ReflectionAdapterAdapter(dataToLoad, viewGorup.context, displayField, isMethod, field.type, 20f)
                        spinner.setAdapter(adapter)

                        if (fieldValue != null) {
                            var objectInList = dataToLoad.find {
                                f ->
                                (
                                        field.type
                                                .getMethod("getId")

                                                .invoke(f) as Long) ==
                                        (field.type.getMethod("getId").invoke(fieldValue) as Long)
                            }
                            var indexOfObject = dataToLoad.indexOf(objectInList)
                            if (indexOfObject >= 0) {
                                spinner.setSelection(indexOfObject)
                            }
                        }
                        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                                field.set(bindObject, adapter!!.dataList.get(pos))
                                visibilityCheck(fieldPaterns, fieldNameMap, bindObject)

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
                            intent.putExtra("autosave", false)
                            viewGorup.context.startActivity(intent)
                        }
                        if (id == 1) {
                            val intent = Intent(viewGorup.context, DataFillActivity::class.java)
                            intent.putExtra("class", field.type)
                            intent.putExtra("autosave", false)
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

                    var viewFieldHolder = ViewFieldHolder();
                    viewFieldHolder.bindObject = bindObject;
                    viewFieldHolder.field = field;
                    viewFieldHolder.view = view
                    viewFieldHolder.paterns = annotation.visibilityPatern
                    fieldPaterns.add(viewFieldHolder)
                    viewList.put(annotation.position, view);
                }


            }
            if (field.isAnnotationPresent(ObjectsListFieldTypeViewAnottion::class.java)) {
                val annotation = field.getAnnotation<ObjectsListFieldTypeViewAnottion>(ObjectsListFieldTypeViewAnottion::class.java)
                val name = annotation.name
                val displayField = annotation.displayField
                val isMethod = annotation.isMethod
                val type = annotation.type
                val sqlData = annotation.sqlData
                val canAddToDb = annotation.canAddToDb
                val joinField = annotation.joinField


                //ArrayList<Person>().getData()
                val listType = field.genericType as ParameterizedType
                val listClass = listType.actualTypeArguments[0] as Class<*>

                val view = inflater.inflate(R.layout.multi_value_field_layout, null)
                //

                val listView = view.findViewById(R.id.listView) as ListView
                val btn = view.findViewById(R.id.addBtn) as Button
                val label = view.findViewById(R.id.textLabel) as TextView
                label.text = "${name}:"
                val updaterUUID = UUID.randomUUID().toString()
                var adapter: ReflectionAdapterAdapter? = null
                var listAdapter: ReflectionAdapterAdapter? = null
                val func = fun() {

                    val method = listClass.getMethod("findby" + joinField, Long::class.java)
                    val dataToLoad = method.invoke(null, (clazz.getMethod("getId").invoke(bindObject) as Long)) as ArrayList<Any>
                    //dataOfField.addAll(dataToLoad)
                    listAdapter = ReflectionAdapterAdapter(dataToLoad, viewGorup.context, displayField, isMethod, listClass, 20f)
                    listView.adapter = listAdapter
                    listView.setOnTouchListener { v, event ->
                        val action = event.action
                        when (action) {
                            MotionEvent.ACTION_DOWN ->
                                // Disallow ScrollView to intercept touch events.
                                v.parent.requestDisallowInterceptTouchEvent(true)

                            MotionEvent.ACTION_UP ->
                                // Allow ScrollView to intercept touch events.
                                v.parent.requestDisallowInterceptTouchEvent(false)
                        }

                        // Handle ListView touch events.
                        v.onTouchEvent(event)
                    }


                }
                func()
                listView.setOnItemClickListener { parent, view, position, id ->

                    var dialogBody = inflater.inflate(R.layout.sub_list_prompt_layout, null);

                    val builder = AlertDialog.Builder(viewGorup.context).setTitle("მოქმედება").setView(dialogBody).create()

                    (dialogBody.findViewById(R.id.cancelButton) as Button).setOnClickListener {
                        builder.hide()
                    }
                    (dialogBody.findViewById(R.id.deleteButton) as Button).setOnClickListener {
                        var item = listAdapter!!.dataList.get(position)
                        listClass.getMethod("delete").invoke(item)
                        func();
                        builder.hide()
                    }
                    builder.show()

                }
                btn.setOnClickListener {
                    val intent = Intent(viewGorup.context, DataFillActivity::class.java)

                    intent.putExtra("updaterUUID", updaterUUID)

                    var joinId = (clazz.getMethod("getId").invoke(bindObject) as Long)
                    intent.putExtra("class", listClass)
                    intent.putExtra("joinClass", clazz)

                    intent.putExtra("autosave", false)
                    intent.putExtra("joinId", joinId)
                    intent.putExtra("joinField", joinField)

                    viewGorup.context.startActivity(intent)
                }

                StaticData.comboBoxUpdateFunctions.put(updaterUUID, func)


                var viewFieldHolder = ViewFieldHolder();
                viewFieldHolder.bindObject = bindObject;
                viewFieldHolder.field = field;
                viewFieldHolder.view = view
                viewFieldHolder.paterns = annotation.visibilityPatern
                fieldPaterns.add(viewFieldHolder)
                viewList.put(annotation.position, view);
            }
            if (field.isAnnotationPresent(BooleanFieldTypeViewAnotation::class.java)) {
                val annotation = field.getAnnotation<BooleanFieldTypeViewAnotation>(BooleanFieldTypeViewAnotation::class.java)
                val name = annotation.name
                val defaultVal = annotation.defaultVal
                val view = inflater.inflate(R.layout.checkbox_with_name, null)
                val checkbox = view.findViewById(R.id.checkBox) as CheckBox
                checkbox.text = name
                val fieldValue = field.get(bindObject)
                var valueToSet = defaultVal;
                if (fieldValue != null) {
                    valueToSet = fieldValue as Boolean
                }

                checkbox.isChecked = valueToSet;



                checkbox.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
                    field.set(bindObject, isChecked)
                    visibilityCheck(fieldPaterns, fieldNameMap, bindObject)
                })
                viewList.put(annotation.position, view);

            }
        }
        val orderedList = viewList.toSortedMap();
        orderedList.forEach { t, u ->
            viewGorup.addView(u)
        }
        if (onSave != null) {
            val saveBtn = Button(viewGorup.context)
            saveBtn.text = saveText
            saveBtn.setOnClickListener(onSave)
            viewGorup.addView(saveBtn)
        } else {

        }
        visibilityCheck(fieldPaterns, fieldNameMap, bindObject)


    }
}

fun visibilityCheck(fieldPaterns: ArrayList<ViewFieldHolder>, fieldNameMap: HashMap<String, Field>, bindObject: Any) {


    fieldPaterns.forEach {
        var visible = true


        it.paterns!!.forEach {
            val patern = it.split(":")
            val fieldName = patern[0]
            val fieldValueMustBe = patern[1].split(",")
            val field = fieldNameMap.get(fieldName)

            val rs = field!!.get(bindObject);
            var fieldValue = "";
            if (rs != null) {
                fieldValue = rs.toString()
            }
            var visibleTemp = false
            fieldValueMustBe.forEach {
                if (it == fieldValue)
                    visibleTemp = true
            }
            visible = visibleTemp

        }

        if (visible)
            it.view!!.visibility = View.VISIBLE
        else {
            it.view!!.visibility = View.GONE
        }


    }
}


private fun Field.getTypeForThis(): Any {
    return type;
}

