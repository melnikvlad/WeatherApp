package com.example.ksusha.redmeteo.ActiveAndroidModels;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.io.Serializable;
import java.util.List;

@Table(name = "CurrentWeather")
public class CurrentModel extends Model implements Serializable {
    @Column(name = "City")
    public String city;
    @Column(name = "Rain")
    public double rain;
    @Column(name = "Clouds")
    public int clouds;
    @Column(name = "Deg")
    public int deg;
    @Column(name = "Speed")
    public double speed;
    @Column(name = "Pressure")
    public double pressure;
    @Column(name = "Humidity")
    public int humidity;
    @Column(name = "Temperature")
    public double temp;
    @Column(name = "Min")
    public double min;
    @Column(name = "Max")
    public double max;
    @Column(name = "Condition")
    public String condition;
    @Column(name = "Description")
    public String description;
    @Column(name = "Icon")
    public String icon;

    public static List getData() {
        return new Select().from(CurrentModel.class).execute();
    }

    public CurrentModel() {
        super();
    }

    public CurrentModel(String city, int clouds, String condition, int deg, String description, int humidity, String icon, double max, double min, double pressure, double rain, double speed, double temp) {
       super();
        this.city = city;
        this.clouds = clouds;
        this.condition = condition;
        this.deg = deg;
        this.description = description;
        this.humidity = humidity;
        this.icon = icon;
        this.max = max;
        this.min = min;
        this.pressure = pressure;
        this.rain = rain;
        this.speed = speed;
        this.temp = temp;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getClouds() {
        return clouds;
    }

    public void setClouds(int clouds) {
        this.clouds = clouds;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public int getDeg() {
        return deg;
    }

    public void setDeg(int deg) {
        this.deg = deg;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getRain() {
        return rain;
    }

    public void setRain(double rain) {
        this.rain = rain;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }
}
