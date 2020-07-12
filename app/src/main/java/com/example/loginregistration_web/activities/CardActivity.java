package com.example.loginregistration_web.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginregistration_web.Adapters.CardDetailsAdapter;
import com.example.loginregistration_web.R;
import com.example.loginregistration_web.Storage.SharedPrefManager;
import com.example.loginregistration_web.models.CardDetails;
import com.example.loginregistration_web.models.LoginResponse;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        tvTitle = findViewById(R.id.Title);

        cardDetailsList = new ArrayList<>();
        itemView = (RecyclerView)findViewById(R.id.Itemlist );
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        itemView.setLayoutManager(layoutManager);
        cardDetailsAdapter = new CardDetailsAdapter(getApplicationContext(),cardDetailsList);
        itemView.setAdapter(cardDetailsAdapter);


        LoginResponse user = SharedPrefManager.getInstance(this).getUser() ;
        userid = user.getUserid();

        taskid = getIntent().getStringExtra("taskid");
        taskname=getIntent().getStringExtra("taskname");
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
}