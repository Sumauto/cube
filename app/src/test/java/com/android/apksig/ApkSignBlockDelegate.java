package com.android.apksig;

import java.lang.reflect.InvocationTargetException;

public class ApkSignBlockDelegate {

    private Object mApkSigningBlock;

    public ApkSignBlockDelegate(DataSourceDelegate inputApk, ZipSectionsDelegate zipSectionsDelegate, ClassLoader classLoader) {
        try {
            mApkSigningBlock = Delegate.apkUtils_findApkSigningBlock().invoke(null, inputApk.getDataSource(),
                    zipSectionsDelegate.getZipSections());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public long getStartOffset() {
        try {
            //noinspection ConstantConditions
            return (Long) mApkSigningBlock.getClass().getMethod("getStartOffset").invoke(mApkSigningBlock);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public DataSourceDelegate getContents() {

        try {
            return new DataSourceDelegate(mApkSigningBlock.getClass().getMethod("getContents").invoke(mApkSigningBlock));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
