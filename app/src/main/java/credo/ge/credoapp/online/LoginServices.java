package credo.ge.credoapp.online;

import credo.ge.credoapp.models.OnlineDataModels.LoginReturn;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by vakhtanggelashvili on 5/4/17.
 */

public interface LoginServices {
    @FormUrlEncoded
    @POST("api/Authentication")
    Observable<LoginReturn> login(@Field("Username") String email, @Field("Password") String password);
}
