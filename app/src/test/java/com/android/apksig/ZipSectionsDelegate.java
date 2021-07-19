package com.android.apksig;

public class ZipSectionsDelegate {
    private Object mZipSections;

    public ZipSectionsDelegate(DataSourceDelegate delegate, ClassLoader classLoader) {
        try {
            mZipSections = Delegate.apkUtils_findZipSections().invoke(null, delegate.getDataSource());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object getZipSections() {
        return mZipSections;
    }
}
