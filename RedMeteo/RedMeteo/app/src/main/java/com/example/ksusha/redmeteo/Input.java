package com.example.ksusha.redmeteo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.activeandroid.query.Delete;
import com.example.ksusha.redmeteo.ActiveAndroidModels.Cities;
import com.example.ksusha.redmeteo.Weather.Current;
import com.example.ksusha.redmeteo.Weather.Forecast;
import com.example.ksusha.redmeteo.Weather.Forecast2;


public class Input extends Fragment {
    Context context;
    View view;
    EditText editText;
    Button button;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.input,container,false);
        editText = (EditText)view.findViewById(R.id.editcity);
        button = (Button)view.findViewById(R.id.btn);
       final FragmentManager fragmentManager = getFragmentManager();

        final Cities cities = new Cities();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                cities.setName(editText.getText().toString());
                cities.save();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame_current,new Current())
                        .replace(R.id.content_frame_forecast2_list,new Forecast2())
                        .replace(R.id.content_frame_forecast_list,new Forecast())
                        .commit();
                fragmentManager.beginTransaction().addToBackStack(null);
            }
        });

        return view;
    }
}
