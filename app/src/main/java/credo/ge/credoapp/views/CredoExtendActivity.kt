package credo.ge.credoapp.views

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.amulyakhare.textdrawable.TextDrawable
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.DividerDrawerItem

import credo.ge.credoapp.models.Person
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IProfile
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import credo.ge.credoapp.*


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
    public fun makeDrawer(){

        val item1 = PrimaryDrawerItem().withIdentifier(1).withName("კლიენტები").withIcon(R.drawable.person)
        val item2 = SecondaryDrawerItem().withIdentifier(2).withName("სესხები").withIcon(R.drawable.bank)
        val item3 = SecondaryDrawerItem().withIdentifier(3).withName("სესხის დამატება").withIcon(R.drawable.loan)
        val item4 = SecondaryDrawerItem().withIdentifier(4).withName("კლიენტის დამატება").withIcon(R.drawable.person)
        val item5 = SecondaryDrawerItem().withIdentifier(5).withName("ავტომატური გადამოწმება").withIcon(R.drawable.refresh)
        val item6 = SecondaryDrawerItem().withIdentifier(6).withName("ფაილების ატვირთვა").withIcon(R.drawable.filesup)
        val font1 = Typeface.createFromAsset(applicationContext.getAssets(), "fonts/font1.ttf");

        var firstName = "";
        var lastName = "";
        val nameStrings = StaticData.loginData!!.name.split(" ")
        if (nameStrings.size == 2) {
            firstName = nameStrings[0].substring(0, 1)
            lastName = nameStrings[1].substring(0, 1)
        } else {
            firstName = StaticData.loginData!!.name.substring(0, 1)
        }

        var drawable = TextDrawable.builder()
                .beginConfig()
                .textColor(Color.WHITE)
                .useFont(font1)
                .bold()
                .toUpperCase()
                .height(462)
                .width(462)
                .withBorder(12) /* thickness in px */
                .endConfig()
                .buildRound("${firstName}${lastName}", resources.getColor(R.color.colorAccent))

        val headerResult = AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(credo.ge.credoapp.R.drawable.header)
                .addProfiles(
                        ProfileDrawerItem()
                                .withName(StaticData.loginData!!.name)
                                .withEmail(StaticData.loginData!!.position)
                                .withIcon(drawable)
                )
                .withOnAccountHeaderListener(AccountHeader.OnAccountHeaderListener { view, profile, currentProfile -> false })
                .build()


        DrawerBuilder().withActivity(this)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        item1,
                        DividerDrawerItem(),
                        item2,
                        DividerDrawerItem(),
                        item3,
                        DividerDrawerItem(),
                        item4,
                        DividerDrawerItem(),
                        item5,
                        DividerDrawerItem(),
                        item6
                ).withOnDrawerItemClickListener(object: Drawer.OnDrawerItemClickListener{
            override fun onItemClick(view: View?, position: Int, drawerItem: IDrawerItem<*, *>?): Boolean {


               return true;
            }

        }).build();
    }


}
