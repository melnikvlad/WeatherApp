package com.example.ksusha.redmeteo.RecyclerViewAdditionalClasses;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringDef;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ksusha.redmeteo.Info;
import com.example.ksusha.redmeteo.MainScreen;
import com.example.ksusha.redmeteo.R;
import com.example.ksusha.redmeteo.Weather.Current;
import com.example.ksusha.redmeteo.Weather.Forecast;
import com.example.ksusha.redmeteo.Weather.Forecast2;

public class ForecastViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{

    public TextView date,temp,vis;
    public ImageView icon;
    private Context context;

    public ForecastViewHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();

        date = (TextView) itemView.findViewById(R.id.date);
        temp = (TextView) itemView.findViewById(R.id.temp);
        icon = (ImageView) itemView.findViewById(R.id.icon);
        itemView.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        int position = getAdapterPosition();
        Info info = new Info();
        Bundle bundle = new Bundle();
        bundle.putInt("tag", position);
        info.setArguments(bundle);
        FragmentManager fragmentManager = ((MainScreen)context).getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame_forecast_list,info).commit();
        fragmentManager.beginTransaction().addToBackStack(null);


        Intent intent = new Intent(context,Info.class);

        Log.e("TAG", "POS2 " + position);
    }
}

