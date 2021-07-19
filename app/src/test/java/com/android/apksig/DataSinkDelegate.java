package com.android.apksig;

import java.io.File;
import java.io.FileInputStream;
import java.io.RandomAccessFile;

public class DataSinkDelegate {

    private Object dataSink;

    public DataSinkDelegate(RandomAccessFile f, ClassLoader loader) {
        try {
            dataSink = Delegate.dataSinks_asDataSink().invoke(null, f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
