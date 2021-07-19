package com.android.apksig;

import java.io.RandomAccessFile;
import java.lang.reflect.Method;

public class Delegate {
    private static ClassLoader loader;


    public static void init(ClassLoader loader) {
        Delegate.loader = loader;
    }
    public static Class<?> dataSources() {
        return cls("com.android.apksig.util.DataSources");
    }

    public static Method dataSources_asDataSource() throws NoSuchMethodException {
        return dataSources().getMethod("asDataSource", RandomAccessFile.class);
    }

    public static Class<?> dataSource() {
        return cls("com.android.apksig.util.DataSource");
    }

    public static Class<?> apkUtils() {
        return cls("com.android.apksig.apk.ApkUtils");
    }

    public static Class<?> zipSections() {
        return cls("com.android.apksig.apk.ApkUtils$ZipSections");
    }

    public static Class<?> dataSinks() {
        return cls("com.android.apksig.util.DataSinks");
    }

    public static Method dataSinks_asDataSink() throws NoSuchMethodException {
        return dataSinks().getMethod("asDataSink", RandomAccessFile.class);
    }


    public static Method apkUtils_findApkSigningBlock() throws NoSuchMethodException {
        return apkUtils().getMethod("findApkSigningBlock", dataSource(),
                zipSections());
    }

    public static Method apkUtils_findZipSections() throws NoSuchMethodException {
        return apkUtils().getMethod("findZipSections", dataSource());
    }

    private static Class<?> cls(String name) {
        try {
            return loader.loadClass(name);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }


}
