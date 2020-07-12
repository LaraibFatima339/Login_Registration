package com.example.loginregistration_web.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import com.example.loginregistration_web.Adapters.TaskDetailsAdapter;
import com.example.loginregistration_web.R;
import com.example.loginregistration_web.models.TaskDetails;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

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


       // Toast.makeText(this, userid+" ", Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, progresstype+" ", Toast.LENGTH_SHORT).show();

        //Log.i("Check", userid);


//        RetrofitClient apiService = RetrofitClient..getInstance()
//                .getApi().create(ApiInterface.class);
//        Call<List<TaskCardInfo >> call = apiService.getItems(userid, progresstype);

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
}