package com.sumauto.data;

import android.content.Context;
import android.util.Log;

import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava3.RxDataStore;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.functions.Function;

public class DataStoreHelper {
    public static final String TAG = "DataStoreHelper";

    public static DataStoreHelper get() {
        return Holder.INSTANCE;
    }

    private RxDataStore<Preferences> mDataStorePreference;

    public void init(Context context) {

        mDataStorePreference = new RxPreferenceDataStoreBuilder(context, "settings").build();
    }

    public <T> T get(String key, T defaultValue) {
        Log.d(TAG, "start get:" + key + " default:" + defaultValue);
        return mDataStorePreference.data().map(preferences -> {
            Preferences.Key<T> prefKey = new Preferences.Key<>(key);

            T t = preferences.get(prefKey);
            if (t == null) {
                t = defaultValue;
                Log.d(TAG, "no value for " + key);
            }
            Log.d(TAG, "get:" + key + " return:" + t);

            return t;
        }).blockingFirst();
    }

    public <T> void put(String key, T value) {
        Log.d(TAG, "put:" + key + " v:" + value);
        mDataStorePreference.updateDataAsync(prefs -> {
            MutablePreferences mutablePreferences = prefs.toMutablePreferences();
            mutablePreferences.set(new Preferences.Key<>(key), value);
            return Single.just(mutablePreferences);
        }).subscribe();
    }

    private static final class Holder {
        private static final DataStoreHelper INSTANCE = new DataStoreHelper();
    }
}
