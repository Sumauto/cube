package com.sumauto.data.proto;

import android.content.Context;

import androidx.datastore.preferences.core.Preferences;

public class ProtoHelper {

    public static <T> T get(Context context, String key, T defaultValue) {
        Preferences.Key<T> pfk = new Preferences.Key<>(key);
        return ProtoConfig.INSTANCE.get(context, pfk, defaultValue);
    }

    public static <T> void write(Context context, String key, T value) {
        Preferences.Key<T> pfk = new Preferences.Key<>(key);
        ProtoConfig.INSTANCE.set(context, pfk, value);
    }
}
