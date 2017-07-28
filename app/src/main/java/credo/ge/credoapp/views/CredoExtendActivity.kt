package credo.ge.credoapp.views

import android.annotation.TargetApi
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.mikepenz.fontawesome_typeface_library.FontAwesome
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
import com.sromku.simple.storage.InternalStorage
import com.sromku.simple.storage.SimpleStorage
import credo.ge.credoapp.*
import credo.ge.credoapp.models.Loan
import credo.ge.credoapp.models.ViewModels.FileUploadView
import credo.ge.credoapp.models.ViewModels.LoansViewer
import kotlinx.android.synthetic.main.activity_auto_check.*
import org.jetbrains.anko.*
import rx.functions.Action1


/**
 * Created by vakhtanggelashvili on 5/5/17.
 */

open class CredoExtendActivity : AppCompatActivity() {


    private var progressDialog: ProgressDialog? = null




    private var storage: InternalStorage? = null


    override fun onResume() {
        super.onResume()
        StaticObjects.currentContext = this.applicationContext
        if (!StaticData.loggedIn) {
            //StaticData.loggedIn = true
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }

    open fun makeDrawer() {

        progressDialog = ProgressDialog(this)

        storage = SimpleStorage.getInternalStorage(applicationContext);
        storage!!.createDirectory("pdf");
        storage!!.createFile("pdf", "kaxa", "some content of the file");

        var globalIt = this

        val generator = ColorGenerator.MATERIAL;


        var loans = Loan.findAll(Loan::class.java);


        var loanItems: ArrayList<SecondaryDrawerItem> = ArrayList();

        var drawableAll = TextDrawable.builder()
                .beginConfig()
                .textColor(Color.WHITE)
                .bold()
                .toUpperCase()
                .withBorder(3) /* thickness in px */
                .endConfig()
                .buildRound("All", resources.getColor(R.color.colorAccent))

        loanItems.add(SecondaryDrawerItem().withIdentifier(22).withName("ყველა").withIcon(drawableAll))
        loans.forEach {

            var colorId = R.color.colorAccent

            if(it.sent){
               colorId = R.color.md_green_200
            }

            var drawable = TextDrawable.builder()
                    .beginConfig()
                    .textColor(Color.WHITE)
                    .bold()
                    .toUpperCase()
                    .withBorder(3) /* thickness in px */
                    .endConfig()
                    .buildRound(it.product.name, resources.getColor(colorId))

            loanItems.add(SecondaryDrawerItem().withIdentifier(it.id+2000).withName(it.name).withIcon(drawable))


        }


        val item1 = PrimaryDrawerItem().withIdentifier(1).withName("კლიენტები").withIcon(FontAwesome.Icon.faw_user)
        val item2 = SecondaryDrawerItem().withIdentifier(2).withName("სესხები")
                .withIconTintingEnabled(true)
                .withIcon(R.drawable.bank)
                .withIsExpanded(false)
                .withSubItems(loanItems.toList())
        val item3 = SecondaryDrawerItem().withIdentifier(3).withName("სესხის დამატება").withIcon(FontAwesome.Icon.faw_plus)
        val item4 = SecondaryDrawerItem().withIdentifier(4).withName("კლიენტის დამატება").withIcon(FontAwesome.Icon.faw_user_plus)
        val item5 = SecondaryDrawerItem().withIdentifier(5).withName("ავტომატური გადამოწმება").withIcon(FontAwesome.Icon.faw_refresh)
        val item6 = SecondaryDrawerItem().withIdentifier(6).withName("ფაილების ატვირთვა").withIcon(FontAwesome.Icon.faw_cloud_upload)
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
                .withActivity(globalIt)
                .withHeaderBackground(credo.ge.credoapp.R.drawable.header)
                .addProfiles(
                        ProfileDrawerItem()
                                .withName(StaticData.loginData!!.name)
                                .withEmail(StaticData.loginData!!.position)
                                .withIcon(drawable)
                )
                .withOnAccountHeaderListener(AccountHeader.OnAccountHeaderListener { view, profile, currentProfile -> false })
                .build()



        var drawer = DrawerBuilder().withActivity(globalIt)
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
                )
                .addStickyDrawerItems(
                        SecondaryDrawerItem()
                                .withName("გამოსვლა")
                                .withIcon(FontAwesome.Icon.faw_sign_out)
                                .withIdentifier(25)).withOnDrawerItemClickListener(object : Drawer.OnDrawerItemClickListener {
            override fun onItemClick(view: View?, position: Int, drawerItem: IDrawerItem<*, *>?): Boolean {
                when (drawerItem!!.identifier) {
                    1L -> {
                        val intent = Intent(applicationContext, data_list_activity::class.java)
                        intent.putExtra("class", Person::class.java)
                        intent.putExtra("nameFieldMethodName", "fullName")
                        startActivity(intent)
                        return false;
                    }
                    2L -> {
                        return true;
                    }
                    3L -> {
                        val intent = Intent(applicationContext, DataFillActivity::class.java)
                        intent.putExtra("class", Loan::class.java)
                        intent.putExtra("autosave", true)
                        startActivity(intent)
                        return false;
                    }
                    4L -> {
                        val intent = Intent(applicationContext, DataFillActivity::class.java)
                        intent.putExtra("class", Person::class.java)
                        intent.putExtra("autosave", true)
                        startActivity(intent)
                    }
                    5L -> {
                        /*val intent = Intent(applicationContext, AutoCheckActivity::class.java)



                        startActivity(intent)
                        overridePendingTransition(R.anim.trans_left_in,
                                R.anim.trans_left_out)*/


                        alert ("ავტომატური გადამოწმება") {
                            customView {
                                var pnField = editText()

                                yesButton {
                                    val id = pnField.text.toString()
                                    progressDialog!!.setMessage("მიმდინარეობს გადამოწმება!")
                                    progressDialog!!.setCancelable(false)
                                    progressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
                                    progressDialog!!.show()
                                    StaticJava().download(storage, StaticData.loginData!!.access_token,
                                            id, StaticData.loginData!!.branchId, StaticData.loginData!!.userId,
                                            Action1 {

                                                if(it != null){
                                                    StaticData.pdf = it.pdf


                                                    /* floaty = Floaty.createInstance(this, head, body, NOTIFICATION_ID, notification, object : FloatyOrientationListener {
                                                         override
                                                         fun beforeOrientationChange(floaty: Floaty) {
                                                             Toast.makeText(me, "Orientation Change Start", Toast.LENGTH_SHORT).show();
                                                         }

                                                         override
                                                         fun afterOrientationChange(floaty: Floaty) {
                                                             Toast.makeText(me, "Orientation Change End", Toast.LENGTH_SHORT).show();
                                                         }
                                                     });*/

                                                    //
                                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                        if (Settings.canDrawOverlays(applicationContext)) {
                                                            val startHoverIntent = Intent(applicationContext, SingleSectionHoverMenuService::class.java)
                                                            startService(startHoverIntent)
                                                        } else {
                                                            val intent2 = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                                                                    Uri.parse("package:" + packageName))
                                                            startActivityForResult(intent2, 1234)
                                                        }

                                                    } else {
                                                        val startHoverIntent = Intent(applicationContext, SingleSectionHoverMenuService::class.java)
                                                        startService(startHoverIntent)
                                                    }






                                                    progressDialog!!.hide()

                                                }else{

                                                    progressDialog!!.hide()

                                                }



                                            }, progressDialog)
                                }
                                noButton {

                                }
                            }

                        }.show()
                        return false;
                    }
                    6L -> {
                        val intent = Intent(applicationContext, DataFillActivity::class.java)
                        intent.putExtra("class", FileUploadView::class.java)
                        intent.putExtra("autosave", false)
                        intent.putExtra("hideSave", true)
                        startActivity(intent)
                        return false;
                    }
                    22L -> {
                        val intent = Intent(applicationContext, DataFillActivity::class.java)
                        intent.putExtra("class", LoansViewer::class.java)
                        intent.putExtra("autosave", false)
                        intent.putExtra("hideSave", true)
                        startActivity(intent)
                        return false;
                    }
                    25L -> {
                        var pref = applicationContext.getSharedPreferences(StaticData.preferencesName, StaticData.preferencesMode)
                        val editor = pref!!.edit();
                        editor!!.putLong("session", 0)
                        editor!!.commit()
                        startActivity(intentFor<LoginActivity>().singleTop())
                        finish()
                        return false;
                    }
                    else -> {
                        if(drawerItem.identifier>2000&&drawerItem.identifier<15000){
                            val id = drawerItem.identifier-2000;
                            val loan = Loan.findById(Loan::class.java,id)
                            if(loan.sent){
                                val intent = Intent(applicationContext, sent_loan_page::class.java)


                                //intent.putExtra("updaterUUID", uuid)
                                intent.putExtra("id", id)
                                startActivity(intent)
                            }else{
                                val intent = Intent(applicationContext, DataFillActivity::class.java)
                                intent.putExtra("class", Loan::class.java)

                                intent.putExtra("hideSave", true)
                                intent.putExtra("autosave", true)
                                //intent.putExtra("updaterUUID", uuid)
                                intent.putExtra("id", id)
                                startActivity(intent)
                            }


                        }
                        return false;
                    }
                }

                return true;
            }

        }).build()
    }

    @TargetApi(Build.VERSION_CODES.M)
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val startHoverIntent = Intent(applicationContext, SingleSectionHoverMenuService::class.java)
        startService(startHoverIntent)
    }

}
