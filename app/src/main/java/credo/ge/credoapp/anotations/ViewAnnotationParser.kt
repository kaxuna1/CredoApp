package credo.ge.credoapp.anotations

import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout

import java.lang.reflect.*

import credo.ge.credoapp.models.TestModel
import android.text.Editable
import android.widget.DatePicker


/**
 * Created by vakhtanggelashvili on 4/25/17.
 */

class ViewAnnotationParser {
    fun parse(clazz: Class<*>, viewGorup: ViewGroup, bindObject: Any) {
        val fields = clazz.fields
        for (field in fields) {
            if (field.isAnnotationPresent(TextFieldTypeViewAnotation::class.java)) {
                val annotation = field.getAnnotation<TextFieldTypeViewAnotation>(TextFieldTypeViewAnotation::class.java)
                val name = annotation.name
                val type = annotation.type
                val deffaultValue = annotation.deffaultValue
                val editText = EditText(viewGorup.context)
                editText.setText(deffaultValue)

                var obj=clazz.cast(bindObject)

                viewGorup.addView(editText)
                editText.addTextChangedListener(object : TextWatcher {

                    override fun afterTextChanged(s: Editable) {}

                    override fun beforeTextChanged(s: CharSequence, start: Int,
                                                   count: Int, after: Int) {
                    }

                    override fun onTextChanged(s: CharSequence, start: Int,
                                               before: Int, count: Int) {
                        if (s.length != 0){
                            field.set(bindObject,s.toString())


                        }
                    }
                })
            }
            if (field.isAnnotationPresent(DateFieldTypeViewAnotation::class.java)){
                val annotation = field.getAnnotation<TextFieldTypeViewAnotation>(TextFieldTypeViewAnotation::class.java)
                val name = annotation.name
                var datePicker=DatePicker(viewGorup.context)
                viewGorup.addView(datePicker)
            }
        }
    }
}

