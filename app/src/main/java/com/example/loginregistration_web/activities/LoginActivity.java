package com.example.loginregistration_web.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginregistration_web.Storage.SharedPrefManager;
import com.example.loginregistration_web.models.LoginResponse;
import com.example.loginregistration_web.R;
import com.example.loginregistration_web.api.RetrofitClient;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername;
    EditText etPassword;
    Button btnLogin;
    TextView tvRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        etUsername = findViewById(R.id.etUsername ) ;
        etPassword  = findViewById(R.id.etPassword ) ;
        btnLogin  = findViewById(R.id.btnLogin  ) ;
        tvRegister  = findViewById(R.id.tvRegister ) ;

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent=new Intent(LoginActivity.this, RegistrationActivity.class ) ;
                startActivity(registerIntent ) ;
            }
        }) ;

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = etUsername .getText() .toString().trim() ;
                String pwd = etPassword  .getText() .toString().trim() ;

                if (userName.isEmpty()){
                    etUsername.setError("Username is required");
                    etUsername.requestFocus();
                    return;
                }

                if (pwd.isEmpty()){
                    etPassword.setError("Password is required");
                    etPassword.requestFocus();
                    return;
                }

                if(pwd.length() <6){
                    etPassword.setError("Password should be atleast 6 character long");
                    etPassword.requestFocus();
                    return;
                }

                Call<LoginResponse> call = RetrofitClient
                        .getInstance()
                        .getApi()
                        .userLogin(userName ,pwd) ;

                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                        LoginResponse loginResponse = response.body();

                        if(!loginResponse.isError() ){

                            //save user

                            SharedPrefManager.getInstance(getApplicationContext())
                            .saveUser(
                                 loginResponse.getUserID() ,
                                    loginResponse.getUserName(),
                                    loginResponse.getFirstName(),
                                    loginResponse.getLastName() ,
                                    loginResponse.getEmail()
                            ) ;

                            startActivity(new Intent (getApplicationContext(), MainActivity.class)) ;
                            finish();


                            Toast.makeText(getApplicationContext(), loginResponse .getMessage() , Toast.LENGTH_SHORT ).show();
                        }else{
                            Toast.makeText(getApplicationContext(), loginResponse .getMessage() , Toast.LENGTH_SHORT ).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {

                    }
                });


            }


        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(SharedPrefManager.getInstance(this).isLoggedIn() ){
            finish();
            startActivity(new Intent(this,MainActivity .class)) ;
        }
    }
}
