package com.example.loginregistration_web.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginregistration_web.MyCanvasDrawing.MyCanvas;
import com.example.loginregistration_web.R;
import com.example.loginregistration_web.Storage.SharedPrefManager;
import com.example.loginregistration_web.api.RetrofitClient;
import com.example.loginregistration_web.models.DefaultResponse;
import com.example.loginregistration_web.models.LoginResponse;
import com.example.loginregistration_web.models.RegistrationResponse;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HandwritingActivity extends AppCompatActivity {

    LinearLayout view;
    MyCanvas myCanvas;
    Button btnClear, btnUpload, btnPrevious, btnNext;
   static String userid,taskid,cardid;
    int position;
    TextView tvTitle;
    BottomNavigationView bottomNavigationView;

    // int defaultColor;
    int STORAGE_PERMISSION_CODE = 1;
    //private ArrayList<Path> paths = new ArrayList<Path>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handwriting);
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

        tvTitle = findViewById(R.id.Title);

        btnClear = findViewById(R.id.btnClear);
        btnUpload = findViewById(R.id.btnUpload);
        btnPrevious= findViewById(R.id.btnPrevious);
        btnNext= findViewById(R.id.btnNext);

        myCanvas = findViewById(R.id.myCanvas);

        final LoginResponse user = SharedPrefManager .getInstance(this).getUser() ;
        userid = user.getUserid();
//        int height = myCanvas.getHeight();
//        int width = myCanvas.getMeasuredWidth();
//        Toast.makeText(getApplicationContext(), "Height:"+ height+"  width: "+ width, Toast.LENGTH_SHORT).show();

        taskid = getIntent().getStringExtra("taskid");
        final ArrayList<String> cardsid = getIntent().getStringArrayListExtra("cardsid");
        final ArrayList<String> cards = getIntent().getStringArrayListExtra("cards");

        cardid = cardsid.get(position);
       // String[] cards = getIntent().getStringArrayListExtra("cards");
                //getStringArrayExtra("cards");
        position= getIntent().getIntExtra("position",0);

        tvTitle.setText(cards.get(position));

//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//
//
//        myCanvas= new MyCanvas(this,null);
  //     myCanvas.initialize();

        //myCanvas = new MyCanvas(this,null) ;
//
//        view.addView(myCanvas);


        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myCanvas.clearCanvas();

            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                int height = view.getHeight();
//                int width = view.getMeasuredWidth();
//                Toast.makeText(getApplicationContext(), "Height:"+ height+"  width: "+ width, Toast.LENGTH_SHORT).show();
//


                // Getting the x and y coordinates points in "JSON" type
                String pointsData= myCanvas. savePoints();
                Toast.makeText(getApplicationContext(), "Points: "+ pointsData ,Toast.LENGTH_LONG).show();

                if (ContextCompat.checkSelfPermission(HandwritingActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    requestStoragePermission();

                }
                //Getting the file to be uploaded
               String filename = myCanvas.saveImage();
                //File sdDirectory = Environment.getExternalStorageDirectory();

                File file = new File(filename);
                if(file.exists()){
                    Toast.makeText(getApplicationContext(), "File exists at: "+ filename ,Toast.LENGTH_LONG).show();

                    RequestBody upload__userid= RequestBody.create(MediaType.parse("text/plain"), userid);
                    RequestBody upload_taskid = RequestBody.create(MediaType.parse("text/plain"), taskid);
                    RequestBody upload_cardid = RequestBody.create(MediaType.parse("text/plain"), cardid);
                    RequestBody upload_data = RequestBody.create(MediaType.parse("text/plain"), pointsData);
                    RequestBody upload_datafile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

                    Call<DefaultResponse> call = RetrofitClient
                            .getInstance()
                            .getApi()
                            .uploadImage(upload__userid,upload_taskid,upload_cardid,upload_data,upload_datafile ) ;

                    call.enqueue(new Callback<DefaultResponse>() {
                        @Override
                        public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                           DefaultResponse defaultResponser = response.body();

                            Toast.makeText(getApplicationContext(), defaultResponser.getMessage(), Toast.LENGTH_SHORT ) .show() ;
                        }

                        @Override
                        public void onFailure(Call<DefaultResponse> call, Throwable t) {

                        }
                    });




                }else{
                    Toast.makeText(getApplicationContext(), "First draw the alphabet" ,Toast.LENGTH_LONG).show();

                }

            }
        });

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //int size = cards.size();
              //  btnPrevious.setEnabled(true);
                if(position!=0){
                    btnNext.setEnabled(true);
                    position= (position-1)%cards.size();
                    tvTitle.setText(cards.get(position));
                    cardid = cardsid.get(position);
                    Toast.makeText(HandwritingActivity.this, "cardid "+ cardid, Toast.LENGTH_LONG).show();

                    myCanvas.clearCanvas();
                   // btnPrevious.setBackgroundColor(R.drawable.round_button);
                }else {
                    btnPrevious.setEnabled(false);
                }


            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(position < cards.size()-1) {
                    btnPrevious.setEnabled(true);
                    position = (position + 1) % cards.size();
                    tvTitle.setText(cards.get(position));

                    cardid = cardsid.get(position);
                    Toast.makeText(HandwritingActivity.this, "cardid " + cardid, Toast.LENGTH_LONG).show();

                    myCanvas.clearCanvas();
                }else{
                    btnNext.setEnabled(false);
                }
            }
        });

    }

    private void requestStoragePermission () {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("Needed to save image")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            ActivityCompat.requestPermissions(HandwritingActivity.this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);

                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();

                        }

                    })
                    .create().show();

        } else {

            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == STORAGE_PERMISSION_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this, "Access granted", Toast.LENGTH_LONG).show();

            } else {

                Toast.makeText(this, "Access denied", Toast.LENGTH_LONG).show();

            }

        }

    }




//
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
