package credo.ge.credoapp.anotations

import android.app.Activity
import android.view.View

import java.lang.reflect.*

import android.widget.*
import credo.ge.credoapp.views.ListAdapters.ReflectionAdapterAdapter
import com.shehabic.droppy.DroppyMenuPopup
import com.shehabic.droppy.DroppyMenuItem
import android.view.LayoutInflater
import android.content.Intent
import android.graphics.Color
import android.support.v4.app.FragmentManager
import android.support.v7.app.AlertDialog
import android.text.InputType.TYPE_CLASS_NUMBER
import java.util.*
import kotlin.collections.ArrayList
import br.com.sapereaude.maskedEditText.MaskedEditText
import credo.ge.credoapp.views.CredoPageAdapter
import credo.ge.credoapp.views.ViewFieldHolder
import kotlin.collections.HashMap
import android.support.v4.view.ViewPager
import com.astuetz.PagerSlidingTabStrip
import mehdi.sakout.fancybuttons.FancyButton
import java.util.concurrent.ConcurrentHashMap
import android.graphics.Typeface
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutCompat
import android.text.*
import android.util.Log
import cc.cloudist.acplibrary.ACProgressConstant
import cc.cloudist.acplibrary.ACProgressFlower
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetSequence
import credo.ge.credoapp.*
import credo.ge.credoapp.models.Loan
import credo.ge.credoapp.online.OnlineData
import org.jetbrains.anko.*
import rx.functions.Action1
import java.text.SimpleDateFormat


/**
 * Created by vakhtanggelashvili on 4/25/17.
 */

class ViewAnnotationParser {


    public var checkRequaredFieldsForSave = fun(): Boolean {
        var validForSave = true;

        fieldPaterns.forEach {
            if (it.requiredForSave) {
                if (it.field!!.get(it.bindObject) != null) {
                    var value = it.field!!.get(it.bindObject).toString()

                    if (value.isNullOrEmpty()) {
                        validForSave = false;
                    }
                    Log.d("", value)
                } else {
                    validForSave = false;
                }

            }
        }
        return validForSave;
    }

    public var saveFun = fun() {
        if (checkRequaredFieldsForSave()) {
            clazz!!.getMethod("save").invoke(myLocalObject)
        }
    }

    var tapInstructions = ConcurrentHashMap<Int,TapTargetSequence>()

    fun viewToList(fields: Array<Field>,
                   inflater: LayoutInflater,
                   fieldNameMap: HashMap<String, Field>,
                   fieldPaterns: ArrayList<ViewFieldHolder>,
                   clazz: Class<*>,
                   bindObject: Any,
                   pager: ViewPager,
                   autoSave: Boolean)
            : ConcurrentHashMap<Int, ConcurrentHashMap<Int, View>> {


        val myLocalObject = bindObject;


        val font1 = Typeface.createFromAsset(pager.context.getAssets(), "fonts/font1.ttf");


        var listForDataLoad = ArrayList<() -> Unit>()


        val viewPagesList: ConcurrentHashMap<Int, ConcurrentHashMap<Int, View>> = ConcurrentHashMap();
        for (field in fields) {
            fieldNameMap.put(field.name, field);

            if (field.isAnnotationPresent(TextFieldTypeViewAnotation::class.java)) {
                val annotation = field.getAnnotation<TextFieldTypeViewAnotation>(TextFieldTypeViewAnotation::class.java)
                val name = annotation.name
                val type = annotation.type
                val hint = annotation.hint
                val deffaultValue = annotation.defaultValue
                val mask = annotation.mask
                val allowedChars = annotation.allowed_chars
                val visibilityPater = annotation.visibilityPatern
                val view: View?
                if (hint.isNullOrEmpty()) {
                    view = inflater.inflate(R.layout.text_field, null)
                    val editText = view!!.findViewById(R.id.editor) as MaskedEditText
                    val label = view!!.findViewById(R.id.label) as TextView
                    val required = view!!.findViewById(R.id.required) as ImageView
                    if (annotation.requiredForSave) {
                        required.visibility = View.VISIBLE
                    } else {
                        required.visibility = View.GONE
                    }
                    label.text = name
                    label.typeface = font1
                    if (!mask.isBlank())
                        editText.mask = mask
                    val fieldValue = field.get(myLocalObject)
                    var valueToSet = deffaultValue;
                    if (fieldValue != null) {
                        if (type == "text")
                            valueToSet = fieldValue as String
                        if (type == "int") {
                            var value = fieldValue as Int
                            if (value != 0) {
                                valueToSet = (value).toString()
                            } else {
                                valueToSet = ""
                            }


                        }

                    }
                    editText.setText(valueToSet)
                    if (type == "int") {
                        editText.inputType = TYPE_CLASS_NUMBER;
                    } else {
                        if (type == "number") {
                            editText.inputType = InputType.TYPE_CLASS_PHONE
                        }
                    }
                    val allowed = "აბგდევზთიკლმნოპჟრსტუფქღყშჩცძწჭხჰ"


                    var filter = StaticJava().getFilter(allowed)
                    if (mask.isNullOrEmpty())
                        editText.filters = arrayOf(filter)
                    editText.addTextChangedListener(object : TextWatcher {

                        override fun afterTextChanged(s: Editable) {}

                        override fun beforeTextChanged(s: CharSequence, start: Int,
                                                       count: Int, after: Int) {
                        }

                        override fun onTextChanged(s: CharSequence, start: Int,
                                                   before: Int, count: Int) {


                            if (type == "text" || type == "number")
                                field.set(myLocalObject, editText.rawText.toString())
                            if (type == "int" ){
                                if(s.isEmpty())
                                    field.set(myLocalObject, 0)
                                else
                                    field.set(myLocalObject, editText.rawText.toString().toInt())
                            }

                            if (autoSave) {
                                saveFun()
                            }
                        }
                    })
                } else {
                    view = inflater.inflate(R.layout.text_field_hint, null)
                    val editText = view.findViewById(R.id.editor2) as EditText
                    val label = view.findViewById(R.id.label) as TextView
                    editText.setHint(hint)
                    label.text = name
                    label.typeface = font1
                    val required = view!!.findViewById(R.id.required) as ImageView
                    if (annotation.requiredForSave) {
                        required.visibility = View.VISIBLE
                    } else {
                        required.visibility = View.GONE
                    }
                    val fieldValue = field.get(myLocalObject)
                    var valueToSet = deffaultValue;
                    if (fieldValue != null) {
                        if (type == "text")
                            valueToSet = fieldValue as String
                        if (type == "int") {
                            var value = fieldValue as Int
                            if (value != 0) {
                                valueToSet = (value).toString()
                            } else {
                                valueToSet = ""
                            }


                        }

                    }
                    editText.setText(valueToSet)
                    if (type == "int") {
                        editText.inputType = TYPE_CLASS_NUMBER;
                    } else {
                        if (type == "number") {
                            editText.inputType = InputType.TYPE_CLASS_PHONE
                        }
                    }

                    val allowed = "აბცდევზთიკლმნოპჟრსტუფქღყშჩცძწჭხჰ"

                    var filter = StaticJava().getFilter(allowed)
                    if (mask.isNullOrEmpty())
                        editText.filters = arrayOf(filter)
                    editText.addTextChangedListener(object : TextWatcher {

                        override fun afterTextChanged(s: Editable) {}

                        override fun beforeTextChanged(s: CharSequence, start: Int,
                                                       count: Int, after: Int) {
                        }

                        override fun onTextChanged(s: CharSequence, start: Int,
                                                   before: Int, count: Int) {


                            if (type == "text" || type == "number")
                                field.set(myLocalObject, editText.text.toString())
                            if (type == "int" && !s.isEmpty())
                                field.set(myLocalObject, editText.text.toString().toInt())
                            if (autoSave) {
                                saveFun()
                            }
                        }
                    })
                }

                var viewFieldHolder = ViewFieldHolder();
                viewFieldHolder.bindObject = myLocalObject;
                viewFieldHolder.field = field;
                viewFieldHolder.view = view
                viewFieldHolder.paterns = visibilityPater
                viewFieldHolder.requiredForSave = annotation.requiredForSave
                fieldPaterns.add(viewFieldHolder)
                viewPagesList.putIfAbsent(annotation.page, ConcurrentHashMap())
                viewPagesList.get(annotation.page)!!.put(annotation.position, view);
            }
            if (field.isAnnotationPresent(LabelFieldTypeViewAnotaion::class.java)) {
                val annotation = field.getAnnotation<LabelFieldTypeViewAnotaion>(LabelFieldTypeViewAnotaion::class.java)

                val view = inflater.inflate(R.layout.label_field, null)
                val label = view.findViewById(R.id.label) as TextView
                label.text = annotation.label

                viewPagesList.putIfAbsent(annotation.page, ConcurrentHashMap())
                viewPagesList.get(annotation.page)!!.put(annotation.position, view);

            }
            if (field.isAnnotationPresent(DateFieldTypeViewAnotation::class.java)) {
                val annotation = field.getAnnotation<DateFieldTypeViewAnotation>(DateFieldTypeViewAnotation::class.java)
                val name = annotation.name


                val view = inflater.inflate(R.layout.date_field_view, null)


                val label = view.findViewById(R.id.label) as TextView

                label.text = name


                val button = view.findViewById(R.id.button) as FancyButton


                var current = field.get(myLocalObject) as Date

                val sdf = SimpleDateFormat("yyyy/MM/dd")

                button.setText(sdf.format(current))

                button.setOnClickListener {

                    val dialogBody = inflater.inflate(R.layout.datepickerview, null);

                    val picker = dialogBody.findViewById(R.id.datePicker) as DatePicker

                    val builder = AlertDialog.Builder(pager.context).setTitle(annotation.name).setView(dialogBody).create()

                    val save = dialogBody.findViewById(R.id.save) as Button
                    val cancel = dialogBody.findViewById(R.id.cancel) as Button





                    cancel.setOnClickListener {
                        builder.hide();
                    }
                    save.setOnClickListener {

                        val date = Date()

                        date.date = picker.dayOfMonth
                        date.month = picker.month
                        date.year = picker.year - 1900


                        field.set(myLocalObject, date)

                        saveFun()

                        button.setText(sdf.format(date))

                        builder.hide()
                    }

                    builder.show()
                }


                //var datePicker = view.findViewById(R.id.dateField)


                viewPagesList.putIfAbsent(annotation.page, ConcurrentHashMap())
                viewPagesList.get(annotation.page)!!.put(annotation.position, view);
            }
            if (field.isAnnotationPresent(ObjectFieldTypeViewAnotation::class.java)) {
                val annotation = field.getAnnotation<ObjectFieldTypeViewAnotation>(ObjectFieldTypeViewAnotation::class.java)
                val name = annotation.name
                val displayField = annotation.displayField
                val isMethod = annotation.isMethod
                val type = annotation.type
                val filter = annotation.filterWith;
                val filterWithField = annotation.filterWithField.split(":")

                var filterObject: String = ""

                var filterField: String = ""

                var filterFieldMy: String = ""

                if (filterWithField.size == 3) {
                    filterObject = filterWithField[0]
                    filterField = filterWithField[1]
                    filterFieldMy = filterWithField[2]
                }


                val sqlData = annotation.sqlData
                val canAddToDb = annotation.canAddToDb
                if (type == "comboBox") {
                    var view = inflater.inflate(R.layout.combobox_with_button, null)
                    //
                    // val spinner = view.findViewById(R.id.spinner) as Spinner
                    //val spinner2 = view.findViewById(R.id.spinner2) as SearchableSpinner

                    val btn = view.findViewById(R.id.editBtn) as TextView
                    btn.typeface = font1
                    val required = view!!.findViewById(R.id.required) as ImageView
                    if (annotation.requiredForSave) {
                        required.visibility = View.VISIBLE
                    } else {
                        required.visibility = View.GONE
                    }
                    // val choose = view.findViewById(R.id.chooseDialogBtn) as TextView

                    //choose.typeface = font1

                    //val dropdown = view.findViewById(R.id.chooserOpen) as FancyButton

                    val updaterUUID = UUID.randomUUID().toString()
                    var adapter: ReflectionAdapterAdapter? = null

                    val func = fun() {


                        var dataToLoad: List<Any>? = null;


                        val fieldValue = field.get(myLocalObject)
                        if (filter.isNullOrEmpty()) {
                            val method = field.type.getMethod("getData")
                            dataToLoad = method.invoke(null) as List<Any>
                        } else {

                            val method = field.type.getMethod("getData", String::class.java)
                            dataToLoad = method.invoke(null, filter) as List<Any>
                        }

                        val list = ArrayList<String>()

                        dataToLoad.forEach {
                            list.add((field.type.getMethod(displayField).invoke(it) as String))
                        }

                        val chooserFunction = fun() {


                            val dialogBody = inflater.inflate(R.layout.combo_with_filter, null);

                            val listView = dialogBody.findViewById(R.id.list_item_id) as ListView

                            val filterText = dialogBody.findViewById(R.id.filterField) as EditText

                            if (dataToLoad!!.size < 5) {
                                filterText.visibility = View.GONE
                            }


                            var filterString = "";
                            val builder = AlertDialog.Builder(pager.context).setTitle(annotation.name).setView(dialogBody).create()

                            val fillList = fun() {


                                var filteredList = dataToLoad!!.filter {
                                    (field.type.getMethod(displayField).invoke(it) as String).contains(filterString)
                                }
                                if (filterWithField.size == 3) {

                                    try {
                                        filteredList = filteredList.filter {
                                            val val1 = field.type.getField(filterField).get((it)).toString();
                                            val val2 = clazz.getField(filterObject).type.getField(filterField).get(clazz.getField(filterObject).get(bindObject)).toString()
                                            val1 == val2
                                        }
                                    } catch (e: Exception) {
                                        e.printStackTrace()
                                    }

                                }


                                adapter = ReflectionAdapterAdapter(filteredList, pager.context, displayField, isMethod, field.type, 20f)

                                listView.adapter = adapter


                                listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->

                                    field.set(bindObject, filteredList.get(position))

                                    val value = field.type.getMethod(displayField).invoke(filteredList!!.get(position)) as String

                                    btn.setText(value)

                                    visibilityCheck(fieldPaterns, fieldNameMap, bindObject)

                                    if (autoSave) {
                                        saveFun()
                                    }
                                    builder.hide()
                                }

                            }

                            filterText.addTextChangedListener(object : TextWatcher {

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

                        btn.setOnClickListener {

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
                                    btn.setText(value)

                                } catch (e: Exception) {
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
                    if (!canAddToDb) {
                        anchor.visibility = View.GONE
                    }
                    val label = view.findViewById(R.id.label) as TextView
                    label.text = "${name}:"

                    label.typeface = font1

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
                    viewFieldHolder.bindObject = myLocalObject;
                    viewFieldHolder.field = field;
                    viewFieldHolder.view = view
                    viewFieldHolder.requiredForSave = annotation.requiredForSave
                    viewFieldHolder.paterns = annotation.visibilityPatern
                    fieldPaterns.add(viewFieldHolder)
                    viewPagesList.putIfAbsent(annotation.page, ConcurrentHashMap())
                    viewPagesList.get(annotation.page)!!.put(annotation.position, view);
                }


            }
            if (field.isAnnotationPresent(InlineObjectsListFieldTypeViewAnotation::class.java)) {
                val annotation = field.getAnnotation<InlineObjectsListFieldTypeViewAnotation>(InlineObjectsListFieldTypeViewAnotation::class.java)
                val name = annotation.name
                val displayField = annotation.displayField
                val isMethod = annotation.isMethod
                val type = annotation.type
                val sqlData = annotation.sqlData
                val canAddToDb = annotation.canAddToDb
                val joinField = annotation.joinField


                val listType = field.genericType as ParameterizedType
                val listClass = listType.actualTypeArguments[0] as Class<*>


                val view = inflater.inflate(R.layout.inline_objects_list_view, null)
                val addBtn = view.findViewById(R.id.addObjectButton) as FancyButton
                val label = view.findViewById(R.id.label) as TextView
                val linearPlace = view.findViewById(R.id.linearForObjects) as LinearLayout


                label.typeface = font1
                label.text = annotation.name


                val updaterUUID = UUID.randomUUID().toString()

                val func = fun() {
                    val method = listClass.getMethod("findby" + joinField, Long::class.java)
                    val dataToLoad = method.invoke(null, (clazz.getMethod("getId").invoke(myLocalObject) as Long)) as ArrayList<Any>
                    linearPlace.removeAllViews()
                    dataToLoad.forEach {
                        val objectItemView = inflater.inflate(R.layout.inline_objects_item_linear, null)

                        val linearForObjectFields = objectItemView.findViewById(R.id.linearForObjectFields) as LinearLayout
                        val deleteItemBtn = objectItemView.findViewById(R.id.removeObjectButton)
                        deleteItemBtn.setOnClickListener {
                            listClass.getMethod("delete").invoke(it)
                            val func1 = StaticData.comboBoxUpdateFunctions.get(updaterUUID) as () -> Unit
                            func1()
                        }


                        val viewPagesListInner: ConcurrentHashMap<Int, ConcurrentHashMap<Int, View>> =
                                viewToList(listClass.fields, inflater, fieldNameMap, fieldPaterns, listClass, it, pager, true);


                        viewPagesListInner[0]!!.forEach {
                            i, v ->
                            linearForObjectFields.addView(v)
                        }


                        linearPlace.addView(objectItemView);

                    }


                }
                StaticData.comboBoxUpdateFunctions.put(updaterUUID, func)

                listForDataLoad.add(func)



                addBtn.setOnClickListener {

                    if (checkRequaredFieldsForSave()) {
                        saveFun()
                        var joinId = (clazz.getMethod("getId").invoke(myLocalObject) as Long)
                        val ctor = listClass.getConstructor()
                        var objectItemToSave = ctor.newInstance()
                        val joinClassName: Class<*> = clazz as Class<*>
                        val joinFieldName: String = joinField
                        var joinObject = joinClassName.getMethod("getById", Long::class.java).invoke(null, joinId)
                        listClass.getField(joinFieldName).set(objectItemToSave, joinObject)
                        listClass.getMethod("save").invoke(objectItemToSave)
                    } else {
                        Snackbar.make(pager, "შეავსეთ სავალდებულო ველები!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show()
                    }




                    func();
                }


                var viewFieldHolder = ViewFieldHolder();
                viewFieldHolder.bindObject = myLocalObject;
                viewFieldHolder.field = field;
                viewFieldHolder.view = view
                viewFieldHolder.paterns = annotation.visibilityPatern
                fieldPaterns.add(viewFieldHolder)
                viewPagesList.putIfAbsent(annotation.page, ConcurrentHashMap())
                viewPagesList.get(annotation.page)!!.put(annotation.position, view);


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



                if (type == "comboBox") {
                    //ArrayList<Person>().getData()
                    val listType = field.genericType as ParameterizedType
                    val listClass = listType.actualTypeArguments[0] as Class<*>

                    val view = inflater.inflate(R.layout.multi_value_field_layout, null)
                    val addBtn = view.findViewById(R.id.fieldName) as FancyButton
                    val fieldValue = view.findViewById(R.id.fieldValue) as FancyButton



                    addBtn.setText(annotation.name)
                    addBtn.setCustomTextFont("fonts/font1.ttf")
                    fieldValue.setCustomTextFont("fonts/font1.ttf")


                    var list: ListView? = null

                    val updaterUUID = UUID.randomUUID().toString()

                    var listAdapter: ReflectionAdapterAdapter? = null
                    val func = fun() {
                        if (checkRequaredFieldsForSave()) {
                            saveFun()
                            val method = listClass.getMethod("findby" + joinField, Long::class.java)
                            val dataToLoad = method.invoke(null, (clazz.getMethod("getId").invoke(bindObject) as Long)) as ArrayList<Any>
                            //dataOfField.addAll(dataToLoad)
                            listAdapter = ReflectionAdapterAdapter(dataToLoad, pager.context, displayField, isMethod, listClass, 20f)
                            /*     listView.adapter = listAdapter
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
                                 }*/

                            if (list != null) {
                                list!!.adapter = listAdapter
                            }
                            fieldValue.setText("${dataToLoad.size}")
                        }
                    }

                    listForDataLoad.add(func)




                    addBtn.setOnClickListener {

                        if (checkRequaredFieldsForSave()) {
                            saveFun()
                            var dialogBody = inflater.inflate(R.layout.multifieldviewandadd, null);

                            val builder = AlertDialog.Builder(pager.context).setTitle(annotation.name).setView(dialogBody).create()
                            val addToListButton = dialogBody.findViewById(R.id.addBtn) as FancyButton
                            list = dialogBody.findViewById(R.id.listView) as ListView
                            list!!.adapter = listAdapter

                            addToListButton.setCustomTextFont("fonts/font1.ttf")
                            addToListButton.setOnClickListener {
                                val intent = Intent(pager.context, DataFillActivity::class.java)

                                intent.putExtra("updaterUUID", updaterUUID)

                                var joinId = (clazz.getMethod("getId").invoke(myLocalObject) as Long)
                                intent.putExtra("class", listClass)
                                intent.putExtra("joinClass", clazz)

                                intent.putExtra("autosave", false)
                                intent.putExtra("joinId", joinId)
                                intent.putExtra("joinField", joinField)

                                pager.context.startActivity(intent)
                            }
                            list!!.setOnItemClickListener { parent, view, position, id ->

                                var dialogBody2 = inflater.inflate(R.layout.sub_list_prompt_layout, null);

                                val builder2 = AlertDialog.Builder(pager.context).setTitle("მოქმედება").setView(dialogBody2).create()

                                (dialogBody2.findViewById(R.id.cancelButton) as Button).setOnClickListener {
                                    builder2.hide()
                                }
                                (dialogBody2.findViewById(R.id.deleteButton) as Button).setOnClickListener {
                                    var item = listAdapter!!.dataList.get(position)
                                    listClass.getMethod("delete").invoke(item)
                                    func();
                                    builder2.hide()
                                }
                                (dialogBody2.findViewById(R.id.editButton) as Button).setOnClickListener {
                                    val item = listAdapter!!.dataList.get(position)

                                    val currentId = listClass.getMethod("getId").invoke(item) as Long
                                    val intent = Intent(pager.context, DataFillActivity::class.java)
                                    intent.putExtra("class", listClass)

                                    intent.putExtra("updaterUUID", updaterUUID)
                                    intent.putExtra("id", currentId)
                                    pager.context.startActivity(intent)
                                    builder2.hide()

                                }
                                builder2.show()

                            }






                            builder.show();
                        } else {
                            Snackbar.make(pager, "შეავსეთ სავალდებულო ველები!", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show()
                        }


                    }
                    //
                    StaticData.comboBoxUpdateFunctions.put(updaterUUID, func)


                    var viewFieldHolder = ViewFieldHolder();
                    viewFieldHolder.bindObject = myLocalObject;
                    viewFieldHolder.field = field;
                    viewFieldHolder.view = view
                    viewFieldHolder.paterns = annotation.visibilityPatern
                    fieldPaterns.add(viewFieldHolder)
                    viewPagesList.putIfAbsent(annotation.page, ConcurrentHashMap())
                    viewPagesList.get(annotation.page)!!.put(annotation.position, view);
                }
                if (type == "list") {
                    var view = inflater.inflate(R.layout.object_list_view, null) as LinearLayout

                    val listType = field.genericType as ParameterizedType
                    val listClass = listType.actualTypeArguments[0] as Class<*>

                    val method = listClass.getMethod("getData")
                    val inflater = LayoutInflater.from(pager.context)

                    if (annotation.objectType == "loan") {
                        var dataToLoad = method.invoke(null) as List<Loan>

                        if (annotation.filter.equals("1")) {
                            dataToLoad.filter { loan -> loan.sent }.forEach {

                                var textColor = Color.WHITE
                                var color = pager.context.resources.getColor(R.color.grey)


                                val globalIt = it;

                                when (globalIt.product.name) {
                                    "U1" -> {
                                        textColor = Color.WHITE
                                        color = pager.context.resources.getColor(R.color.grey)
                                    }
                                    "A1" -> {
                                        textColor = Color.WHITE
                                        color = pager.context.resources.getColor(R.color.darkgrey)
                                    }
                                    else -> {
                                        textColor = Color.BLACK
                                        color = pager.context.resources.getColor(R.color.white)
                                    }
                                }
                                var item = inflater.inflate(R.layout.loanlistitem, null, false)


                                var productImage = item.findViewById(R.id.image) as ImageView
                                val generator = ColorGenerator.MATERIAL;
                                val color1 = generator.getRandomColor()
                                val drawable = TextDrawable.builder()
                                        .beginConfig()
                                        .textColor(textColor)
                                        .useFont(font1)
                                        .bold()
                                        .toUpperCase()
                                        .withBorder(12) /* thickness in px */
                                        .endConfig()
                                        .buildRoundRect("${globalIt.product.product}", color, 20)
                                productImage.setImageDrawable(drawable)


                                val user = item.findViewById(R.id.clientName) as TextView
                                val status = item.findViewById(R.id.status) as TextView
                                val loanSum = item.findViewById(R.id.loanSum) as TextView

                                user.setOnClickListener {
                                    val intent = Intent(pager.context, sent_loan_page::class.java)
                                    //intent.putExtra("updaterUUID", uuid)
                                    intent.putExtra("id", globalIt.id)
                                    pager.context.startActivity(intent)
                                }

                                if (it.person != null)
                                    user.setText(it.person.fullName() + " " + (if (it.person.personalNumber != null) it.person.personalNumber else ""))


                                loanSum.setText("${globalIt.loanSum} ${globalIt.currency.name}")

                                status.text = "${it.getStatus()}"


                                var reloadButton = item.findViewById(R.id.reloadStatus) as ImageView

                                val observer = reloadButton.viewTreeObserver

                                observer.addOnDrawListener {
                                    Snackbar.make(reloadButton, "ღილაკი დაიხატა", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show()
                                }

                                reloadButton.setOnClickListener {

                                    /*        var mDialog = BottomSheetDialog(view.context);

                                            mDialog.contentView(view.context.verticalLayout  {
                                                val name = editText()
                                                button("Say Hello") {
                                                    onClick { view.context.toast("Hello, ${name.text}!") }
                                                }
                                            })
                                                    .heightParam(ViewGroup.LayoutParams.WRAP_CONTENT)
                                                    .inDuration(500)
                                                    .cancelable(true)
                                                    .show();*/

                                    val dialog = ACProgressFlower.Builder(pager.getContext())
                                            .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                                            .themeColor(Color.WHITE)
                                            .text("გადამოწმება")
                                            .fadeColor(Color.DKGRAY).build()
                                    dialog.show()
                                    try {
                                        OnlineData.syncLoanStatus(globalIt, Action1 {
                                            if (it != null) {
                                                val loanStatus = it.data.errorMessage
                                                globalIt.status = loanStatus
                                                globalIt.save()
                                                status.setText(globalIt.getStatus())
                                                dialog.hide()
                                            } else {
                                                dialog.hide()
                                                Snackbar.make(reloadButton, "სტატუსის განახლების დროს მოხდა შეცდომა! \n ცადეთ მოგვიანებით.", Snackbar.LENGTH_LONG)
                                                        .setAction("Action", null).show()
                                            }

                                        })
                                    } catch (e: Exception) {
                                        view.context.toast("ვერ მოხდა სერვერთან დაკავშირება!")
                                        dialog.hide()
                                    }

                                }

                                view.addView(item)
                            }
                        }
                        if (annotation.filter.equals("2")) {
                            var first = true;
                            dataToLoad.filter { loan -> !loan.sent }.forEach {


                                var textColor = Color.WHITE
                                var color = pager.context.resources.getColor(R.color.grey)


                                val globalIt = it;

                                when (globalIt.product.name) {
                                    "U1" -> {
                                        textColor = Color.WHITE
                                        color = pager.context.resources.getColor(R.color.grey)
                                    }
                                    "A1" -> {
                                        textColor = Color.WHITE
                                        color = pager.context.resources.getColor(R.color.darkgrey)
                                    }
                                    else -> {
                                        textColor = Color.BLACK
                                        color = pager.context.resources.getColor(R.color.white)
                                    }
                                }
                                var item = inflater.inflate(R.layout.loanlistitem, null, false)


                                var productImage = item.findViewById(R.id.image) as ImageView
                                val generator = ColorGenerator.MATERIAL;
                                val color1 = generator.getRandomColor()
                                val drawable = TextDrawable.builder()
                                        .beginConfig()
                                        .textColor(textColor)
                                        .useFont(font1)
                                        .bold()
                                        .toUpperCase()
                                        .withBorder(12) /* thickness in px */
                                        .endConfig()
                                        .buildRoundRect("${globalIt.product.product}", color, 20)
                                productImage.setImageDrawable(drawable)

                                productImage.setOnClickListener {
                                    val intent = Intent(pager.context, DataFillActivity::class.java)
                                    intent.putExtra("class", Loan::class.java)

                                    //intent.putExtra("updaterUUID", uuid)
                                    intent.putExtra("id", globalIt.id)
                                    pager.context.startActivity(intent)
                                }


                                val user = item.findViewById(R.id.clientName) as TextView
                                val status = item.findViewById(R.id.status) as TextView
                                val loanSum = item.findViewById(R.id.loanSum) as TextView

                                if (it.person != null)
                                    user.setText(it.person.fullName() + " " + (if (it.person.personalNumber != null) it.person.personalNumber else ""))


                                loanSum.setText("${globalIt.loanSum} ${globalIt.currency.name}")

                                status.text = "${it.getStatus()}"


                                var sendButton = item.findViewById(R.id.reloadStatus) as ImageView

                                sendButton.setImageResource(R.drawable.cloud_up)

                                sendButton.setOnClickListener(globalIt.sendClick)
                                if(first&&StaticData.loginData!!.isSendLoanTutorial){
                                    first = false;
                                    var instructions =  TapTargetSequence(this.activity).targets(
                                            TapTarget.forView(productImage, "განაცხადის შესაცვლელად დააჭირეთ მითითებულ ღილაკს ",
                                                    "ეს ღილაკი გადაგიყვანთ განაცხადის ედიტირების გვერდზე საიდანაც შეძლებთ შემდგომ განაცხადის გაგზავნას.")
                                                    .outerCircleColor(R.color.colorPrimary)
                                                    .outerCircleAlpha(0.96f)
                                                    .targetCircleColor(R.color.white)
                                                    .titleTextSize(20)
                                                    .titleTextColor(R.color.white)
                                                    .descriptionTextSize(13)
                                                    .descriptionTextColor(R.color.white)
                                                    .textColor(R.color.white)
                                                    .textTypeface(Typeface.SANS_SERIF)
                                                    .dimColor(R.color.material_blue_grey_950)
                                                    .drawShadow(true)
                                                    .cancelable(false)
                                                    .tintTarget(false)
                                                    .transparentTarget(true)
                                                    .targetRadius(60),
                                            TapTarget.forView(sendButton, "შევსებული განაცხადის გადაგზავნა",
                                                    "განაცხადი გადაიგზავნება მხოლოდ იმ შემთხვევაში თუ ყველა სავალდებულო ველი შევსებულია")
                                                    .outerCircleColor(R.color.colorPrimary)
                                                    .outerCircleAlpha(0.96f)
                                                    .targetCircleColor(R.color.white)
                                                    .titleTextSize(20)
                                                    .titleTextColor(R.color.white)
                                                    .descriptionTextSize(13)
                                                    .descriptionTextColor(R.color.white)
                                                    .textColor(R.color.white)
                                                    .textTypeface(Typeface.SANS_SERIF)
                                                    .dimColor(R.color.material_blue_grey_950)
                                                    .drawShadow(true)
                                                    .cancelable(false)
                                                    .tintTarget(true)
                                                    .transparentTarget(false)
                                                    .targetRadius(60)).listener(object : TapTargetSequence.Listener {
                                        override fun onSequenceCanceled(lastTarget: TapTarget?) {

                                        }

                                        override fun onSequenceFinish() {
                                            StaticData.loginData!!.isSendLoanTutorial = false
                                            StaticData.loginData!!.save()
                                        }

                                        override fun onSequenceStep(lastTarget: TapTarget?, targetClicked: Boolean) {

                                        }
                                    })

                                    tapInstructions.put(annotation.page,instructions)
                                }

                                view.addView(item)


                            }
                        }
                    }


                    var viewFieldHolder = ViewFieldHolder();
                    viewFieldHolder.bindObject = myLocalObject;
                    viewFieldHolder.field = field;
                    viewFieldHolder.view = view
                    viewFieldHolder.paterns = annotation.visibilityPatern
                    fieldPaterns.add(viewFieldHolder)
                    viewPagesList.putIfAbsent(annotation.page, ConcurrentHashMap())
                    viewPagesList.get(annotation.page)!!.put(annotation.position, view);
                }


            }
            if (field.isAnnotationPresent(BooleanFieldTypeViewAnotation::class.java)) {
                val annotation = field.getAnnotation<BooleanFieldTypeViewAnotation>(BooleanFieldTypeViewAnotation::class.java)
                val name = annotation.name
                val defaultVal = annotation.defaultVal
                val view = inflater.inflate(R.layout.checkbox_with_name, null)
                val checkbox = view.findViewById(R.id.checkBox) as CheckBox
                checkbox.text = name
                val fieldValue = field.get(myLocalObject)
                var valueToSet = defaultVal;
                if (fieldValue != null) {
                    valueToSet = fieldValue as Boolean
                }

                checkbox.isChecked = valueToSet;



                checkbox.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
                    field.set(myLocalObject, isChecked)
                    visibilityCheck(fieldPaterns, fieldNameMap, myLocalObject)
                    if (autoSave) {
                        saveFun()
                    }
                })
                viewPagesList.putIfAbsent(annotation.page, ConcurrentHashMap())
                viewPagesList.get(annotation.page)!!.put(annotation.position, view);

            }
            if (field.isAnnotationPresent(DataGroupFieldTypeViewAnotation::class.java)) {
                val annotation = field.getAnnotation<DataGroupFieldTypeViewAnotation>(DataGroupFieldTypeViewAnotation::class.java)
                val name = annotation.name

                val view = inflater.inflate(R.layout.object_inner_layout, null) as RelativeLayout

                val linear = view.findViewById(R.id.linear) as LinearLayout


                val label = view.findViewById(R.id.label) as TextView

                label.text = annotation.name


                label.typeface = font1

                val viewPagesListInner: ConcurrentHashMap<Int, ConcurrentHashMap<Int, View>> =
                        viewToList(field.type.fields, inflater, fieldNameMap, fieldPaterns, field.type, field.get(myLocalObject), pager, true);


                viewPagesListInner[0]!!.forEach({
                    i, v ->
                    linear.addView(v)
                })



                viewPagesList.putIfAbsent(annotation.page, ConcurrentHashMap())
                viewPagesList.get(annotation.page)!!.put(annotation.position, view)


            }
            if (field.isAnnotationPresent(DataGroupContainerTypeViewAnotation::class.java)) {
                val annotation = field.getAnnotation<DataGroupContainerTypeViewAnotation>(DataGroupContainerTypeViewAnotation::class.java)
                val name = annotation.name


                val view = inflater.inflate(R.layout.multi_value_field_layout, null)
                val addBtn = view.findViewById(R.id.fieldName) as FancyButton
                val fieldValue = view.findViewById(R.id.fieldValue) as FancyButton



                addBtn.setText(annotation.name)
                addBtn.setCustomTextFont("fonts/font1.ttf")
                fieldValue.setCustomTextFont("fonts/font1.ttf")


                val updaterUUID = UUID.randomUUID().toString()

                val func = fun() {

                    val updateObj = field.type.getMethod("getById", Long::class.java).invoke(null, field.type.getMethod("getId").invoke(field.get(myLocalObject)) as Long)

                    field.set(myLocalObject, updateObj)

                    val countNumber = field.type.getMethod("getCount").invoke(field.get(myLocalObject)) as Int

                    fieldValue.setText("${countNumber}")
                }
                func()


                addBtn.setOnClickListener {
                    if (checkRequaredFieldsForSave()) {
                        saveFun()
                        val intent = Intent(pager.context, DataFillActivity::class.java)
                        intent.putExtra("class", field.type)
                        intent.putExtra("updaterUUID", updaterUUID)
                        val currentId = field.type.getMethod("getId").invoke(field.get(myLocalObject)) as Long
                        intent.putExtra("id", currentId)
                        pager.context.startActivity(intent)
                    }
                }


                StaticData.comboBoxUpdateFunctions.put(updaterUUID, func)


                viewPagesList.putIfAbsent(annotation.page, ConcurrentHashMap())
                viewPagesList.get(annotation.page)!!.put(annotation.position, view)
            }
            if (field.isAnnotationPresent(ButtonFieldTypeViewAnnotation::class.java)) {
                val annotation = field.getAnnotation<ButtonFieldTypeViewAnnotation>(ButtonFieldTypeViewAnnotation::class.java)
                val name = annotation.name


                val view = inflater.inflate(R.layout.button_layout_field, null)
                val btn = view.findViewById(R.id.button) as FancyButton
                btn.setText(name);
                btn.setCustomTextFont("fonts/font1.ttf")
                btn.setOnClickListener(field.get(bindObject) as View.OnClickListener)

                /*      val icon = annotation.icon

                      if(icon>0){
                          btn.setIconResource(icon)
                          //btn.setIconPadding(0,0,20,0)
                      }*/

                viewPagesList.putIfAbsent(annotation.page, ConcurrentHashMap())
                viewPagesList.get(annotation.page)!!.put(annotation.position, view)

            }

        }

        listForDataLoad.forEach {
            it()
        }

        return viewPagesList
    }


    public val fieldPaterns = ArrayList<ViewFieldHolder>()

    var clazz: Class<*>? = null

    var myLocalObject: Any? = null

    public var dataModel: Boolean? = null

    var activity:Activity? = null

    fun parse(clazz: Class<*>, bindObject: Any, onSave: View.OnClickListener?,
              saveText: String, autoSave: Boolean, fragmentManager: FragmentManager, pager: ViewPager, tabs: PagerSlidingTabStrip, saveVisible: Boolean = true, dataFillActivity: Activity) {

        this.activity = dataFillActivity

        this.clazz = clazz
        this.myLocalObject = bindObject

        var inflater = LayoutInflater.from(pager.context)
        val fields = clazz.fields
        val annotation = clazz.getAnnotation<ParserClassAnnotation>(ParserClassAnnotation::class.java)

        val fieldNameMap = HashMap<String, Field>()
        var titles = arrayOf("მთავარი")
        if (annotation.cols.isNotEmpty()) {
            titles = annotation.cols
        }
        this.dataModel = annotation.dataModel

        val adapter = CredoPageAdapter(fragmentManager, titles);
        val viewPagesListOuter: ConcurrentHashMap<Int, ConcurrentHashMap<Int, View>> = viewToList(fields, inflater, fieldNameMap, fieldPaterns, clazz, bindObject, pager, autoSave);




        visibilityCheck(fieldPaterns, fieldNameMap, bindObject)
        adapter.items = viewPagesListOuter
        pager.adapter = adapter;
        tabs.setViewPager(pager)
        tabs.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                val k = adapter.saveState()
                adapter.restoreState(k, null)
                var instruction = tapInstructions.get(position)
                if(instruction!=null){
                    instruction!!.start()
                }
            }

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        });




        pager.currentItem = 0

        val font1 = Typeface.createFromAsset(pager.context.getAssets(), "fonts/font1.ttf");
        tabs.applyRecursively { view ->
            when (view) {
                is EditText -> view.typeface = font1
                is TextView -> view.typeface = font1
            }
        }
        pager.applyRecursively { view ->
            when (view) {
                is EditText -> view.typeface = font1
                is TextView -> view.typeface = font1
            }
        }

        if (onSave != null && saveVisible && !autoSave) {


            val saveButton = FancyButton(pager.context)
            saveButton.setText(saveText)
            saveButton.setBackgroundColor(Color.parseColor("#3b5998"))
            saveButton.setFocusBackgroundColor(Color.parseColor("#5474b8"))
            saveButton.setTextSize(17)
            saveButton.setRadius(5)
            saveButton.setIconResource("\uf0c7")
            saveButton.setIconPosition(FancyButton.POSITION_LEFT)
            saveButton.setFontIconSize(30)

            val params = LinearLayout.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, 150)
            params.setMargins(50, 50, 50, 50);

            saveButton.setLayoutParams(params);

            saveButton.setOnClickListener(onSave)
            viewPagesListOuter.get(0)!!.put(100, saveButton);
        } else {

        }

    }

    fun visibilityCheck(fieldPaterns: ArrayList<ViewFieldHolder>, fieldNameMap: HashMap<String, Field>, bindObject: Any) {


        fieldPaterns.forEach {
            var visible = true


            if (it.paterns != null)
                it.paterns!!.forEach {
                    if (!it.isNullOrEmpty()) {
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
}



