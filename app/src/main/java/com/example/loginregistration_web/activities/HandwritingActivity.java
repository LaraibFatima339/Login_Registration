package com.example.loginregistration_web.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginregistration_web.MyCanvasDrawing.MyCanvas;
import com.example.loginregistration_web.R;

import java.util.ArrayList;

public class HandwritingActivity extends AppCompatActivity {

    LinearLayout view;
    MyCanvas myCanvas;
    Button btnClear, btnUpload, btnPrevious, btnNext;
    String userid,cardid,cardname;
    int position;
    TextView tvTitle;
   // int defaultColor;
    int STORAGE_PERMISSION_CODE = 1;
    //private ArrayList<Path> paths = new ArrayList<Path>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handwriting);

        tvTitle = findViewById(R.id.Title);

        btnClear = findViewById(R.id.btnClear);
        btnUpload = findViewById(R.id.btnUpload);
        btnPrevious= findViewById(R.id.btnPrevious);
        btnNext= findViewById(R.id.btnNext);

        view = findViewById(R.id.canvasLayout);


       final ArrayList<String> cards = getIntent().getStringArrayListExtra("cards");
       // String[] cards = getIntent().getStringArrayListExtra("cards");
                //getStringArrayExtra("cards");
        position= getIntent().getIntExtra("position",0);

        tvTitle.setText(cards.get(position));


        //cardid = getIntent().getStringExtra("cardid");
        //cardname=getIntent().getStringExtra("cardname");
       Toast.makeText(HandwritingActivity.this, "received array"+cards, Toast.LENGTH_LONG).show();
//        Toast.makeText(HandwritingActivity.this, "received size= "+cards.size(), Toast.LENGTH_LONG).show();
//        Toast.makeText(HandwritingActivity.this, "received position= "+position, Toast.LENGTH_LONG).show();


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);


        myCanvas= new MyCanvas(this,null);
        myCanvas.initialize(displayMetrics);

        //myCanvas = new MyCanvas(this,null) ;

        view.addView(myCanvas);


        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myCanvas.clearCanvas();

            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                myCanvas.showArray();

                if (ContextCompat.checkSelfPermission(HandwritingActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    requestStoragePermission();

                }
                myCanvas.saveImage();


            }
        });

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //int size = cards.size();
                if(position!=0){
                    position= (position-1)%cards.size();
                    tvTitle.setText(cards.get(position));
                    myCanvas.clearCanvas();
                }
               // tvTitle.setText(cards.get(position));
               // Toast.makeText(HandwritingActivity.this, "size"+size, Toast.LENGTH_LONG).show();
//                Toast.makeText(HandwritingActivity.this, "size= "+cards.size(), Toast.LENGTH_LONG).show();
//                Toast.makeText(HandwritingActivity.this, "position= "+position, Toast.LENGTH_LONG).show();


            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               position= (position+1)%cards.size();
               tvTitle.setText(cards.get(position));
                myCanvas.clearCanvas();
//                Toast.makeText(HandwritingActivity.this, "size= "+cards.size(), Toast.LENGTH_LONG).show();
//
//                Toast.makeText(HandwritingActivity.this, "position= "+position, Toast.LENGTH_LONG).show();

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


}
