package com.example.loginregistration_web.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginregistration_web.R;
import com.example.loginregistration_web.activities.HandwritingActivity;
import com.example.loginregistration_web.models.CardDetails;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class CardDetailsAdapter extends RecyclerView.Adapter<CardDetailsAdapter.ViewHolderNewItem > {


    Context context;

    List<CardDetails> cardDetailsList;
    ArrayList<String> cards = new ArrayList<>();
    String taskid;

    ArrayList<String> cardsid = new ArrayList<>();

    public CardDetailsAdapter(Context context, List<CardDetails>cardDetailsList, String taskid) {
        this.context = context;
        this.cardDetailsList  = cardDetailsList;
        this.taskid = taskid;
    }

    public void setItemList(List<CardDetails> cardDetailsList) {
        this.cardDetailsList  = cardDetailsList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public CardDetailsAdapter.ViewHolderNewItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext() ).inflate(R.layout.cardinfo_row,parent, false);
        //ViewHolderNew viewHolderNew = new ViewHolderNew(v) ;

        return new CardDetailsAdapter.ViewHolderNewItem(v) ;
    }

    @Override
    public void onBindViewHolder(@NonNull CardDetailsAdapter.ViewHolderNewItem holder, final int position) {

        holder.tvCardname .setText(cardDetailsList.get(position).getText() );
        holder.tvDescription  .setText(cardDetailsList.get(position).getDescription());
        holder.tvPoints.setText("Points: "+cardDetailsList.get(position).getPoints());



       // final String[] cards= new Array<String>();


        cardsid.add(cardDetailsList.get(position).getCardid()+"");
        cards.add(cardDetailsList.get(position).getText()+"");   //Here getText is function of cardDetailsList class



       // final ArrayList<String> cards = new ArrayList<>();
       // cards.add(position,cardDetailsList.get(position).getText());
       // cards.add(mystring);
       // final List <String> cards = new ArrayList();
       // cards.add(cardDetailsList.get(position).getText() );


        final String cardid = cardDetailsList.get(position).getCardid();
        final String cardname = cardDetailsList.get(position).getText();

        holder.btnOpenCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent cardIntent = new Intent(context, HandwritingActivity.class);

                cardIntent.putExtra("taskid",taskid);

                cardIntent.putExtra("cardsid",cardsid);
                cardIntent.putExtra("cards",cards);
                cardIntent.putExtra("position",position);
                //cardIntent.putExtra("cardid",cardid);
                //cardIntent.putExtra("cardname",cardname);
                //CardIntent.putExtra("description",CardDetailsList.get(position).getDescription());
                //CardIntent.putExtra("duedate",CardDetailsList.get(position).getDuedate());
                context.startActivity(cardIntent) ;



            }
        });



    }

    @Override
    public int getItemCount() {

        if(cardDetailsList  != null){
            return cardDetailsList .size();
        }
        return 0;

    }

    class ViewHolderNewItem extends RecyclerView.ViewHolder {
        TextView tvCardname,tvDescription,tvPoints;
        Button btnOpenCard;


        public ViewHolderNewItem (View itemView) {
            super(itemView);
            tvCardname   = itemView.findViewById(R.id.cardname);
            tvDescription   = itemView.findViewById(R.id.description);
            tvPoints  = itemView.findViewById(R.id.points);
            btnOpenCard = itemView.findViewById(R.id.btnOpenCard);

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