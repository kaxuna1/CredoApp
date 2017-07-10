package credo.ge.credoapp;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.Snackbar;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.util.Base64;
import android.util.Log;
import android.widget.EditText;

import com.google.gson.Gson;
import com.sromku.simple.storage.InternalStorage;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    static String TAG = "kaxaaa";

    public void setKeyboard(EditText e){
        e.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
    }

    public void download(InternalStorage storage,
                         String token,
                         String pn,
                         String branchId,
                         String byUserID,
                         Action1<PdfFile> onSync,
                         ProgressDialog progressDialog) {
        AutoCheckService downloadService = createService(AutoCheckService.class, "http://109.238.225.188:8081/");



        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + token);


        try {
            byte[] file = storage.readFile("pdf", pn);
            PdfFile pdfFile = new PdfFile();

            pdfFile.pdf=file;

            onSync.call(pdfFile);

        } catch (Exception e) {
            downloadService.downloadFileByUrlRx(headers, new Gson().toJsonTree(new AutoCheckMethod("GetPersonCreditInfo",
                    branchId, byUserID, pn)).getAsJsonObject(), "api/CallServiceMethodFile")
                    .flatMap(processResponse())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(handleResult(onSync, pn,storage,progressDialog));
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

    private Observer<PdfFile> handleResult(final Action1<PdfFile> onSync, final String pn, final InternalStorage storage, final ProgressDialog progressDialog) {

        return new Observer<PdfFile>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                onSync.call(null);
                Log.d(TAG, "Error " + e.getMessage());
            }

            @Override
            public void onNext(PdfFile file) {
                file.pId = pn;

                storage.createFile("pdf",pn,file.pdf);
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

    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com"); //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }

    }

    public InputFilter getFilter(final String alowedChars){
        return new InputFilter() {

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

                for (int i = 0; i <source.length(); i++) {
                    Matcher matcher= Pattern.compile("[ა-ჰ0-9,-_.;:\\- ]").matcher(""+source.charAt(i));
                    if(!matcher.matches())
                        return "";
                }


                return null;
            }
        };
    }


    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality)
    {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    public InputFilter getFilterLengh(final int length) {
        return new InputFilter() {

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

                if(source.length()>length)
                    return "";


                return null;
            }
        };
    }
}
