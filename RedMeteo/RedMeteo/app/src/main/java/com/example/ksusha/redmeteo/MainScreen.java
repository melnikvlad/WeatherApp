package com.example.ksusha.redmeteo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.ksusha.redmeteo.ActiveAndroidModels.Cities;
import com.example.ksusha.redmeteo.Weather.Current;
import com.example.ksusha.redmeteo.Weather.Forecast;
import com.example.ksusha.redmeteo.Weather.Forecast2;

import java.util.List;

import static com.activeandroid.Cache.getContext;
import static com.example.ksusha.redmeteo.R.styleable.NavigationView;

public class MainScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
List<Cities> citiesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("units", "&units=metric");
        editor.apply();

        citiesList= Cities.getData();
        FragmentManager fragmentManager = getSupportFragmentManager();
        if(citiesList.isEmpty())
        {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame_current,new Input())
                    .replace(R.id.content_frame_forecast2_list,new Helper())
                    .replace(R.id.content_frame_forecast_list,new Helper())
                    .commit();
            fragmentManager.beginTransaction().addToBackStack(null);
        }else
        {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame_current,new Current())
                    .replace(R.id.content_frame_forecast2_list,new Forecast2())
                    .replace(R.id.content_frame_forecast_list,new Forecast())
                    .commit();
            fragmentManager.beginTransaction().addToBackStack(null);
        }


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (id ==R.id.nav_first) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame_current,new Current())
                    .replace(R.id.content_frame_forecast2_list,new Forecast2())
                    .replace(R.id.content_frame_forecast_list,new Forecast())
                    .commit();
            fragmentManager.beginTransaction().addToBackStack(null);
        }
        else if (id ==R.id.nav_second)
        {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame_current,new Input())
                    .replace(R.id.content_frame_forecast2_list,new Helper())
                    .replace(R.id.content_frame_forecast_list,new Helper())
                    .commit();
            fragmentManager.beginTransaction().addToBackStack(null);
        }
        else if (id ==R.id.nav_third)
        {
            SharedPreferences preferences = getContext().getSharedPreferences("UNITS", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("units","&units=imperial");
            editor.apply();

            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame_current,new Current())
                    .replace(R.id.content_frame_forecast2_list,new Forecast2())
                    .replace(R.id.content_frame_forecast_list,new Forecast())
                    .commit();
            fragmentManager.beginTransaction().addToBackStack(null);
        }
        else if (id ==R.id.nav_fouth)
        {
            SharedPreferences preferences = getContext().getSharedPreferences("UNITS", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("units","&units=metric");
            editor.apply();

            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame_current,new Current())
                    .replace(R.id.content_frame_forecast2_list,new Forecast2())
                    .replace(R.id.content_frame_forecast_list,new Forecast())
                    .commit();
            fragmentManager.beginTransaction().addToBackStack(null);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
