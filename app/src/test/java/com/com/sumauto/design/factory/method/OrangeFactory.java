package com.com.sumauto.design.factory.method;

import com.com.sumauto.design.factory.AbsFruit;
import com.com.sumauto.design.factory.items.Orange;

public class OrangeFactory extends AbsFruitFactory {
    @Override
    public AbsFruit create() {
        return new Orange();
    }
}
