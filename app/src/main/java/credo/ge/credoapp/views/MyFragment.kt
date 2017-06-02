package credo.ge.credoapp.views

import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import credo.ge.credoapp.Functions

import credo.ge.credoapp.R
import java.util.concurrent.ConcurrentHashMap


/**
 * Created by kaxge on 5/23/2017.
 */

class MyFragment : android.support.v4.app.Fragment() {

    var linearLayout: LinearLayout? =  null


    var viewToAdd: ConcurrentHashMap<Int, View>? = null

    private val position: Int = 0
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {




        val rootView = inflater!!.inflate(R.layout.fragment_layout, container, false)
        ViewCompat.setElevation(rootView, 50f)
        linearLayout = rootView.findViewById(R.id.linearFormPlace) as LinearLayout

        val orderedList = viewToAdd!!.toSortedMap();
        linearLayout!!.removeAllViews()
        try {
            Functions.viewsHandler(orderedList,linearLayout);
        }catch (e:Exception){
            e.printStackTrace()
        }catch (e2:NoClassDefFoundError){

        }


        return rootView
    }

    companion object {

        private val ARG_POSITION = "position"

        private val view: View? = null


        fun newInstance(position: Int, positionItems: ConcurrentHashMap<Int, View>?): MyFragment {
            val f = MyFragment()
            f.viewToAdd = positionItems ?: ConcurrentHashMap<Int, View>()
            val b = Bundle()
            b.putInt(ARG_POSITION, position)
            f.arguments = b
            return f
        }
    }

}
