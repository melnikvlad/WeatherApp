package com.example.ksusha.redmeteo.RecyclerViewAdditionalClasses;


import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ksusha.redmeteo.R;

public class Forecast2ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView date,temp;
    public ImageView icon;

    public Forecast2ViewHolder(View itemView) {
        super(itemView);
        date = (TextView) itemView.findViewById(R.id.date);
        temp = (TextView) itemView.findViewById(R.id.temp);
        icon = (ImageView) itemView.findViewById(R.id.icon);
        itemView.setOnClickListener(this);
        
    }


    @Override
    public void onClick(View view) {
        int position  =   getAdapterPosition();
        Log.e("TAG","POS1 "+ position);
    }
}

