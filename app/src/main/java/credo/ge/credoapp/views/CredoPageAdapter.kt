package credo.ge.credoapp.views

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.View

import java.util.HashMap
import java.util.concurrent.ConcurrentHashMap

/**
 * Created by kaxge on 5/23/2017.
 */

class CredoPageAdapter(fragmentManager: FragmentManager, var titles: Array<String>) : FragmentPagerAdapter(fragmentManager) {


    var items: ConcurrentHashMap<Int, ConcurrentHashMap<Int, View>>? = null

    override fun getPageTitle(position: Int): CharSequence {
        return titles[position]
    }

    override fun getItem(position: Int): Fragment {


        var positionItems = items!!.get(position)

        val myFragment = MyFragment.newInstance(position,positionItems)
        return myFragment
    }

    override fun getCount(): Int {
        return titles.size
    }
}
