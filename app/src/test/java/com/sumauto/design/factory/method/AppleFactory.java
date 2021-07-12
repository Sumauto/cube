package com.sumauto.design.factory.method;

import com.sumauto.design.factory.items.Apple;
import com.sumauto.design.factory.AbsFruit;

public class AppleFactory extends AbsFruitFactory {
    @Override
    public AbsFruit create() {
        return new Apple();
    }
}
