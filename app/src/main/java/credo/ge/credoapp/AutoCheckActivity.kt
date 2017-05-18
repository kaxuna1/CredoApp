package credo.ge.credoapp

import android.annotation.SuppressLint
import android.annotation.TargetApi
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
import android.util.Log
import com.github.barteksc.pdfviewer.util.Util
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



class AutoCheckActivity : AppCompatActivity() {
    private var progressDialog: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auto_check)
        showCustomFloatingView(applicationContext,true);
        progressDialog = ProgressDialog(this)
        backBtn.setOnClickListener {
            finish()
        }
        clear.setOnClickListener {
            PdfFile.deleteAll(PdfFile::class.java)
        }
        btn_search.setOnClickListener {
            val id = idNumber.text.toString()
            progressDialog!!.setMessage("მიმდინარეობს განახლება!")
            progressDialog!!.setCancelable(false)
            progressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
            progressDialog!!.show()
            StaticJava().download(StaticData.loginData!!.access_token, id, StaticData.loginData!!.branchId, StaticData.loginData!!.userId, Action1 {


                pdfView.fromBytes(it.pdf)
                        .enableSwipe(true) // allows to block changing pages using swipe
                        .swipeHorizontal(false)
                        .enableDoubletap(true)
                        .defaultPage(0)
                        .enableAntialiasing(true)
                        .load()

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
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == CHATHEAD_OVERLAY_PERMISSION_REQUEST_CODE) {
            showChatHead(applicationContext, false)
        } else if (requestCode == CUSTOM_OVERLAY_PERMISSION_REQUEST_CODE) {
            showCustomFloatingView(applicationContext, false)
        }
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