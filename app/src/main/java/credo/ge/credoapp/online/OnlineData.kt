package credo.ge.credoapp.online

import credo.ge.credoapp.models.OnlineDataModels.LoginReturn
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action1
import rx.schedulers.Schedulers

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
}
