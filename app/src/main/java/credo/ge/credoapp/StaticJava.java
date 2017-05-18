package credo.ge.credoapp;

import android.util.Log;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import credo.ge.credoapp.models.OnlineDataModels.AutoCheckMethod;
import credo.ge.credoapp.models.PdfFile;
import credo.ge.credoapp.online.AutoCheckService;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.Okio;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by kaxge on 5/18/2017.
 */

public class StaticJava {
    static String TAG="kaxaaa";
    public void download(String token, String pn, String branchId, String byUserID, Action1<PdfFile> onSync){
        AutoCheckService downloadService = createService(AutoCheckService.class, "http://109.238.225.188:8081/");


        Map<String,String> headers=new HashMap<>();
        headers.put("Authorization","Bearer "+token);


        List<PdfFile> pdfFiles = PdfFile.find(PdfFile.class,"p_id = ?",pn);
        if(pdfFiles.size()>0){
            onSync.call(pdfFiles.get(0));
        }else{
            downloadService.downloadFileByUrlRx(headers,new Gson().toJsonTree(new AutoCheckMethod("GetPersonCreditInfo", branchId, byUserID, pn)).getAsJsonObject(),"api/CallServiceMethodFile")
                    .flatMap(processResponse())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(handleResult(onSync,pn));
        }

    }
    private Func1<Response<ResponseBody>, Observable<PdfFile>> processResponse() {
        return new Func1<Response<ResponseBody>, Observable<PdfFile>>() {
            @Override
            public Observable<PdfFile> call(Response<ResponseBody> responseBodyResponse) {
                return saveToDiskRx(responseBodyResponse);
            }
        };
    }

    private Observable<PdfFile> saveToDiskRx(final Response<ResponseBody> response) {
        return Observable.create(new Observable.OnSubscribe<PdfFile>() {
            @Override
            public void call(Subscriber<? super PdfFile> subscriber) {
                try {
                    String header = response.headers().get("Content-Disposition");
                    String filename = header.replace("attachment; filename=", "");



                    PdfFile pdfFile = new PdfFile();

                    pdfFile.pId = "";

                    pdfFile.pdf = response.body().bytes();
                    subscriber.onNext(pdfFile);
                    subscriber.onCompleted();
                } catch (IOException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        });
    }

    private Observer<PdfFile> handleResult(final Action1<PdfFile> onSync, final String pn) {

        return new Observer<PdfFile>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                Log.d(TAG, "Error " + e.getMessage());
            }

            @Override
            public void onNext(PdfFile file) {
                file.pId=pn;
             //   file.save();
                onSync.call(file);
            }
        };
    }

    public <T> T createService(Class<T> serviceClass, String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(new OkHttpClient.Builder().readTimeout(5, TimeUnit.MINUTES).build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
        return retrofit.create(serviceClass);
    }
}
