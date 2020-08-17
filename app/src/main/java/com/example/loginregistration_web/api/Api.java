package com.example.loginregistration_web.api;

import com.example.loginregistration_web.models.CardDetails;
import com.example.loginregistration_web.models.DefaultResponse;
import com.example.loginregistration_web.models.ProgressBarPercent;
import com.example.loginregistration_web.models.ProgressPercent;
import com.example.loginregistration_web.models.RegistrationResponse;
import com.example.loginregistration_web.models.LoginResponse;
import com.example.loginregistration_web.models.TaskDetails;
import com.example.loginregistration_web.models.UploadedCards;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface Api {

    @FormUrlEncoded
    @POST("registerUser.php")
    Call<RegistrationResponse> createUser(
            @Field("username") String username,
            @Field("password") String password,
            @Field("firstname") String firstname,
            @Field("lastname") String lastname,
            @Field("email") String email,
            @Field("phone") String phone
    );



    @FormUrlEncoded
    @POST ("userLogin.php")
    Call<LoginResponse>userLogin(
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST ("updateUsername.php")
    Call<LoginResponse>updateUsername(
            @Field("userid") String userid,
            @Field("username") String username
    );

    @FormUrlEncoded
    @POST ("updatePassword.php")
    Call<DefaultResponse>updatePassword(
            @Field("userid") String userid,
            @Field("currentpassword") String currentpassword,
            @Field("newpassword") String newpassword
    );

    @GET("TotalTasks.php")
    Call<ProgressPercent> getPercent (@Query("userid") String userid);

    @GET("AssignedTasksApi.php")
    Call<List<TaskDetails>> getTaskDetail(@Query("userid") String userid,
                                         @Query("progresstype") String progresstype);

    @GET("AssignedCardsApi.php")
    Call<List<CardDetails>> getCardDetail(@Query("userid") String userid,
                                         @Query("taskid") String taskid);

    @GET("progressbarPercentage.php")
    Call<ProgressBarPercent> getCardPercent(@Query("userid") String userid,
                                           @Query("taskid") String taskid);


//    @Multipart
//    @POST("Api.php?apicall=upload")
//    Call<MyResponse> uploadImage(@Part("image\"; filename=\"myfile.jpg\" ") RequestBody file, @Part("desc") RequestBody desc);


    @Multipart
    @POST("uploadcard.php?apicall=upload")
    Call<DefaultResponse> uploadImage( @Part("userid") RequestBody userid,
                                       @Part("taskid") RequestBody taskid,
                                       @Part("cardid") RequestBody cardid,
                                       @Part("data") RequestBody data,
                                       @Part("image\"; filename=\"myfile.jpg\" ") RequestBody file);

    @GET("uploadcard.php?apicall=images")
    Call<List<UploadedCards>> getUploadedCards(@Query("userid") String userid);
}
