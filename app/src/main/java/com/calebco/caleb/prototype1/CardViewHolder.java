package com.calebco.caleb.prototype1;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
//card view holder for
public class CardViewHolder extends RecyclerView.ViewHolder{
    public TextView titleText, recipientText, dateText, id;



    public CardViewHolder(View itemView){
        super(itemView);
        titleText = itemView.findViewById(R.id.titleTextCard);
        recipientText = itemView.findViewById(R.id.recipientTextCard);
        dateText = itemView.findViewById(R.id.dateTextCard);

    }
    public void bind(VideoCardClass card){

        titleText.setText(card.getTitle());
        recipientText.setText(card.getRecipient());
        dateText.setText(card.getDate());
        card.setId(card.getId());

        //test
        Log.d("BIND CALL", "BIND CALLED IN HOLDER TITLE VAL:" + card.getTitle());

    }

    public void setTitle(String title){
       titleText.setText(title);
    }
    public void setRecipient(String recipient){
        recipientText.setText(recipient);

    }
    public void setDate(String date){
        dateText.setText(date);

    }

}
