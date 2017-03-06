package com.example.ksusha.redmeteo.Weather;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.activeandroid.query.Delete;
import com.example.ksusha.redmeteo.ActiveAndroidModels.Cities;
import com.example.ksusha.redmeteo.ActiveAndroidModels.CurrentModel;
import com.example.ksusha.redmeteo.OpenWeatherMapConnection.Connection;
import com.example.ksusha.redmeteo.R;
import com.example.ksusha.redmeteo.WeatherParsers.CurrentParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class Current extends Fragment {
    View view;

    TextView cityname,conditionanddescription,temperature,min;
    ImageView icon;
    List<CurrentModel> storedWeather;
    List<Cities> city;
    Map<String, String> m;

    final String ATR_TEMP = "temp";
    final String ATR_TEMP_MIN = "tempmin";
    final String ATR_TEMP_MAX = "tempmax";
    final String ATR_HUM = "humidity";
    final String ATR_PRES = "pressure";
    final String ATR_CITY = "city";
    final String ATR_COND = "condition";
    final String ATR_DESCR = "description";
    final String ATR_IC = "icon";
    final String ATR_SPEED = "speed";
    final String ATR_CLOUDS = "clouds";
    final String ATR_DEG = "deg";
    private final static String addres = "http://api.openweathermap.org/data/2.5/weather?q=";
    private final static String api_key = "&appid=7b5abb359df4f893ab31852495726aa0";
    int[] icons = { R.drawable.icon_01d,
            R.drawable.icon_02d,
            R.drawable.icon_03d,
            R.drawable.icon_04d,
            R.drawable.icon_09d,
            R.drawable.icon_10d,
            R.drawable.icon_11d,
            R.drawable.icon_13d,
            R.drawable.icon_50d};
    int[] iconss = {R.drawable.icon_01n,
            R.drawable.icon_02n,
            R.drawable.icon_03n,
            R.drawable.icon_04n,
            R.drawable.icon_09n,
            R.drawable.icon_10n,
            R.drawable.icon_11n,
            R.drawable.icon_13n};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.current,container,false);

        cityname = (TextView) view.findViewById(R.id.cityname);
        conditionanddescription = (TextView) view.findViewById(R.id.conditionanddescription);
        temperature = (TextView) view.findViewById(R.id.temperature);
        min = (TextView)view.findViewById(R.id.min);
        icon =(ImageView) view.findViewById(R.id.icon);

        new CurrentBackTask().execute();
        return view;
    }


    public  class CurrentBackTask extends AsyncTask<Object, Object, Map<String, String>>
    {
        Connection connection;
        JSONObject jsonObject;
        CurrentParser currentParser;
        CurrentModel currentModel;
        ProgressDialog progressDialog;



        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(getContext(), "", "Doing stuff. Please wait...", true);
        }

        @Override
        protected Map<String, String> doInBackground(Object... objects) {
            city = Cities.getData();

            connection = new Connection();
            jsonObject = new JSONObject();
            currentParser = new CurrentParser();
            storedWeather =  Cities.getData();

            SharedPreferences sharedPreferences = Current.this.getActivity().getSharedPreferences("UNITS",MODE_PRIVATE);
            String units = sharedPreferences.getString("units","");

            try {
                jsonObject = connection.Request(addres, city.get(city.size()-1).getName(),units, api_key);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            new Delete().from(CurrentModel.class).execute();

            currentModel = new CurrentModel();
            try {
                currentModel = currentParser.getCurrent(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            currentModel.save();

            storedWeather = CurrentModel.getData();
            m = new HashMap<String, String>();

            m.put(ATR_TEMP,String.valueOf(storedWeather.get(0).getTemp()));
            m.put(ATR_TEMP_MIN, String.valueOf(storedWeather.get(0).getMin()));
            m.put(ATR_TEMP_MAX, String.valueOf(storedWeather.get(0).getMax()));
            m.put(ATR_HUM, String.valueOf(storedWeather.get(0).getHumidity()));
            m.put(ATR_PRES, String.valueOf(storedWeather.get(0).getPressure()));
            m.put(ATR_CITY, storedWeather.get(0).getCity());
            m.put(ATR_COND, String.valueOf(storedWeather.get(0).getCondition()));
            m.put(ATR_DESCR, String.valueOf(storedWeather.get(0).getDescription()));
            m.put(ATR_IC, storedWeather.get(0).getIcon());
            m.put(ATR_SPEED, String.valueOf(storedWeather.get(0).getSpeed()));
            m.put(ATR_CLOUDS, String.valueOf(storedWeather.get(0).getClouds() + "%"));
            m.put(ATR_DEG, String.valueOf(storedWeather.get(0).getDeg() + "°"));
            Log.e("TAG","MAP "+ m.get(ATR_TEMP));
            return m;
        }



        @Override
        public void onPostExecute(Map<String, String> stringStringMap) {
            super.onPostExecute(stringStringMap);
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
                cityname.setText(m.get(ATR_CITY));
                temperature.setText(m.get(ATR_TEMP)+"°");
                conditionanddescription.setText(m.get(ATR_COND)+", "+m.get(ATR_DESCR));
            min.setText(m.get(ATR_TEMP_MIN)+"    ...      "+m.get(ATR_TEMP_MAX));

            switch (m.get(ATR_IC))
            {
                case "01d":
                    icon.setImageResource(icons[0]);
                    break;
                case "02d":
                    icon.setImageResource(icons[1]);
                    break;
                case "03d":
                    icon.setImageResource(icons[2]);
                    break;
                case "04d":
                    icon.setImageResource(icons[3]);
                    break;
                case "09d":
                   icon.setImageResource(icons[4]);
                    break;
                case "10d":
                    icon.setImageResource(icons[5]);
                    break;
                case "11d":
                    icon.setImageResource(icons[6]);
                    break;
                case "13d":
                  icon.setImageResource(icons[7]);
                    break;
                case "50d":
                    icon.setImageResource(icons[8]);
                    break;
                case "01n":
                    icon.setImageResource(iconss[0]);
                    break;
                case "02n":
                  icon.setImageResource(iconss[1]);
                    break;
                case "03n":
                    icon.setImageResource(iconss[2]);
                    break;
                case "04n":
                  icon.setImageResource(iconss[3]);
                    break;
                case "09n":
                   icon.setImageResource(iconss[4]);
                    break;
                case "10n":
                    icon.setImageResource(iconss[5]);
                    break;
                case "11n":
                    icon.setImageResource(iconss[6]);
                    break;
                case "13n":
                    icon.setImageResource(iconss[7]);
                    break;
                case "50n":
                    icon.setImageResource(icons[8]);
                    break;
            }
        }
    }

}
