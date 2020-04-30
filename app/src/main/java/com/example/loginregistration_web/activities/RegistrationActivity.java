package com.example.loginregistration_web.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginregistration_web.R;
import com.example.loginregistration_web.models.RegistrationResponse;
import com.example.loginregistration_web.api.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {

    EditText etUsername;
    EditText etPassword;
    EditText etCnfPassword;
    EditText etFirstName;
    EditText etLastName;
    EditText etEmail;
    EditText etPhone;
    Button btnRegister;
    TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        etUsername = findViewById(R.id.etUsername ) ;
        etFirstName  = findViewById(R.id.etFirstName  ) ;
        etLastName  = findViewById(R.id.etLastName  ) ;
        etEmail  = findViewById(R.id.etEmail  ) ;
        etPhone  = findViewById(R.id.etPhone  ) ;
        etPassword  = findViewById(R.id.etPassword ) ;
        etCnfPassword  = findViewById(R.id.etCnfPassword ) ;
        btnRegister   = findViewById(R.id.btnRegister  ) ;
        tvLogin   = findViewById(R.id.tvLogin  ) ;


        tvLogin .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LoginIntent = new Intent(RegistrationActivity.this, LoginActivity.class) ;
                startActivity(LoginIntent ) ;
            }
        }) ;


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String userName = etUsername .getText() .toString().trim() ;
                String firstName = etFirstName  .getText() .toString().trim() ;
                String lastName = etLastName  .getText() .toString().trim() ;
                String email = etEmail  .getText() .toString().trim() ;
                String phone = etPhone  .getText() .toString().trim() ;
                String pwd = etPassword  .getText() .toString().trim() ;
                String cnf_pwd = etCnfPassword .getText() .toString().trim() ;


                if (firstName.isEmpty()){
                    etFirstName.setError("First Name is required");
                    etFirstName.requestFocus();
                    return;
                }

                if (lastName.isEmpty()){
                    etLastName.setError("Last Name is required");
                    etLastName.requestFocus();
                    return;
                }

                if (userName.isEmpty()){
                    etUsername.setError("Username is required");
                    etUsername.requestFocus();
                    return;
                }

                if (email.isEmpty()){
                    etEmail.setError("Email is required");
                    etEmail.requestFocus();
                    return;
                }


               if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){

                   etEmail.setError("Enter a valid email");
                   etEmail.requestFocus();
                   return;
               }

                if (phone.isEmpty()){
                    etPhone.setError("Phone Number is required");
                    etPhone.requestFocus();
                    return;
                }

                if (!Patterns.PHONE.matcher(phone).matches()){

                    etPhone.setError("Enter a valid Phone Number");
                    etPhone.requestFocus();
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

                if (cnf_pwd.isEmpty()){
                    etCnfPassword.setError("Confirm Password is required");
                    etCnfPassword.requestFocus();
                    return;
                }

                if (!pwd.equals(cnf_pwd)){
                    etCnfPassword.setError("Password and Confirm Password should be same");
                    etCnfPassword.requestFocus();
                    return;
                }





                /*Do usr registration using the api call*/
                Call <RegistrationResponse > call = RetrofitClient
                        .getInstance()
                        .getApi()
                        .createUser(userName,pwd, firstName, lastName, email, phone ) ;

                call.enqueue(new Callback<RegistrationResponse>() {
                    @Override
                    public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                        RegistrationResponse rr = response.body();

                        Toast.makeText(getApplicationContext(), rr.getMessage(), Toast.LENGTH_SHORT ) .show() ;
                    }

                    @Override
                    public void onFailure(Call<RegistrationResponse> call, Throwable t) {

                    }
                });


            }
        });
    }
}