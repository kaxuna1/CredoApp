package credo.ge.credoapp

import android.app.Activity
import android.content.Context
import android.view.View
import com.orm.SugarRecord
import credo.ge.credoapp.models.OnlineDataModels.LoginData
import org.jetbrains.anko.intentFor
import java.util.HashMap

import credo.ge.credoapp.models.Person
import java.net.InetAddress
import android.net.NetworkInfo
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager



/**
 * Created by vakhtanggelashvili on 4/26/17.
 */

object StaticData {
    internal var data = HashMap<String,List<Any>>()
    internal var comboBoxUpdateFunctions = HashMap<String,()->Unit>()
    internal var loggedIn = false;
    internal var preferencesName = "CredoPreferences"
    internal var preferencesMode = 0
    var body: View? = null
    internal var loginData: LoginData?=null
    fun isInternetAvailable(): Boolean {
        try {
            val ipAddr = InetAddress.getByName("google.com") //You can replace it with your name
            return ipAddr.equals("")

        } catch (e: Exception) {
            return false
        }

    }

    public fun isNetworkAvailable(context:Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    var pdf: ByteArray?  = null





















}
