package com.android.apksig;

import java.io.RandomAccessFile;

public class DataSourceDelegate {

    private Object dataSource;

    public DataSourceDelegate(Object dataSource) {
        this.dataSource = dataSource;
    }

    public DataSourceDelegate(RandomAccessFile f, ClassLoader loader) {
        try {
            dataSource = Delegate.dataSources_asDataSource().invoke(null, f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object getDataSource() {
        return dataSource;
    }
}
