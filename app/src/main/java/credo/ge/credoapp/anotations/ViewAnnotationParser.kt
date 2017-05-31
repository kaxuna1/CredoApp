package credo.ge.credoapp.anotations

import android.text.TextWatcher
import android.view.View

import java.lang.reflect.*

import android.text.Editable
import android.widget.*
import credo.ge.credoapp.views.ListAdapters.ReflectionAdapterAdapter
import com.shehabic.droppy.DroppyMenuPopup
import com.shehabic.droppy.DroppyMenuItem
import android.view.LayoutInflater
import credo.ge.credoapp.R
import android.content.Intent
import android.support.v4.app.FragmentManager
import android.support.v7.app.AlertDialog
import android.text.InputType.TYPE_CLASS_NUMBER
import credo.ge.credoapp.DataFillActivity
import credo.ge.credoapp.StaticData
import java.util.*
import kotlin.collections.ArrayList
import android.view.MotionEvent
import br.com.sapereaude.maskedEditText.MaskedEditText
import credo.ge.credoapp.views.CredoPageAdapter
import credo.ge.credoapp.views.ViewFieldHolder
import kotlin.collections.HashMap
import android.support.v4.view.ViewPager
import com.astuetz.PagerSlidingTabStrip
import mehdi.sakout.fancybuttons.FancyButton


/**
 * Created by vakhtanggelashvili on 4/25/17.
 */

class ViewAnnotationParser {
    fun parse(clazz: Class<*>, bindObject: Any, onSave: View.OnClickListener?,
              saveText: String, autoSave: Boolean,fragmentManager: FragmentManager,pager: ViewPager, tabs: PagerSlidingTabStrip) {





        var inflater = LayoutInflater.from(pager.context)
        val fields = clazz.fields
        val annotation = clazz.getAnnotation<ParserClassAnnotation>(ParserClassAnnotation::class.java)
        val fieldPaterns = ArrayList<ViewFieldHolder>()
        val fieldNameMap = HashMap<String, Field>()
        var titles = arrayOf("მთავარი")
        if(annotation.cols.isNotEmpty()){
            titles = annotation.cols
        }
        val adapter = CredoPageAdapter(fragmentManager,titles);
        val viewPagesList:HashMap<Int,HashMap<Int,View> > = HashMap();


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
                        if(autoSave){

                        }
                    }
                })

                var viewFieldHolder = ViewFieldHolder();
                viewFieldHolder.bindObject = bindObject;
                viewFieldHolder.field = field;
                viewFieldHolder.view = view
                viewFieldHolder.paterns = visibilityPater
                fieldPaterns.add(viewFieldHolder)


                viewPagesList.putIfAbsent(annotation.page, HashMap())
                viewPagesList.get(annotation.page)!!.put(annotation.position, view);

            }
            if (field.isAnnotationPresent(LabelFieldTypeViewAnotaion::class.java)) {
                val annotation = field.getAnnotation<LabelFieldTypeViewAnotaion>(LabelFieldTypeViewAnotaion::class.java)

                val view = inflater.inflate(R.layout.label_field, null)
                val label = view.findViewById(R.id.label) as TextView
                label.text = annotation.label

                viewPagesList.putIfAbsent(annotation.page, HashMap())
                viewPagesList.get(annotation.page)!!.put(annotation.position, view);
            }
            if (field.isAnnotationPresent(DateFieldTypeViewAnotation::class.java)) {
                val annotation = field.getAnnotation<TextFieldTypeViewAnotation>(TextFieldTypeViewAnotation::class.java)
                val name = annotation.name
                var datePicker = DatePicker(pager.context)
                viewPagesList.putIfAbsent(annotation.page, HashMap())
                viewPagesList.get(annotation.page)!!.put(annotation.position, datePicker);
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


                   // val spinner = view.findViewById(R.id.spinner) as Spinner
                    //val spinner2 = view.findViewById(R.id.spinner2) as SearchableSpinner

                    val choose = view.findViewById(R.id.chooseDialogBtn) as TextView

                    val dropdown = view.findViewById(R.id.chooserOpen) as FancyButton





                    val updaterUUID = UUID.randomUUID().toString()
                    var adapter: ReflectionAdapterAdapter? = null

                    val func = fun() {





                        var dataToLoad:List<Any>?=null;


                        val fieldValue = field.get(bindObject)
                        if (filter.isNullOrEmpty()) {
                            val method = field.type.getMethod("getData")
                            dataToLoad = method.invoke(null) as List<Any>
                        }else{

                            val method = field.type.getMethod("getData" ,String::class.java)
                            dataToLoad = method.invoke(null,filter) as List<Any>
                        }

                        val list = ArrayList<String>()

                        dataToLoad.forEach {
                            list.add((field.type.getMethod(displayField).invoke(it) as String))
                        }

                        val chooserFunction = fun(){



                            val dialogBody = inflater.inflate(R.layout.combo_with_filter, null);

                            val listView = dialogBody.findViewById(R.id.list_item_id) as ListView

                            val filterText = dialogBody.findViewById(R.id.filterField) as EditText


                            var filterString = "";
                            val builder = AlertDialog.Builder(pager.context).setTitle(annotation.name).setView(dialogBody).create()

                            val fillList = fun(){


                                val filteredList = dataToLoad!!.filter{

                                    (field.type.getMethod(displayField).invoke(it) as String).contains(filterString)

                                }

                                adapter = ReflectionAdapterAdapter(filteredList, pager.context, displayField, isMethod, field.type, 20f)

                                listView.adapter = adapter


                                listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->

                                    field.set(bindObject, dataToLoad!!.get(position))

                                    val value = field.type.getMethod(displayField).invoke(dataToLoad!!.get(position)) as String

                                    choose.text = value

                                    visibilityCheck(fieldPaterns, fieldNameMap, bindObject)

                                    builder.hide()
                                }

                            }

                            filterText.addTextChangedListener(object : TextWatcher{

                                override fun afterTextChanged(s: Editable?) {

                                }

                                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                                }

                                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                                    filterString = s!!.toString()
                                    fillList();
                                }

                            })
                            fillList();


                            builder.show();
                        }

                        choose.setOnClickListener {

                            chooserFunction();
                        }
                        dropdown.setOnClickListener {
                            chooserFunction();
                        }



                     //   val kadapter = ArrayAdapter<String>(pager.context,android.R.layout.simple_list_item_1,list)






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
                                try {
                                    var value = field.type.getMethod(displayField).invoke(dataToLoad!!.get(indexOfObject)) as String
                                    choose.text = value

                                }catch (e:Exception){
                                    e.printStackTrace()
                                }
                            }
                        }
                        /*spinner2.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
                            override fun onNothingSelected(parent: AdapterView<*>?) {


                            }

                            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                                field.set(bindObject, dataToLoad!!.get(position))
                                visibilityCheck(fieldPaterns, fieldNameMap, bindObject)

                            }

                        }*/
                    }
                    func()
                    StaticData.comboBoxUpdateFunctions.put(updaterUUID, func)

                    val anchor = view.findViewById(R.id.buttonActions) as FancyButton
                    if(!canAddToDb){
                        anchor.visibility=View.GONE
                    }
                    val label = view.findViewById(R.id.label) as TextView
                    label.text = "${name}:"
                    val droppyBuilder = DroppyMenuPopup.Builder(pager.context, anchor)

                    droppyBuilder.addMenuItem(DroppyMenuItem("add"))
                            .addMenuItem(DroppyMenuItem("edit"))
                            .addMenuItem(DroppyMenuItem("delete"))
                            .addSeparator()


                    droppyBuilder.setOnClick { v, id ->
                        if (id == 0) {
                            val intent = Intent(pager.context, DataFillActivity::class.java)
                            intent.putExtra("class", field.type)
                            intent.putExtra("updaterUUID", updaterUUID)
                            intent.putExtra("autosave", false)
                            pager.context.startActivity(intent)
                        }
                        if (id == 1) {
                            val intent = Intent(pager.context, DataFillActivity::class.java)
                            intent.putExtra("class", field.type)
                            intent.putExtra("autosave", false)
                            intent.putExtra("updaterUUID", updaterUUID)

                           // var currentId = field.type.getMethod("getId").invoke(adapter!!.dataList.get(spinner.selectedItemPosition)) as Long
                           // intent.putExtra("id", currentId)
                            pager.context.startActivity(intent)
                        }
                        if (id == 2) {
                        //    field.type.getMethod("delete").invoke(adapter!!.dataList.get(spinner.selectedItemPosition))
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
                    viewPagesList.putIfAbsent(annotation.page, HashMap())
                    viewPagesList.get(annotation.page)!!.put(annotation.position, view);
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
                val btn = view.findViewById(R.id.addBtn) as FancyButton
                val label = view.findViewById(R.id.textLabel) as TextView
                label.text = "${name}"
                val updaterUUID = UUID.randomUUID().toString()
                var adapter: ReflectionAdapterAdapter? = null
                var listAdapter: ReflectionAdapterAdapter? = null
                val func = fun() {

                    val method = listClass.getMethod("findby" + joinField, Long::class.java)
                    val dataToLoad = method.invoke(null, (clazz.getMethod("getId").invoke(bindObject) as Long)) as ArrayList<Any>
                    //dataOfField.addAll(dataToLoad)
                    listAdapter = ReflectionAdapterAdapter(dataToLoad, pager.context, displayField, isMethod, listClass, 20f)
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

                    val builder = AlertDialog.Builder(pager.context).setTitle("მოქმედება").setView(dialogBody).create()

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
                    val intent = Intent(pager.context, DataFillActivity::class.java)

                    intent.putExtra("updaterUUID", updaterUUID)

                    var joinId = (clazz.getMethod("getId").invoke(bindObject) as Long)
                    intent.putExtra("class", listClass)
                    intent.putExtra("joinClass", clazz)

                    intent.putExtra("autosave", false)
                    intent.putExtra("joinId", joinId)
                    intent.putExtra("joinField", joinField)

                    pager.context.startActivity(intent)
                }

                StaticData.comboBoxUpdateFunctions.put(updaterUUID, func)


                var viewFieldHolder = ViewFieldHolder();
                viewFieldHolder.bindObject = bindObject;
                viewFieldHolder.field = field;
                viewFieldHolder.view = view
                viewFieldHolder.paterns = annotation.visibilityPatern
                fieldPaterns.add(viewFieldHolder)
                viewPagesList.putIfAbsent(annotation.page, HashMap())
                viewPagesList.get(annotation.page)!!.put(annotation.position, view);
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
                viewPagesList.putIfAbsent(annotation.page, HashMap())
                viewPagesList.get(annotation.page)!!.put(annotation.position, view);

            }
        }
        visibilityCheck(fieldPaterns, fieldNameMap, bindObject)
        adapter.items=viewPagesList
        pager.adapter = adapter;
        tabs.setViewPager(pager)
        tabs.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                val k = adapter.saveState()
                adapter.restoreState(k, null)
            }

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        });



        pager.currentItem = 0


        if (onSave != null) {
            val saveBtn = Button(pager.context)
            saveBtn.text = saveText
            saveBtn.setOnClickListener(onSave)
            viewPagesList.get(0)!!.put(100, saveBtn);
        } else {

        }

    }
}

fun visibilityCheck(fieldPaterns: ArrayList<ViewFieldHolder>, fieldNameMap: HashMap<String, Field>, bindObject: Any) {


    fieldPaterns.forEach {
        var visible = true


        if(it.paterns!=null)
        it.paterns!!.forEach {
            if(!it.isNullOrEmpty()){
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


        }

        if (visible)
            it.view!!.visibility = View.VISIBLE
        else {
            it.view!!.visibility = View.GONE
        }
    }
}

