package com.example.loginregistration_web.Adapters;

import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginregistration_web.R;
import com.example.loginregistration_web.activities.CardActivity;

import com.example.loginregistration_web.models.ProgressBarPercent;
import com.example.loginregistration_web.models.TaskDetails;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.loginregistration_web.api.RetrofitClient.getInstance;


public class TaskDetailsAdapter extends RecyclerView.Adapter<TaskDetailsAdapter.ViewHolderNewItem > {


    Context context;

    List<TaskDetails> taskDetailsList;
    public static ProgressBarPercent progressBarPercent;

    public TaskDetailsAdapter(Context context, List<TaskDetails>taskDetailsList) {
        this.context = context;
        this.taskDetailsList  = taskDetailsList;

    }

    public void setItemList(List<TaskDetails> TaskDetailsList) {
        this.taskDetailsList  = TaskDetailsList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public TaskDetailsAdapter.ViewHolderNewItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext() ).inflate(R.layout.taskinfo_row,parent, false);
        //ViewHolderNew viewHolderNew = new ViewHolderNew(v) ;

        return new TaskDetailsAdapter.ViewHolderNewItem(v) ;
    }

    @Override
    public void onBindViewHolder(@NonNull final TaskDetailsAdapter.ViewHolderNewItem holder, int position) {


        holder.tvTaskname .setText(taskDetailsList.get(position).getTaskname() );
        holder.tvDescription  .setText(taskDetailsList.get(position).getDescription());
        holder.tvDuedate.setText(taskDetailsList.get(position).getDuedate());

        Call<ProgressBarPercent> call = getInstance()
                .getApi()
                .getCardPercent(taskDetailsList.get(position).getUserid() ,
                        taskDetailsList.get(position).getTaskid() ) ;
        call.enqueue(new Callback<ProgressBarPercent>() {
            @Override
            public void onResponse(Call<ProgressBarPercent> call, Response<ProgressBarPercent> response) {
             progressBarPercent = response.body();

                holder.pb.setProgress(Integer.parseInt(progressBarPercent.getPercentage()));
                holder.tv.setText( Integer.parseInt(progressBarPercent.getPercentage())+ "%");

            }

            @Override
            public void onFailure(Call<ProgressBarPercent> call, Throwable t) {

            }
        });

        final String taskid = taskDetailsList.get(position).getTaskid();
        final String taskname = taskDetailsList.get(position).getTaskname();


        holder.btnOpenTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent taskIntent = new Intent(context, CardActivity.class);

                taskIntent.putExtra("taskid",taskid);
                taskIntent.putExtra("taskname",taskname);
//                taskIntent.putExtra("description",taskDetailsList.get(position).getDescription());
//                taskIntent.putExtra("duedate",taskDetailsList.get(position).getDuedate());
                context.startActivity(taskIntent);


            }
        });



    }

    @Override
    public int getItemCount() {

        if(taskDetailsList  != null){
            return taskDetailsList .size();
        }
        return 0;

    }

    class ViewHolderNewItem extends RecyclerView.ViewHolder {
        TextView tvTaskname,tvDescription,tvDuedate;
        Button btnOpenTask;
        ProgressBar pb;
        TextView tv;

        public ViewHolderNewItem (View itemView) {
            super(itemView);
            tvTaskname   = itemView.findViewById(R.id.taskname);
            tvDescription   = itemView.findViewById(R.id.description);
            tvDuedate  = itemView.findViewById(R.id.duedate);
            btnOpenTask = itemView.findViewById(R.id.btnOpenTask);
            pb= itemView.findViewById(R.id.pbId);
            tv=itemView.findViewById(R.id.tvId);


//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(context, " You Clicked the card",Toast.LENGTH_LONG ).show();
//                    Intent intent= new Intent( context ,NextActivity.class) ;
//                    context.startActivity(intent ) ;
//
//                }
//            }) ;

        }
    }


}