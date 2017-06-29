package credo.ge.credoapp

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.PendingIntent
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.support.design.widget.Snackbar
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import com.bezyapps.floatieslibrary.Floaty
import com.bezyapps.floatieslibrary.FloatyOrientationListener
import com.github.barteksc.pdfviewer.PDFView
import com.github.barteksc.pdfviewer.util.Util
import com.sromku.simple.storage.InternalStorage
import com.sromku.simple.storage.SimpleStorage
import credo.ge.credoapp.models.PdfFile
import credo.ge.credoapp.online.OnlineData
import credo.ge.credoapp.views.ChatHeadService
import credo.ge.credoapp.views.CustomFloatingViewService
import kotlinx.android.synthetic.main.activity_auto_check.*
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action1
import rx.observables.StringObservable
import rx.schedulers.Schedulers
import credo.ge.credoapp.views.FloatingViewControlFragment
import credo.ge.credoapp.views.SingleSectionHoverMenuService
import kotlinx.android.synthetic.main.float_body.*


class AutoCheckActivity : AppCompatActivity() {
    private var progressDialog: ProgressDialog? = null


    //var floaty: Floaty? = null
    private val NOTIFICATION_ID = 1500
    val PERMISSION_REQUEST_CODE = 16


    private var storage: InternalStorage? = null



    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auto_check)
        progressDialog = ProgressDialog(this)
        val me = this;
        storage = SimpleStorage.getInternalStorage(applicationContext);
        storage!!.createDirectory("pdf");
        storage!!.createFile("pdf", "kaxa", "some content of the file");


        backBtn.setOnClickListener {
            finish()
        }
        clear.setOnClickListener {
            PdfFile.deleteAll(PdfFile::class.java)
        }

        val head = LayoutInflater.from(this).inflate(R.layout.float_head, null);

        StaticData.body = LayoutInflater.from(this).inflate(R.layout.float_body, null)
        btn_search.setOnClickListener {
            val id = idNumber.text.toString()
            progressDialog!!.setMessage("მიმდინარეობს განახლება!")
            progressDialog!!.setCancelable(false)
            progressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
            progressDialog!!.show()
            StaticJava().download(storage,StaticData.loginData!!.access_token, id, StaticData.loginData!!.branchId, StaticData.loginData!!.userId, Action1 {



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
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(Settings.canDrawOverlays(this)){
                        val startHoverIntent =  Intent(applicationContext, SingleSectionHoverMenuService::class.java)
                        startService(startHoverIntent)
                    }else{
                        val intent2 = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                                Uri.parse("package:" + packageName))
                        startActivityForResult(intent2, 1234)
                    }

                }else{
                    val startHoverIntent =  Intent(applicationContext, SingleSectionHoverMenuService::class.java)
                    startService(startHoverIntent)
                }






                progressDialog!!.hide()
                Snackbar.make(btn_search, "PDF ჩაიტვირთა", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
                Log.d("pdfDownload", "Done")

            })
        }


    }

    private val TAG = "FloatingViewControl"

    /**
     * シンプルなFloatingViewを表示するフローのパーミッション許可コード
     */
    private val CHATHEAD_OVERLAY_PERMISSION_REQUEST_CODE = 100

    /**
     * カスタマイズFloatingViewを表示するフローのパーミッション許可コード
     */
    private val CUSTOM_OVERLAY_PERMISSION_REQUEST_CODE = 101

    @TargetApi(Build.VERSION_CODES.M)
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val startHoverIntent =  Intent(applicationContext, SingleSectionHoverMenuService::class.java)
        startService(startHoverIntent)
    }

    /**
     * シンプルなFloatingViewの表示

     * @param context                 Context
     * *
     * @param isShowOverlayPermission 表示できなかった場合に表示許可の画面を表示するフラグ
     */
    @SuppressLint("NewApi")
    private fun showChatHead(context: Context, isShowOverlayPermission: Boolean) {
        // API22以下かチェック
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {
            context.startService(Intent(context, ChatHeadService::class.java))
            return
        }

        // 他のアプリの上に表示できるかチェック
        if (Settings.canDrawOverlays(context)) {
            context.startService(Intent(context, ChatHeadService::class.java))
            return
        }

        // オーバレイパーミッションの表示
        if (isShowOverlayPermission) {
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + context.packageName))
            startActivityForResult(intent, CHATHEAD_OVERLAY_PERMISSION_REQUEST_CODE)
        }
    }

    /**
     * カスタマイズFloatingViewの表示

     * @param context                 Context
     * *
     * @param isShowOverlayPermission 表示できなかった場合に表示許可の画面を表示するフラグ
     */
    @SuppressLint("NewApi")
    private fun showCustomFloatingView(context: Context, isShowOverlayPermission: Boolean) {
        // API22以下かチェック
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {
            context.startService(Intent(context, CustomFloatingViewService::class.java))
            return
        }

        // 他のアプリの上に表示できるかチェック
        if (Settings.canDrawOverlays(context)) {
            context.startService(Intent(context, CustomFloatingViewService::class.java))
            return
        }

        // オーバレイパーミッションの表示
        if (isShowOverlayPermission) {
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + context.packageName))
            startActivityForResult(intent, CUSTOM_OVERLAY_PERMISSION_REQUEST_CODE)
        }
    }


}