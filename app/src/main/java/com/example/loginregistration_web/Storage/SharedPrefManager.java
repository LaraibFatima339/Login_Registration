package com.example.loginregistration_web.Storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.loginregistration_web.models.LoginResponse;

public class SharedPrefManager {


    private static SharedPrefManager mInstance;
    private Context context;

    private static final String SHARED_PREF_NAME= "my_shared_preff";



    private SharedPrefManager(Context context) {
        this.context = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance ==null){
            mInstance =  new SharedPrefManager(context );
        }
        return mInstance;

    }

    public void saveUser(String userid, String username, String firstname, String lastname, String email ){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE  );
        SharedPreferences.Editor editor =  sharedPreferences.edit();

        editor.putString("userid", userid  );
        editor.putString("username", username ) ;
        editor.putString("firstname", firstname ) ;
        editor.putString("lastname", lastname ) ;
        editor.putString("email", email ) ;

        editor.apply();
    }



    public LoginResponse getUser(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE  );

        return new LoginResponse(
                sharedPreferences.getBoolean("error",false  ) ,
                sharedPreferences.getString("message",null),
                sharedPreferences.getString("userid","-1") ,
                sharedPreferences.getString("username",null) ,
                sharedPreferences.getString("firstname",null),
                sharedPreferences.getString("lastname", null),
                sharedPreferences.getString("email",null)
        );
    }

    public void clear(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE  );
        SharedPreferences.Editor editor =  sharedPreferences.edit();
        editor.clear();
        editor.apply();

    }



    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE  );
       return (sharedPreferences.getString("userid" ,"-1")!="-1");



    }





}
