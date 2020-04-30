package com.example.loginregistration_web.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.loginregistration_web.R;
import com.example.loginregistration_web.Storage.SharedPrefManager;
import com.example.loginregistration_web.models.LoginResponse;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv);

        LoginResponse user = SharedPrefManager .getInstance(this).getUser() ;
        tv.setText("Welcome Back " + user.getUserName() ) ;


    }

    @Override
    protected void onStart() {
         super.onStart() ;
        if(!SharedPrefManager.getInstance(this).isLoggedIn() ){
            finish();
            startActivity(new Intent(this,LoginActivity .class)) ;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.mainmenu, menu );
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.OptionLogout){
            SharedPrefManager.getInstance(getApplicationContext()).clear();
            startActivity(new Intent (getApplicationContext(), LoginActivity.class)) ;
            finish();


        }
        return super.onOptionsItemSelected(item);
    }
}
