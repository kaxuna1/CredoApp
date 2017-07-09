package credo.ge.credoapp.online

import com.google.gson.Gson
import com.google.gson.JsonObject
import credo.ge.credoapp.StaticData
import credo.ge.credoapp.models.Loan
import credo.ge.credoapp.models.OnlineDataModels.*
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action1
import rx.schedulers.Schedulers
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rx.Observable
import rx.Subscriber
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList


/**
 * Created by vakhtanggelashvili on 5/4/17.
 */
object OnlineData {



    var retrofit1Url = Retrofit.Builder()
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client( OkHttpClient.Builder().readTimeout(15, TimeUnit.MINUTES).build())
            .baseUrl("http://10.195.9.121:8087/")
            .build()
    var retrofit1Url2 = Retrofit.Builder()
            .baseUrl("http://10.195.9.121:8087/")
            .client( OkHttpClient.Builder().readTimeout(15, TimeUnit.MINUTES).build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun login(email: String, password: String, onSync: Action1<LoginReturn>) {


        val sessionObservable = retrofit1Url.create<LoginServices>(LoginServices::class.java!!)
                .login(email, password)
        try {
            sessionObservable
                    .subscribeOn(Schedulers.newThread())
                    .doOnError { throwable -> throwable.printStackTrace() }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe ( object:Subscriber<LoginReturn>(){
                        override fun onNext(t: LoginReturn?) {
                            onSync.call(t)
                        }

                        override fun onError(e: Throwable?) {
                            e!!.printStackTrace();
                            onSync.call(null)
                        }

                        override fun onCompleted() {

                        }

                    } )
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun syncData(token: String, onSync: Action1<SyncResult>) {


        val headers = mapOf<String, String>("Authorization" to "Bearer ${token}")

        val syncObservable = retrofit1Url.create<SyncDataServices>(SyncDataServices::class.java!!)
                .syncData(headers, Gson().toJsonTree(MethodName("GetAllSynchronizeList")).asJsonObject)
        try {
            syncObservable
                    .subscribeOn(Schedulers.newThread())
                    .doOnError { throwable -> throwable.printStackTrace() }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object:Subscriber<SyncResult>(){
                        override fun onNext(t: SyncResult?) {
                            onSync.call(t)
                        }

                        override fun onError(e: Throwable?) {
                            e!!.printStackTrace();
                            onSync.call(null)
                        }

                        override fun onCompleted() {

                        }

                    })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun syncLoan(loan: Loan, onSync: Action1<SyncLoanResult>){

        val headers = mapOf<String, String>("Authorization" to "Bearer ${StaticData.loginData!!.access_token}")

        val syncLoanObservable = retrofit1Url.create<SyncDataServices>(SyncDataServices::class.java!!)
                .syncLoan(headers, Gson().toJsonTree(MethodName("GetProductFullList",loan)).asJsonObject)

        try {
            syncLoanObservable
                    .subscribeOn(Schedulers.newThread())
                    .doOnError { throwable -> throwable.printStackTrace() }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe (object:Subscriber<SyncLoanResult>(){
                        override fun onNext(t: SyncLoanResult?) {
                            onSync.call(t)
                        }

                        override fun onError(e: Throwable?) {
                            e!!.printStackTrace();
                            onSync.call(null)
                        }

                        override fun onCompleted() {

                        }

                    } )
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun syncLoanScoring(loan: Loan, onSync: Action1<SyncLoanResult>){

        val headers = mapOf<String, String>("Authorization" to "Bearer ${StaticData.loginData!!.access_token}")

        val syncLoanObservable = retrofit1Url.create<SyncDataServices>(SyncDataServices::class.java!!)
                .syncLoan(headers, Gson().toJsonTree(MethodName("GetPurposeList",loan)).asJsonObject)

        try {
            syncLoanObservable
                    .subscribeOn(Schedulers.newThread())
                    .doOnError { throwable -> throwable.printStackTrace() }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe (object:Subscriber<SyncLoanResult>(){
                        override fun onNext(t: SyncLoanResult?) {
                            onSync.call(t)
                        }

                        override fun onError(e: Throwable?) {
                            e!!.printStackTrace();
                            onSync.call(null)
                        }

                        override fun onCompleted() {

                        }

                    } )
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
    fun syncLoanStatus(loan: Loan, onSync: Action1<SyncLoanResult>){

        val headers = mapOf<String, String>("Authorization" to "Bearer ${StaticData.loginData!!.access_token}")

        val syncLoanObservable = retrofit1Url.create<SyncDataServices>(SyncDataServices::class.java!!)
                .syncLoan(headers, Gson().toJsonTree(MethodName("GetConsulList",loan)).asJsonObject)

        try {
            syncLoanObservable
                    .subscribeOn(Schedulers.newThread())
                    .doOnError { throwable -> throwable.printStackTrace() }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe (object:Subscriber<SyncLoanResult>(){
                        override fun onNext(t: SyncLoanResult?) {
                            onSync.call(t)
                        }

                        override fun onError(e: Throwable?) {
                            e!!.printStackTrace();
                            onSync.call(null)
                        }

                        override fun onCompleted() {

                        }

                    } )
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
    fun syncCurrencies(onSync: Action1<SyncLoanResult>){

        val headers = mapOf<String, String>("Authorization" to "Bearer ${StaticData.loginData!!.access_token}")

        val syncLoanObservable = retrofit1Url.create<SyncDataServices>(SyncDataServices::class.java!!)
                .syncLoan(headers, Gson().toJsonTree(MethodName("GetLoanOfficerList")).asJsonObject)

        try {
            syncLoanObservable
                    .subscribeOn(Schedulers.newThread())
                    .doOnError { throwable -> throwable.printStackTrace() }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe (object:Subscriber<SyncLoanResult>(){
                        override fun onNext(t: SyncLoanResult?) {
                            onSync.call(t)
                        }

                        override fun onError(e: Throwable?) {
                            e!!.printStackTrace();
                            onSync.call(null)
                        }

                        override fun onCompleted() {

                        }

                    } )
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
    fun syncUserName(userId: String,token:String, onSync: Action1<SyncLoanResult>){

        val headers = mapOf<String, String>("Authorization" to "Bearer ${token}")

        val syncLoanObservable = retrofit1Url.create<SyncDataServices>(SyncDataServices::class.java!!)
                .syncLoan(headers, Gson().toJsonTree(MethodName("GetVillageList",userId)).asJsonObject)

        try {
            syncLoanObservable
                    .subscribeOn(Schedulers.newThread())
                    .doOnError { throwable -> throwable.printStackTrace() }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe (object:Subscriber<SyncLoanResult>(){
                        override fun onNext(t: SyncLoanResult?) {
                            onSync.call(t)
                        }

                        override fun onError(e: Throwable?) {
                            e!!.printStackTrace();
                            onSync.call(null)
                        }

                        override fun onCompleted() {

                        }

                    } )
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
    fun syncFiles(images:List<ImageDataModel>,loan:Loan, onSync: Action1<SyncLoanResult>){

        val headers = mapOf<String, String>("Authorization" to "Bearer ${StaticData.loginData!!.access_token}")

        val syncLoanObservable = retrofit1Url.create<SyncDataServices>(SyncDataServices::class.java!!)
                .syncLoan(headers, Gson().toJsonTree(MethodName("GetAllDictionariesForMobile",loan,images,StaticData!!.loginData!!.userId)).asJsonObject)

        try {
            syncLoanObservable
                    .subscribeOn(Schedulers.newThread())
                    .doOnError { throwable -> throwable.printStackTrace() }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe (object:Subscriber<SyncLoanResult>(){
                        override fun onNext(t: SyncLoanResult?) {
                            onSync.call(t)
                        }

                        override fun onError(e: Throwable?) {
                            e!!.printStackTrace();
                            onSync.call(null)
                        }

                        override fun onCompleted() {

                        }

                    } )
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
    fun autoCheck(token: String, pn: String, branchId: String, byUserID: String, onSync: Action1<ResponseBody>) {


        val headers = mapOf<String, String>("Authorization" to "Bearer ${token}")

        val callObj = retrofit1Url2.create(AutoCheckService::class.java)
                .autoCheck(headers, Gson().toJsonTree(AutoCheckMethod("GetPersonCreditInfo", branchId, byUserID, pn)).asJsonObject)
        callObj.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                if (response!!.isSuccessful) {
//                    val k=response.body().bytes()
                    onSync.call(response.body());
                }
                //To change body of created functions use File | Settings | File Templates.
            }

            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                t!!.printStackTrace()
                //To change body of created functions use File | Settings | File Templates.
            }

        })

    }
}































