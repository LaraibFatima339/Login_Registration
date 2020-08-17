package com.example.loginregistration_web.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.loginregistration_web.R;
import com.example.loginregistration_web.Storage.SharedPrefManager;
import com.example.loginregistration_web.api.RetrofitClient;
import com.example.loginregistration_web.models.DefaultResponse;
import com.example.loginregistration_web.models.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsActivity extends AppCompatActivity {

    EditText etNewUsername, etCurrentPassword, etNewPassword;
    Button btnUpdateUsername, btnUpdatePassword, btnLogout, btnDeleteAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        etNewUsername= findViewById(R.id.etUsername);
        etCurrentPassword= findViewById(R.id.etCurrentPassword);
        etNewPassword = findViewById(R.id.etNewPassword);
        btnUpdateUsername = findViewById(R.id.btnUpdateUsername);
        btnUpdatePassword= findViewById(R.id.btnUpdatePassword);
        btnLogout = findViewById(R.id.btnLogout);
        btnDeleteAccount = findViewById(R.id.btnDeleteAccount);

        btnUpdateUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userName = etNewUsername .getText() .toString().trim() ;

                if (userName.isEmpty()){
                    etNewUsername.setError("Username is required");
                    etNewUsername.requestFocus();
                    return;
                }

                LoginResponse user = SharedPrefManager .getInstance(getApplicationContext() ).getUser() ;

               // userid = user.getUserid();

                Call<LoginResponse> call = RetrofitClient.getInstance()
                        .getApi().updateUsername(
                                user.getUserid(),
                                userName
                        );
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT ).show();

                        if(!response.body().isError()){
                            SharedPrefManager.getInstance(getApplicationContext())
                                    .saveUser(
                                            response.body().getUserid() ,
                                            response.body().getUsername(),
                                            response.body().getFirstname(),
                                            response.body().getLastname() ,
                                            response.body().getEmail()
                                    ) ;
                            LoginResponse user = SharedPrefManager .getInstance(getApplicationContext() ).getUser() ;
                            startActivity(new Intent(getApplicationContext(), MainActivity.class)) ;
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {

                    }
                });



            }
        });

        btnUpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String currentPassword = etCurrentPassword .getText() .toString().trim() ;
                String newPassword = etNewPassword .getText() .toString().trim() ;


                if (currentPassword.isEmpty()){
                    etCurrentPassword.setError("Current Password is required");
                    etCurrentPassword.requestFocus();
                    return;
                }
                if (newPassword.isEmpty()){
                    etNewPassword.setError("New Password is required");
                    etNewPassword.requestFocus();
                    return;
                }

                if(newPassword.length() <6){
                    etNewPassword.setError("Password should be atleast 6 character long");
                    etNewPassword.requestFocus();
                    return;
                }

                LoginResponse user = SharedPrefManager .getInstance(getApplicationContext() ).getUser() ;

                // userid = user.getUserid();

                Call<DefaultResponse> call = RetrofitClient.getInstance()
                        .getApi().updatePassword(
                                user.getUserid(),
                                currentPassword,
                                newPassword
                        );

                call.enqueue(new Callback<DefaultResponse>() {
                    @Override
                    public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT ).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class)) ;
                        finish();

                    }

                    @Override
                    public void onFailure(Call<DefaultResponse> call, Throwable t) {

                    }
                });
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPrefManager.getInstance(getApplicationContext()).clear();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class)) ;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    finishAffinity();
                }

            }
        });

        btnDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
