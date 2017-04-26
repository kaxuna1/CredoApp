package credo.ge.credoapp.views.ListAdapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

/**
 * Created by vakhtanggelashvili on 4/27/17.
 */

class ListAdapter(val data:List<Any>, val context: Context, val nameField_:String, val isMethod_:Boolean, clazz: Class<*>) : BaseAdapter() {


    override fun getCount(): Int {
        return 0
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View, parent: ViewGroup): View? {
        return null
    }
}
