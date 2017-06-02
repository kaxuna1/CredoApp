package credo.ge.credoapp;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;

import java.util.Iterator;
import java.util.Set;
import java.util.SortedMap;

/**
 * Created by kaxge on 5/31/2017.
 */

public class Functions {
    public static void viewsHandler(SortedMap<Integer, View> orderedList, LinearLayout linearLayout){
        Set<Integer> k = orderedList.keySet();
        Iterator<Integer> it = k.iterator();
        for (Iterator i = it; i.hasNext();) {
            Integer key = (Integer) i.next();
            View value = (View) orderedList.get(key);
            ViewParent parent = value.getParent();

            if(parent!=null){
                ViewGroup parentViewGroup = (ViewGroup) parent;
                parentViewGroup.removeAllViews();
            }

            linearLayout.addView(value);
        }
    }
}
