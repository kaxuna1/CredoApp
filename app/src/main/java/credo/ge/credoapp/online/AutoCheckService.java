package credo.ge.credoapp.online;

import com.google.gson.JsonObject;

import java.util.Map;

import credo.ge.credoapp.models.OnlineDataModels.SyncResult;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by kaxge on 5/18/2017.
 */

public interface AutoCheckService {
    @POST("api/CallServiceMethodFile")
    @Streaming
    Call<ResponseBody> autoCheck(@HeaderMap Map<String, String> headers, @Body JsonObject body);


    // Retrofit 2 GET request for rxjava
    @Streaming
    @POST
    Observable<Response<ResponseBody>> downloadFileByUrlRx(@HeaderMap Map<String, String> headers, @Body JsonObject body,@Url String fileUrl);
}
