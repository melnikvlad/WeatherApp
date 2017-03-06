package com.example.ksusha.redmeteo.OpenWeatherMapConnection;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Connection {
    URL url;
    HttpURLConnection httpURLConnection;
    InputStream inputStream;
    BufferedReader reader;
    StringBuilder sb;
    String line,stream;
    JSONObject jsonObject;


    public JSONObject Request(String addres, String city, String units,String api_key) throws IOException, JSONException {

        url = new URL(addres + city + units + api_key);
        httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod(String.valueOf("GET"));
        inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
        stream = ToString(inputStream);
        jsonObject = new JSONObject(stream);

        return jsonObject;
    }

    private String ToString(InputStream is) {
        reader = new BufferedReader(new InputStreamReader(is));
        sb = new StringBuilder();
        try {
            while ((line = reader.readLine()) != null)
                sb.append(line);
            sb.append('\n');
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
