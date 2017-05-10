package credo.ge.credoapp

import com.orm.SugarRecord
import credo.ge.credoapp.models.OnlineDataModels.LoginData

import java.util.HashMap

import credo.ge.credoapp.models.Person

/**
 * Created by vakhtanggelashvili on 4/26/17.
 */

object StaticData {
    internal var classHashMap = HashMap<String, Class<SugarRecord<*>>>()
    internal var data = HashMap<String,List<Any>>()
    internal var comboBoxUpdateFunctions = HashMap<String,()->Unit>()
    internal var loggedIn = false;
    internal var preferencesName = "CredoPreferences"
    internal var preferencesMode = 0
    internal var loginData: LoginData?=null
}
