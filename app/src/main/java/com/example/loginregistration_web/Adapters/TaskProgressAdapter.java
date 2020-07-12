package com.example.loginregistration_web.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.loginregistration_web.R;

public class TaskProgressAdapter extends ArrayAdapter<String> {

    Context context;
    String percent[];
    String pTitle[];
    int pImgs[];



    public TaskProgressAdapter(Context context, String percent[],String pTitle[], int pImgs[]) {
        super(context, R.layout.progresstask_row, pTitle);
        this.context = context;
        this.percent=percent;
        this.pTitle = pTitle;
        this.pImgs = pImgs;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;
        View taskProgressRow= layoutInflater.inflate(R.layout.progresstask_row, parent, false);

        ImageView imageView = taskProgressRow.findViewById(R.id.pimage);
        TextView tvTitle=taskProgressRow.findViewById(R.id.tvProgress);
        TextView tvProgressPercent = taskProgressRow.findViewById(R.id.tvProgressPercent);

        imageView.setImageResource(pImgs[position]);
        tvTitle.setText(pTitle[position]);
        tvProgressPercent.setText(percent[position]);

        return taskProgressRow;
    }
}