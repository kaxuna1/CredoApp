package credo.ge.credoapp.views

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.util.Log
import credo.ge.credoapp.DataFillActivity
import credo.ge.credoapp.LoginActivity

import credo.ge.credoapp.StaticData
import credo.ge.credoapp.StaticObjects
import credo.ge.credoapp.models.Person

/**
 * Created by vakhtanggelashvili on 5/5/17.
 */

open class CredoExtendActivity : AppCompatActivity() {



    override fun onResume() {
        super.onResume()
        StaticObjects.currentContext = this.applicationContext
        if (!StaticData.loggedIn) {
            //StaticData.loggedIn = true
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }


}
