package com.com.sumauto.design.factory.items;

import com.com.sumauto.design.factory.AbsFruit;

public class Apple extends AbsFruit {
    @Override
    public String getName() {
        return getPlace()+" 苹果";
    }

}
