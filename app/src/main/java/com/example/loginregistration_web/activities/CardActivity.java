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

import com.example.loginregistration_web.Adapters.CardDetailsAdapter;
import com.example.loginregistration_web.R;
import com.example.loginregistration_web.Storage.SharedPrefManager;
import com.example.loginregistration_web.models.CardDetails;
import com.example.loginregistration_web.models.LoginResponse;
import com.example.loginregistration_web.models.UploadedCards;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.loginregistration_web.api.RetrofitClient.getInstance;

public class CardActivity extends AppCompatActivity {

    List<CardDetails> cardDetailsList;
    RecyclerView itemView;
    CardDetailsAdapter cardDetailsAdapter;
    String userid,taskid,taskname;
    TextView tvTitle;
    BottomNavigationView bottomNavigationView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

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

        LoginResponse user = SharedPrefManager.getInstance(this).getUser() ;
        userid = user.getUserid();

        taskid = getIntent().getStringExtra("taskid");
        taskname=getIntent().getStringExtra("taskname");

        tvTitle = findViewById(R.id.Title);

        cardDetailsList = new ArrayList<>();
        itemView = (RecyclerView)findViewById(R.id.Itemlist );
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        itemView.setLayoutManager(layoutManager);
        cardDetailsAdapter = new CardDetailsAdapter(getApplicationContext(),cardDetailsList, taskid);
        itemView.setAdapter(cardDetailsAdapter);



        tvTitle.setText("Cards of "+ taskname);


       // Toast.makeText(this, userid+" ", Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, taskid+" ", Toast.LENGTH_SHORT).show();

        //Toast.makeText(this, taskname+" ", Toast.LENGTH_SHORT).show();

        //Log.i("Check", userid);


//        RetrofitClient apiService = RetrofitClient..getInstance()
//                .getApi().create(ApiInterface.class);
//        Call<List<CardCardInfo >> call = apiService.getItems(userid, progresstype);

        Call<List<CardDetails>> call = getInstance()
                .getApi()
                .getCardDetail(userid,taskid) ;

        call.enqueue(new Callback<List<CardDetails>>() {
            @Override
            public void onResponse(Call<List<CardDetails>> call, Response<List<CardDetails>> response) {


                cardDetailsList = response.body();


                Log.d("TAG","Response = "+cardDetailsList);
                cardDetailsAdapter.setItemList(cardDetailsList);



            }

            @Override
            public void onFailure(Call<List<CardDetails>> call, Throwable t) {
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
//                startActivity(new Intent(getApplicationContext(), MainActivity.class)) ;
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                    finishAffinity();
//                }
//                break;
//
//
//            case R.id.OptionUploaded:
//                startActivity(new Intent (getApplicationContext(), UploadedActivity.class)) ;
//                break;
//            case R.id.OptionSetting:
//                startActivity(new Intent(getApplicationContext(), SettingsActivity.class)) ;
//                break;
//
//
//
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}