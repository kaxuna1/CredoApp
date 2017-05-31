package credo.ge.credoapp.views

import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

import java.util.HashMap

import credo.ge.credoapp.R



/**
 * Created by kaxge on 5/23/2017.
 */

class MyFragment : android.support.v4.app.Fragment() {

    var linearLayout: LinearLayout? =  null


    var viewToAdd: HashMap<Int, View>? = null

    private val position: Int = 0
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {




        val rootView = inflater!!.inflate(R.layout.fragment_layout, container, false)
        ViewCompat.setElevation(rootView, 50f)
        linearLayout = rootView.findViewById(R.id.linearFormPlace) as LinearLayout

        val orderedList = viewToAdd!!.toSortedMap();
        linearLayout!!.removeAllViews()
        orderedList.forEach { t, u ->

            val parent = u.getParent()

            if(parent!=null){
                val parentViewGroup = parent  as ViewGroup
                parentViewGroup.removeAllViews()
            }




            linearLayout!!.addView(u);
            //  adapter.items[0].linearLayout.addView(u);
        }

        return rootView
    }

    companion object {

        private val ARG_POSITION = "position"

        private val view: View? = null


        fun newInstance(position: Int, positionItems: HashMap<Int, View>?): MyFragment {
            val f = MyFragment()
            f.viewToAdd = positionItems ?: HashMap<Int, View>()
            val b = Bundle()
            b.putInt(ARG_POSITION, position)
            f.arguments = b
            return f
        }
    }

}
