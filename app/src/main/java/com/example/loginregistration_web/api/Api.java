package com.example.loginregistration_web.api;

import com.example.loginregistration_web.models.RegistrationResponse;
import com.example.loginregistration_web.models.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("registerUser.php")
    Call<RegistrationResponse> createUser(
            @Field("UserName") String UserName,
            @Field("Password") String Password,
            @Field("FirstName") String FirstName,
            @Field("LastName") String LastName,
            @Field("Email") String Email,
            @Field("Phone") String Phone
    );



    @FormUrlEncoded
    @POST ("userLogin.php")
    Call<LoginResponse>userLogin(
            @Field("UserName") String UserName,
            @Field("Password") String Password
    );
}
