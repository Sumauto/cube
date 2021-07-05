package com.com.sumauto.design.factory;

public abstract class AbsFruit {

    private String place;

    public abstract String getName();

    public String getPlace() {
        return this.place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
