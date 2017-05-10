package credo.ge.credoapp.online;

import com.google.gson.JsonObject;

import java.util.Map;

import credo.ge.credoapp.models.OnlineDataModels.SyncResult;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by kaxge on 5/10/2017.
 */

public interface SyncDataServices {

    @POST("api/CallServiceMethod")
    Observable<SyncResult> syncData( @HeaderMap Map<String, String> headers,@Body JsonObject body);
}
