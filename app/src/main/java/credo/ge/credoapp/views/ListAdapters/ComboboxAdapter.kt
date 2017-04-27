package credo.ge.credoapp.views.ListAdapters

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import credo.ge.credoapp.R

/**
 * Created by vakhtanggelashvili on 4/26/17.
 */

class ComboboxAdapter(val data:List<Any>,val context:Context,val nameField_:String,val isMethod_:Boolean,clazz: Class<*>,textSiz:Float) : BaseAdapter() {
    private val mInflator: LayoutInflater
    var dataList:List<Any>
    var clazz: Class<*>
    var nameField:String
    var isMethod:Boolean
    var textSiz:Float
    init {
        this.nameField=nameField_
        this.isMethod=isMethod_
        this.dataList=data
        this.clazz=clazz
        this.textSiz=textSiz
        this.mInflator = LayoutInflater.from(context)
    }

    override fun getCount(): Int {
        return dataList.size
    }

    override fun getItem(position: Int): Any? {
        if(isMethod){
             return clazz.getMethod(nameField).invoke(dataList[position]) as String
        }else{
            return  clazz.getDeclaredField(nameField).get(dataList[position]).toString()
        }
    }

    override fun getItemId(position: Int): Long {
        return clazz.getMethod("getId").invoke(dataList.get(position)) as Long
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        val view: View?
        val vh: ListRowHolder
        if (convertView == null) {
            view = this.mInflator.inflate(R.layout.spinner_row, parent, false)
            vh = ListRowHolder(view)
            view.tag = vh
        } else {
            view = convertView
            vh = view.tag as ListRowHolder
        }
        var displayValue="";
        if(isMethod){
           displayValue = clazz.getMethod(nameField).invoke(dataList[position]) as String
        }else{
            displayValue = clazz.getDeclaredField(nameField).get(dataList[position]).toString()
        }
        vh.label.text = displayValue
        //vh.label.setTextSize(TypedValue.COMPLEX_UNIT_SP ,this.textSiz)
        return view
    }
    private class ListRowHolder(row: View?) {
        public val label: TextView

        init {
            this.label = row?.findViewById(R.id.textView) as TextView
        }
    }
}
