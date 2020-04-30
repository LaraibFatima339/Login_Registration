package com.example.loginregistration_web.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL= "http://172.20.10.6/UrduGlyph/";
    private static RetrofitClient mInstance;
    private Retrofit retrofit;

    private RetrofitClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create() )
                .build();
    }


    public static synchronized RetrofitClient getInstance() {
        if(mInstance == null){
            mInstance = new RetrofitClient();
        }

        return mInstance;
    }

    public Api getApi(){
        return retrofit.create(Api.class) ;
    }
}
