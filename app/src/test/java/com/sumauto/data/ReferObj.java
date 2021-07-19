package com.sumauto.data;

public class ReferObj {
    public String name;

    public ReferObj(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ReferObj{" +
                "name='" + name + '\'' +
                '}';
    }
}
