package credo.ge.credoapp.views.ListAdapters

import android.content.Context
import android.graphics.Typeface
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import credo.ge.credoapp.R
import credo.ge.credoapp.models.Loan
import mehdi.sakout.fancybuttons.FancyButton

/**
 * Created by vakhtanggelashvili on 4/26/17.
 */

class ReflectionAdapterAdapter(val data: List<Any>, val context: Context, val nameField_: String, val isMethod_: Boolean, clazz: Class<*>, textSiz: Float,type_:String = "",filter_:String = "") : BaseAdapter() {
    private val mInflator: LayoutInflater
    var dataList: List<Any>
    var clazz: Class<*>
    var nameField: String
    var isMethod: Boolean
    var textSiz: Float
    var type:String
    var filter:String

    var nameList:ArrayList<String> = ArrayList();
    val font1: Typeface
    init {
        this.nameField = nameField_
        this.isMethod = isMethod_
        this.dataList = data
        this.clazz = clazz
        this.textSiz = textSiz
        this.mInflator = LayoutInflater.from(context)
        this.type = type_
        this.filter = filter_

        font1 = Typeface.createFromAsset(context.getAssets(), "fonts/font1.ttf");

    }

    override fun getCount(): Int {
        return dataList.size
    }

    override fun getItem(position: Int): Any? {
        if (isMethod) {
            return clazz.getMethod(nameField).invoke(dataList[position]) as String
        } else {
            return clazz.getDeclaredField(nameField).get(dataList[position]).toString()
        }
    }

    override fun getItemId(position: Int): Long {
        return clazz.getMethod("getId").invoke(dataList.get(position)) as Long
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {



        var view: View? = View(this.mInflator.context)

        if(type.isNullOrEmpty()){
            val vh: ListRowHolder
            if (convertView == null) {
                view = this.mInflator.inflate(R.layout.spinner_row, parent, false)
                vh = ListRowHolder(view)
                view.tag = vh
            } else {
                view = convertView
                vh = view.tag as ListRowHolder
            }
            var displayValue = "";
            if (isMethod) {
                displayValue = clazz.getMethod(nameField).invoke(dataList[position]) as String
            } else {
                displayValue = clazz.getDeclaredField(nameField).get(dataList[position]).toString()
            }

            nameList.add(displayValue)

            vh.label.text = displayValue
            vh.label.typeface = font1
        }
        if(type == "loan"){
            val vh: ListRowHolderLoan
            if (convertView == null) {
                view = this.mInflator.inflate(R.layout.loan_item_view, parent, false)
                vh = ListRowHolderLoan(view)
                view.tag = vh
            } else {
                view = convertView
                vh = view.tag as ListRowHolderLoan
            }
            var item = dataList[position] as Loan


            if(item.person!= null)
            vh.user.setText( item.person.fullName()+" "+(if(item.person.personalNumber!=null)item.person.personalNumber else ""))
            vh.status.setText(item.status)
            var pString = "";
            if(item.product!=null){
                pString+=item.product.product+" "
            }
            pString+=item.loanSum;

            vh.product.setText(pString)
        }

        //vh.label.setTextSize(TypedValue.COMPLEX_UNIT_SP ,this.textSiz)
        return view!!
    }

    private class ListRowHolder(row: View?) {
        public val label: TextView

        init {
            this.label = row?.findViewById(R.id.textView) as TextView
        }
    }
    private class ListRowHolderLoan(row:View?){
        public val user:FancyButton
        public val status:FancyButton
        public val product:FancyButton
        init {
            user = row?.findViewById(R.id.nameSurname) as FancyButton
            status = row?.findViewById(R.id.status) as FancyButton
            product = row?.findViewById(R.id.productSum) as FancyButton
        }
    }
}
