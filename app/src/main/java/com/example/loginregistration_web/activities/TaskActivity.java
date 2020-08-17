package com.example.loginregistration_web.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import com.example.loginregistration_web.Adapters.TaskDetailsAdapter;
import com.example.loginregistration_web.R;
import com.example.loginregistration_web.Storage.SharedPrefManager;
import com.example.loginregistration_web.models.TaskDetails;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.loginregistration_web.api.RetrofitClient.getInstance;

public class TaskActivity extends AppCompatActivity {

    List<TaskDetails> taskDetailsList;
    RecyclerView itemView;
    TaskDetailsAdapter taskDetailsAdapter;
    String userid;
    String progresstype;
    TextView tvProgresstypeTitle;
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.OptionHome:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class)) ;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            finishAffinity();
                        }
                        break;


                    case R.id.OptionUploaded:
                        startActivity(new Intent (getApplicationContext(), UploadedActivity.class)) ;
                        break;

                    case R.id.OptionSetting:
                        startActivity(new Intent (getApplicationContext(), SettingsActivity.class)) ;
                        break;


                }
                return true;
            }
        });

        tvProgresstypeTitle = findViewById(R.id.progresstypeTitle);

        taskDetailsList = new ArrayList<>();
        itemView = (RecyclerView)findViewById(R.id.Itemlist );
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        itemView.setLayoutManager(layoutManager);
        taskDetailsAdapter = new TaskDetailsAdapter(getApplicationContext(),taskDetailsList);
        itemView.setAdapter(taskDetailsAdapter);


        userid =  getIntent().getStringExtra("userid");
        progresstype = getIntent().getStringExtra("progresstype");
        tvProgresstypeTitle.setText(progresstype);



        Call<List<TaskDetails>> call = getInstance()
                .getApi()
                .getTaskDetail(userid,progresstype) ;

        call.enqueue(new Callback<List<TaskDetails>>() {
            @Override
            public void onResponse(Call<List<TaskDetails>> call, Response<List<TaskDetails>> response) {


                taskDetailsList = response.body();


                Log.d("TAG","Response = "+taskDetailsList);
               taskDetailsAdapter.setItemList(taskDetailsList);



            }

            @Override
            public void onFailure(Call<List<TaskDetails>> call, Throwable t) {
                Log.d("TAG","Response = "+t.toString());
            }
        });







    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater menuInflater = new MenuInflater(this);
//        menuInflater.inflate(R.menu.mainmenu, menu );
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        //int id = item.getItemId();
//
//        switch(item.getItemId()){
//            case R.id.OptionHome:
//                startActivity(new Intent (getApplicationContext(), MainActivity.class)) ;
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                    finishAffinity();
//                }
//                break;
//
//            case R.id.OptionUploaded:
//                startActivity(new Intent (getApplicationContext(), UploadedActivity.class)) ;
//                break;
//
//            case R.id.OptionSetting:
//                startActivity(new Intent(getApplicationContext(), SettingsActivity.class)) ;
//                break;
//
//
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}