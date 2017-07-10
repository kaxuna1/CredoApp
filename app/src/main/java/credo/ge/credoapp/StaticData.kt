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
import credo.ge.credoapp.models.analysis.*


/**
 * Created by vakhtanggelashvili on 4/26/17.
 */

object StaticData {
    internal var data = HashMap<String, List<Any>>()
    internal var comboBoxUpdateFunctions = HashMap<String, () -> Unit>()
    internal var loggedIn = false;
    internal var preferencesName = "CredoPreferences"
    internal var preferencesMode = 0
    var body: View? = null
    internal var loginData: LoginData? = null
    fun isInternetAvailable(): Boolean {
        try {
            val ipAddr = InetAddress.getByName("google.com") //You can replace it with your name
            return ipAddr.equals("")

        } catch (e: Exception) {
            return false
        }

    }

    public fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    var pdf: ByteArray? = null


    fun checkBusiness(b: BusinessBalance): Boolean {
        var valid = true;
        Obligation.findbybusinessbalance(b.getId()!!).forEach {
            if (it.type == null || it.bank == null) {
                valid = false
            }
        }
        LongObligation.findbybusinessbalance(b.getId()!!).forEach {
            if (it.type == null || it.bank == null) {
                valid = false
            }
        }
        LoanObligation.findbybusinessbalance(b.getId()!!).forEach {
            if (it.type == null || it.bank == null) {
                valid = false
            }
        }
        LongLoanObligation.findbybusinessbalance(b.getId()!!).forEach {
            if (it.type == null || it.bank == null) {
                valid = false
            }
        }
        OtherObligation.findbybusinessbalance(b.getId()!!).forEach {
            if (it.type == null || it.bank == null) {
                valid = false
            }
        }
        CredoObligation.findbybusinessbalance(b.getId()!!).forEach {
            if (it.type == null || it.product == null) {
                valid = false
            }
        }
        return valid
    }

    fun checkPersonal(b: PersonalBalance): Boolean {
        var valid = true;
        Obligation.findbypersonalbalance(b.getId()!!).forEach {
            if (it.type == null || it.bank == null) {
                valid = false
            }
        }
        LongObligation.findbypersonalbalance(b.getId()!!).forEach {
            if (it.type == null || it.bank == null) {
                valid = false
            }
        }
        LoanObligation.findbypersonalbalance(b.getId()!!).forEach {
            if (it.type == null || it.bank == null) {
                valid = false
            }
        }
        LongLoanObligation.findbypersonalbalance(b.getId()!!).forEach {
            if (it.type == null || it.bank == null) {
                valid = false
            }
        }
        OtherObligation.findbypersonalbalance(b.getId()!!).forEach {
            if (it.type == null || it.bank == null) {
                valid = false
            }
        }
        CredoObligation.findbypersonalbalance(b.getId()!!).forEach {
            if (it.type == null || it.product == null) {
                valid = false
            }
        }
        return valid
    }

    var x: Double = 0.0
    var y: Double = 0.0


}
