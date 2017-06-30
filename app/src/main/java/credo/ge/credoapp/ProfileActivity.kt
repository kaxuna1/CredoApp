package credo.ge.credoapp

import android.graphics.Color
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import kotlinx.android.synthetic.main.activity_profile.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.singleTop

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)



        var firstName = "";
        val font1 = Typeface.createFromAsset(applicationContext.getAssets(), "fonts/font1.ttf");
        var lastName = "";
        val nameStrings = StaticData.loginData!!.name.split(" ")
        if (nameStrings.size == 2) {
            firstName = nameStrings[0].substring(0, 1)
            lastName = nameStrings[1].substring(0, 1)
        } else {
            firstName = StaticData.loginData!!.name.substring(0, 1)
        }


        val generator = ColorGenerator.MATERIAL;
        val color1 = generator.getRandomColor()

        var drawable = TextDrawable.builder()
                .beginConfig()
                .textColor(Color.WHITE)
                .useFont(font1)
                .bold()
                .toUpperCase()
                .withBorder(12) /* thickness in px */
                .endConfig()
                .buildRound("${firstName}${lastName}", resources.getColor(R.color.colorAccent))
        nameImage.setImageDrawable(drawable)
        nameField.text = "${nameStrings[0]} ${nameStrings[1]}"
        nameField.typeface = font1
        position.text = "${StaticData.loginData!!.position}"
        position.typeface = font1
        logout.setOnClickListener {
            var pref = this.applicationContext.getSharedPreferences(StaticData.preferencesName, StaticData.preferencesMode)
            val editor = pref!!.edit();
            editor!!.putLong("session", 0)
            editor!!.commit()
            startActivity(intentFor<LoginActivity>().singleTop())
            finish()
        }

        imageView4.setOnClickListener {
            finish()
        }
    }
}
