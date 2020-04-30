package com.example.loginregistration_web.Storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.loginregistration_web.models.LoginResponse;

public class SharedPrefManager {


    private static SharedPrefManager mInstance;
    private Context context;

    private static final String SHARED_PREF_NAME= "my_shared_preff";
//    private static final String KEY_USERNAME = "UserName";
//    private static final String KEY_USER_EMAIL = "Email";
//    private static final String KEY_USER_ID = "UserID";



    private SharedPrefManager(Context context) {
        this.context = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance ==null){
            mInstance =  new SharedPrefManager(context );
        }
        return mInstance;

    }

    public void saveUser(Integer UserID, String UserName, String FirstName, String LastName, String Email ){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE  );
        SharedPreferences.Editor editor =  sharedPreferences.edit();

        editor.putInt("UserID", UserID  );
        editor.putString("UserName", UserName ) ;
        editor.putString("FirstName", FirstName ) ;
        editor.putString("LastName", LastName ) ;
        editor.putString("Email", Email ) ;

        editor.apply();
    }



    public LoginResponse getUser(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE  );

        return new LoginResponse(
                sharedPreferences.getBoolean("error",false  ) ,
                sharedPreferences.getString("message",null),
                sharedPreferences.getInt("UserID",-1) ,
                sharedPreferences.getString("UserName",null) ,
                sharedPreferences.getString("FirstName",null),
                sharedPreferences.getString("LastName", null),
                sharedPreferences.getString("Email",null)
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
       return sharedPreferences.getInt("UserID" ,-1)!=-1;

    }





}
