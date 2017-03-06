package com.example.ksusha.redmeteo.ActiveAndroidModels;


import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;

public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Configuration.Builder configurationBuilder = new Configuration.Builder(this);

        configurationBuilder.addModelClass(CurrentModel.class);
        configurationBuilder.addModelClass(ForecastModel.class);
        configurationBuilder.addModelClass(Forecast2Model.class);
        configurationBuilder.addModelClass(Cities.class);


        ActiveAndroid.initialize(configurationBuilder.create());
    }
}
