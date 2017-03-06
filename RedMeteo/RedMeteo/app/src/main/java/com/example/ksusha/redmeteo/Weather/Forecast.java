package com.example.ksusha.redmeteo.Weather;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.example.ksusha.redmeteo.ActiveAndroidModels.Cities;
import com.example.ksusha.redmeteo.ActiveAndroidModels.ForecastModel;
import com.example.ksusha.redmeteo.MainScreen;
import com.example.ksusha.redmeteo.OpenWeatherMapConnection.Connection;
import com.example.ksusha.redmeteo.R;
import com.example.ksusha.redmeteo.RecyclerViewAdditionalClasses.ForecastList;
import com.example.ksusha.redmeteo.RecyclerViewAdditionalClasses.MyForecastRecyclerViewAdapter;
import com.example.ksusha.redmeteo.WeatherParsers.ForecastParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class Forecast extends Fragment {
    List<ForecastModel> storedWeather;
    List<Cities> city;
    Map<String, String> m;
    ArrayList<Map<String, String>> data;
    final String ATR_DATE       = "date";
    final String ATR_TEMP       = "temp";
    final String ATR_IC         = "icon";
    final String ATR_TEMP_MIN   = "tempmin";
    final String ATR_TEMP_MAX   = "tempmax";
    final String ATR_HUM        = "humidity";
    final String ATR_PRES       = "pressure";
    final String ATR_CITY       = "city";
    final String ATR_COUNTRY    = "country";
    final String ATR_COND       = "condition";
    final String ATR_DESCR      = "description";
    final String ATR_SPEED      = "speed";
    final String ATR_CLOUDS     = "clouds";
    final String ATR_DEG        = "deg";
    private final static String addres = "http://api.openweathermap.org/data/2.5/forecast?q=";
    private final static String api_key = "&mode=json&appid=7b5abb359df4f893ab31852495726aa0";
    RecyclerView recyclerview;
    private List<ForecastList> meberlist;
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.forecast,container,false);
        recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerview.hasFixedSize();
        data = new ArrayList<Map<String, String>>();
        meberlist = new ArrayList<ForecastList>();
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        new ForecastBackTask().execute();
        return view;
    }

    public Forecast() {
    }

    public class ForecastBackTask extends AsyncTask<Object, Object, ArrayList<Map<String, String>>> {
       Connection connection;
        JSONObject jsonObject;
        ForecastParser forecastParser;
        ForecastModel weather;
        MyForecastRecyclerViewAdapter adapter;



        @Override
        public ArrayList<Map<String, String>> doInBackground(Object... params) {
            connection = new Connection();
            jsonObject = new JSONObject();
            forecastParser= new ForecastParser();
            city = Cities.getData();

            SharedPreferences sharedPreferences = Forecast.this.getActivity().getSharedPreferences("UNITS",MODE_PRIVATE);
            String units = sharedPreferences.getString("units","");

            try {
                jsonObject = connection.Request(addres,city.get(city.size()-1).getName(),units,api_key);

                new Delete().from(ForecastModel.class).execute();
                ActiveAndroid.beginTransaction();
                for (int i=0;i<34;i++)
                {
                    weather = new ForecastModel();
                    weather = forecastParser.getData(jsonObject,i);
                    weather.save();
                }
                ActiveAndroid.setTransactionSuccessful();
                ActiveAndroid.endTransaction();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            storedWeather = ForecastModel.getAllWeather();

            for (int i=0;i<storedWeather.size();i++) {
                m = new HashMap<String, String>();
                m.put(ATR_IC,       storedWeather.get(i).getIcon());
                m.put(ATR_TEMP,     String.valueOf(storedWeather.get(i).getTemp())+"°");
                m.put(ATR_DATE,     storedWeather.get(i).getDate());
                m.put(ATR_TEMP_MIN, String.valueOf(storedWeather.get(i).getMin()));
                m.put(ATR_TEMP_MAX, String.valueOf(storedWeather.get(i).getMax()));
                m.put(ATR_HUM,      String.valueOf(storedWeather.get(i).getHum()));
                m.put(ATR_PRES,     String.valueOf(storedWeather.get(i).getPres()));
                m.put(ATR_CITY,     storedWeather.get(i).getCity());
                m.put(ATR_COUNTRY,  storedWeather.get(i).getCountry());
                m.put(ATR_COND,     storedWeather.get(i).getCond());
                m.put(ATR_DESCR,    storedWeather.get(i).getDesc());
                m.put(ATR_SPEED,    String.valueOf(storedWeather.get(i).getSpeed()));
                m.put(ATR_CLOUDS,   String.valueOf(storedWeather.get(i).getClouds()));
                m.put(ATR_DEG,      String.valueOf(storedWeather.get(i).getDeg()));

                data.add(m);
            }
            Log.e("FORECAST","TEMP "+ data);
            return data;
        }

        @Override
        protected void onPostExecute(ArrayList<Map<String, String>> weather) {
            super.onPostExecute(weather);

            for(int i=0;i<weather.size();i++)
            {
                ForecastList member = new ForecastList(
                        weather.get(i).get(ATR_DATE),
                        weather.get(i).get(ATR_IC),
                        weather.get(i).get(ATR_TEMP)

                );
                meberlist.add(member);
            }
            Log.e("FORECAST","TEMP "+ weather.get(0).get(ATR_TEMP));
            adapter = new MyForecastRecyclerViewAdapter(meberlist);
            recyclerview.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }
}
