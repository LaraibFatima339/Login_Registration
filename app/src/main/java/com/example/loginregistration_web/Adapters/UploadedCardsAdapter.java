package com.example.loginregistration_web.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginregistration_web.R;
import com.example.loginregistration_web.activities.CardActivity;
import com.example.loginregistration_web.models.ProgressBarPercent;
import com.example.loginregistration_web.models.TaskDetails;
import com.example.loginregistration_web.models.UploadedCards;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.loginregistration_web.api.RetrofitClient.getInstance;

public class UploadedCardsAdapter extends RecyclerView.Adapter<UploadedCardsAdapter.ViewHolderNewItem > {


    Context context;

    List<UploadedCards> uploadedCards;
    public static ProgressBarPercent progressBarPercent;

    public static URL url = null;
    public static InputStream inputStream = null;

    public UploadedCardsAdapter(Context context, List<UploadedCards> uploadedCards) {
        this.context = context;
        this.uploadedCards = uploadedCards;

    }

    public void setItemList(List<UploadedCards> uploadedCards) {
        this.uploadedCards  = uploadedCards;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public UploadedCardsAdapter.ViewHolderNewItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext() ).inflate(R.layout.uploadedcard_row,parent, false);
        //ViewHolderNew viewHolderNew = new ViewHolderNew(v) ;

        return new UploadedCardsAdapter.ViewHolderNewItem(v) ;
    }

    @Override
    public void onBindViewHolder(@NonNull final UploadedCardsAdapter.ViewHolderNewItem holder, int position) {


        holder.tvUrl.setText("Card:");

        String url = uploadedCards.get(position).getDatafile();
       // Bitmap bmp = BitmapFactory.decodeStream(uploadedCards.get(position).getDatafile().);

      //  Picasso.get().load(thisSpacecraft.getImageURL()).placeholder(R.drawable.placeholder).into(spacecraftImageView);
        Picasso.with(context).load(url).into(holder.imageView);

//        try {
//            url = new URL(path);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        try {
//            inputStream = url.openStream();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

       // holder.imageView.setImageBitmap(bitmap);
//        Toast.makeText(context,"Url:"+ url, Toast.LENGTH_SHORT).show();
//        Toast.makeText(context,"InputStream:"+ inputStream, Toast.LENGTH_SHORT).show();
//        //holder.imageView.setImageUrl(uploadedCards.get(position).getDatafile());
       // holder.tvDescription  .setText(taskDetailsList.get(position).getDescription());


    }

    @Override
    public int getItemCount() {

        if(uploadedCards  != null){
            return uploadedCards .size();
        }
        return 0;

    }

    class ViewHolderNewItem extends RecyclerView.ViewHolder {
        TextView tvUrl;
        ImageView imageView;

        public ViewHolderNewItem(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            tvUrl = itemView.findViewById(R.id.tvUrl);

        }

    }

}
