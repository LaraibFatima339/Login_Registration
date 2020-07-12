package com.example.loginregistration_web.api;

import com.example.loginregistration_web.models.CardDetails;
import com.example.loginregistration_web.models.ProgressPercent;
import com.example.loginregistration_web.models.RegistrationResponse;
import com.example.loginregistration_web.models.LoginResponse;
import com.example.loginregistration_web.models.TaskDetails;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
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

    @GET("TotalTasks.php")
    Call<ProgressPercent> getPercent (@Query("userid") String userid);

    @GET("AssignedTasksApi.php")
    Call<List<TaskDetails>> getTaskDetail(@Query("userid") String userid,
                                         @Query("progresstype") String progresstype);

    @GET("AssignedCardsApi.php")
    Call<List<CardDetails>> getCardDetail(@Query("userid") String userid,
                                         @Query("taskid") String taskid);
}
