package com.example.ksusha.redmeteo.RecyclerViewAdditionalClasses;



public class ForecastList {
    public String iconn;
    public String date;
    public String temp;

    public ForecastList() {
    }

    public ForecastList(String date, String iconn, String temp) {
        this.date = date;
        this.iconn = iconn;
        this.temp = temp;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIconn() {
        return iconn;
    }

    public void setIconn(String iconn) {
        this.iconn = iconn;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }
}
