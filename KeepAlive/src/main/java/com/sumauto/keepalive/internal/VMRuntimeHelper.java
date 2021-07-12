package com.sumauto.keepalive.internal;

import com.sumauto.helper.log.XLog;
import com.sumauto.keepalive.ProcessKeeper;

import java.lang.reflect.Method;

public class VMRuntimeHelper {

    private static Method sSetHiddenApiExemptions;
    private static Object sVMRuntime;

    public static void init1() {
        try {
            Method forNameMethod = Class.class.getDeclaredMethod("forName", String.class);

            XLog.d(ProcessKeeper.TAG,"forNameMethod:"+forNameMethod);

            Method getDeclaredMethodMethod = Class.class.getDeclaredMethod(
                    "getDeclaredMethod", String.class, Class[].class);

            XLog.d(ProcessKeeper.TAG,"getDeclaredMethodMethod: "+getDeclaredMethodMethod);

            Class vmRuntimeClass = (Class) forNameMethod.invoke(null, "dalvik.system.VMRuntime");
            XLog.d(ProcessKeeper.TAG,"vmRuntimeClass: "+vmRuntimeClass);

            sSetHiddenApiExemptions = (Method) getDeclaredMethodMethod.invoke(vmRuntimeClass,
                    "setHiddenApiExemptions", new Class[]{String[].class});

            XLog.d(ProcessKeeper.TAG,"sSetHiddenApiExemptions: "+sSetHiddenApiExemptions);

            Method getVMRuntimeMethod = (Method) getDeclaredMethodMethod.invoke(vmRuntimeClass,
                    "getRuntime", null);

            XLog.d(ProcessKeeper.TAG,"getVMRuntimeMethod: "+getVMRuntimeMethod);

            sVMRuntime = getVMRuntimeMethod.invoke(null);
            XLog.d(ProcessKeeper.TAG,"sVMRuntime: "+sVMRuntime);

        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public static void init2() {
        try {
            Method getDeclaredMethodMethod = Class.class.getDeclaredMethod(
                    "getDeclaredMethod", String.class, Class[].class);

            Class<?> vmRuntimeClass = Class.forName("dalvik.system.VMRuntime");
            sSetHiddenApiExemptions = vmRuntimeClass.getDeclaredMethod("setHiddenApiExemptions", String[].class);

            Method getVMRuntimeMethod = (Method) getDeclaredMethodMethod.invoke(vmRuntimeClass,
                    "getRuntime", null);

            sVMRuntime = vmRuntimeClass.getDeclaredMethod("getRuntime").invoke(null);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }
}
