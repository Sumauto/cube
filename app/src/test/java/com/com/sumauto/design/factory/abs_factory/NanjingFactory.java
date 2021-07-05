package com.com.sumauto.design.factory.abs_factory;

import com.com.sumauto.design.factory.AbsFruit;
import com.com.sumauto.design.factory.items.Apple;
import com.com.sumauto.design.factory.items.Orange;

public class NanjingFactory extends AbsFactory {

    @Override
    public AbsFruit createApple() {
        Apple apple = new Apple();
        apple.setPlace("NanJing");
        return apple;
    }

    @Override
    public AbsFruit createOrange() {
        Orange orange = new Orange();
        orange.setPlace("NanJing");
        return orange;
    }
}
