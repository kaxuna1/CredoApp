package credo.ge.credoapp.online

import com.google.gson.Gson
import com.google.gson.JsonObject
import credo.ge.credoapp.models.OnlineDataModels.LoginReturn
import credo.ge.credoapp.models.OnlineDataModels.MethodName
import credo.ge.credoapp.models.OnlineDataModels.SyncResult
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action1
import rx.schedulers.Schedulers
import okhttp3.logging.HttpLoggingInterceptor
import rx.Observable
import java.util.*


/**
 * Created by vakhtanggelashvili on 5/4/17.
 */
object OnlineData {

    var retrofit1Url = Retrofit.Builder()
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://109.238.225.188:8081/")
            .build()

    fun login(email: String, password: String, onSession: Action1<LoginReturn>) {





        val sessionObservable = retrofit1Url.create<LoginServices>(LoginServices::class.java!!)
                .login(email, password)
        try {
            sessionObservable
                    .subscribeOn(Schedulers.newThread())
                    .doOnError { throwable -> throwable.printStackTrace() }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { session -> onSession.call(session) }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
    fun syncData(token:String,onSync: Action1<SyncResult>){







        val headers = mapOf<String,String>("Authorization" to "Bearer ${token}")

        val syncObservable = retrofit1Url.create<SyncDataServices>(SyncDataServices::class.java!!)
                .syncData(headers, Gson().toJsonTree(MethodName("GetAllSynchronizeList")).asJsonObject)
        try {
            syncObservable
                    .subscribeOn(Schedulers.newThread())
                    .doOnError { throwable -> throwable.printStackTrace() }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { syncData -> onSync.call(syncData) }
        }catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
