package com.example.ksusha.redmeteo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ksusha.redmeteo.ActiveAndroidModels.ForecastModel;
import com.example.ksusha.redmeteo.Weather.Forecast;

import java.util.List;


public class Info extends Fragment
{
    View view;
    TextView back,now,humidity,pressure,clouds,wind,temp,max,deg;
    List<ForecastModel> storedWeather;

    int pos;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.info,container,false);
        back = (TextView)view.findViewById(R.id.back);
        now = (TextView)view.findViewById(R.id.now);
        temp = (TextView)view.findViewById(R.id.temp);
        max = (TextView)view.findViewById(R.id.max);
        humidity = (TextView)view.findViewById(R.id.humidity);
        pressure = (TextView)view.findViewById(R.id.pressure);
        clouds = (TextView)view.findViewById(R.id.clouds);
        wind = (TextView)view.findViewById(R.id.wind);
        deg = (TextView)view.findViewById(R.id.deg) ;

        Bundle bundle = getArguments();
        if (bundle != null) {
            pos= bundle.getInt("tag");
        }

        storedWeather = ForecastModel.getAllWeather();

        now.setText("Сейчас "+storedWeather.get(pos).getCond()+","+storedWeather.get(pos).getDesc() );
        temp.setText(String.valueOf(storedWeather.get(pos).getTemp()));
        max.setText(String.valueOf(storedWeather.get(pos).getMax())+".");

        humidity.setText(String.valueOf(storedWeather.get(pos).getHum()+"%"));
        pressure.setText(String.valueOf(storedWeather.get(pos).getPres()));
        clouds.setText(String.valueOf(storedWeather.get(pos).getClouds()+"%"));
        wind.setText(String.valueOf(storedWeather.get(pos).getSpeed()));
        deg.setText(String.valueOf(storedWeather.get(pos).getDeg()));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame_forecast_list,new Forecast()).commit();
                fragmentManager.beginTransaction().addToBackStack(null);
            }
        });

        return view;
    }
}