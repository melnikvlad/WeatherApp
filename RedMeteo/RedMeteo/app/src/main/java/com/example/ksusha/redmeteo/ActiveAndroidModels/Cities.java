package com.example.ksusha.redmeteo.ActiveAndroidModels;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.io.Serializable;
import java.util.List;


@Table(name = "City")
public class Cities extends Model implements Serializable {
    @Column(name = "name")
    public String name;

    public static List getData() {
        return new Select().from(Cities.class).execute();
    }


    public Cities() {
        super();
    }

    public Cities(String name) {
        super();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
