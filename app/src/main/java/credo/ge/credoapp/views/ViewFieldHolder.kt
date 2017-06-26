package credo.ge.credoapp.views

import android.view.View

import java.lang.reflect.Field

/**
 * Created by vakhtanggelashvili on 5/2/17.
 */

class ViewFieldHolder {
    var field: Field? = null
    var view: View? = null
    var bindObject:Any? = null
    var paterns:Array<String>? = null
    var requiredForSave:Boolean = false;
}
