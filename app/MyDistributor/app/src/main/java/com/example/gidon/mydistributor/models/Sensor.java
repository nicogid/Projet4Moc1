package com.example.gidon.mydistributor.models;

import java.text.DateFormat;

/**
 * Created by gidon on 20/06/2017.
 */

public class Sensor {
    private int id;
    private String name;
    private DateFormat date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DateFormat getDate() {
        return date;
    }

    public void setDate(DateFormat date) {
        this.date = date;
    }
}
