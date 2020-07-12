package com.example.loginregistration_web.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginregistration_web.Adapters.TaskProgressAdapter;
import com.example.loginregistration_web.R;
import com.example.loginregistration_web.Storage.SharedPrefManager;
import com.example.loginregistration_web.api.RetrofitClient;
import com.example.loginregistration_web.models.LoginResponse;
import com.example.loginregistration_web.models.ProgressPercent;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView tvUsername;
    Context context;
    String userid;
    ListView taskProgressList;
    String tTitle[]={"All Tasks","New Task","In Progress","Completed"};
    int[] tImages={R.drawable.totaltask,R.drawable.newtaskt,R.drawable.inprogresspq, R.drawable.taskcomplete };
    String[] percent={"0","0","0","0"} ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvUsername = findViewById(R.id.tv);

        LoginResponse user = SharedPrefManager .getInstance(this).getUser() ;
        tvUsername.setText(" " + user.getUsername() ) ;
        userid = user.getUserid();


        context=this;

        taskProgressList = findViewById(R.id.taskProgressList);

        //userid =  getIntent().getStringExtra("userid");

       // Toast.makeText(getApplicationContext(), "userid "+ userid, Toast.LENGTH_LONG ).show();


        Call<ProgressPercent> call = RetrofitClient
                .getInstance()
                .getApi()
                .getPercent(userid) ;
        //ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        //Call<ProgressPercent> call = apiService.getPercent(userid);
        call.enqueue(new Callback<ProgressPercent>() {
            @Override
            public void onResponse(Call<ProgressPercent> call, Response<ProgressPercent> response) {

                ProgressPercent progressPercent = response.body();

                if(progressPercent!= null) {

                    percent[0] = progressPercent.getTotaltasks();
                    percent[1] = progressPercent.getNewtasks();
                    percent[2] = progressPercent.getInprogresstasks();
                    percent[3] = progressPercent.getCompletedtasks();
                    //String s = progressPercent.getTotaltasks();
                   // Toast.makeText(getApplicationContext(), "total: " + s, Toast.LENGTH_LONG).show();
                    //Toast.makeText(getApplicationContext(), percent[0] + " " + percent[1] + " " + percent[2] + " +" + percent[3], Toast.LENGTH_LONG).show();

                }
                TaskProgressAdapter taskProgressAdapter = new TaskProgressAdapter(context, percent, tTitle, tImages);

                taskProgressList.setAdapter(taskProgressAdapter);

                taskProgressList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (position == 0) {

                           // Toast.makeText(getApplicationContext(), "All Tasks", Toast.LENGTH_SHORT).show();

                            Intent intentCard= new Intent(getApplicationContext(), TaskActivity.class);
                            intentCard.putExtra("userid", userid);
                            intentCard.putExtra("progresstype", "TotalTasks");
                            startActivity(intentCard);

                        }

                        if (position == 1) {

                          //  Toast.makeText(getApplicationContext(), "New Tasks", Toast.LENGTH_SHORT).show();
                            Intent intentCard= new Intent(getApplicationContext(),  TaskActivity.class);
                            intentCard.putExtra("userid", userid);
                            intentCard.putExtra("progresstype", "NewTasks");
                            startActivity(intentCard);
                        }

                        if (position == 2) {

                          //  Toast.makeText(getApplicationContext(), "In Progress Tasks", Toast.LENGTH_SHORT).show();
                            Intent intentCard= new Intent(getApplicationContext(),  TaskActivity.class);
                            intentCard.putExtra("userid", userid);
                            intentCard.putExtra("progresstype", "InprogressTasks");
                            startActivity(intentCard);
                        }

                        if (position == 3) {

                           // Toast.makeText(getApplicationContext(), "Completed Tasks", Toast.LENGTH_SHORT).show();
                            Intent intentCard= new Intent(getApplicationContext(),  TaskActivity.class);
                            intentCard.putExtra("userid", userid);
                            intentCard.putExtra("progresstype", "CompletedTasks");
                            startActivity(intentCard);
//                    Intent TaskProgressIntent = new Intent (getApplicationContext(), TaskProgress.class);
//                    TaskProgressIntent.putExtra("Handwriting",hTitle [0]) ;
//                    TaskProgressIntent.putExtra("position",""+0);
//                    startActivity(TaskProgressIntent);

                        }
                    }
                });


            }

            @Override
            public void onFailure(Call<ProgressPercent> call, Throwable t) {
                Log.d("TAG","Response = "+t.toString());

            }
        });


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
