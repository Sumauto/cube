package com.sumauto.helper.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.Window;

import androidx.annotation.RequiresApi;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.sumauto.helper.Sumauto;
import com.sumauto.helper.store.PK;
import com.sumauto.tools.R;

import static android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
import static android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

public class SystemUI implements Parcelable {
    public static final String TAG = "SystemUI";
    public static final int NO_COLOR = -1;
    private boolean isLightStatusBar;
    private boolean layoutFromStatusBar;
    private boolean layoutFullScreen = true;
    private boolean hideNavigation = true;
    private boolean layoutHideNavigation;
    private int statusBarBackgroundColor;
    private int navigationBarColor = Color.parseColor("#88000000");
    private boolean noSystemUI = false;

    private int flags;

    public SystemUI() {
    }

    protected SystemUI(Parcel in) {
        isLightStatusBar = in.readByte() != 0;
        layoutFromStatusBar = in.readByte() != 0;
        layoutFullScreen = in.readByte() != 0;
        hideNavigation = in.readByte() != 0;
        layoutHideNavigation = in.readByte() != 0;
        statusBarBackgroundColor = in.readInt();
        navigationBarColor = in.readInt();
        noSystemUI = in.readByte() != 0;
        flags = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isLightStatusBar ? 1 : 0));
        dest.writeByte((byte) (layoutFromStatusBar ? 1 : 0));
        dest.writeByte((byte) (layoutFullScreen ? 1 : 0));
        dest.writeByte((byte) (hideNavigation ? 1 : 0));
        dest.writeByte((byte) (layoutHideNavigation ? 1 : 0));
        dest.writeInt(statusBarBackgroundColor);
        dest.writeInt(navigationBarColor);
        dest.writeByte((byte) (noSystemUI ? 1 : 0));
        dest.writeInt(flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SystemUI> CREATOR = new Creator<SystemUI>() {
        @Override
        public SystemUI createFromParcel(Parcel in) {
            return new SystemUI(in);
        }

        @Override
        public SystemUI[] newArray(int size) {
            return new SystemUI[size];
        }
    };

    public void setLightStatusBar(boolean lightStatusBar) {
        isLightStatusBar = lightStatusBar;
    }

    public void setLayoutFromStatusBar(boolean layoutFromStatusBar) {
        this.layoutFromStatusBar = layoutFromStatusBar;
    }

    public void setLayoutFullScreen(boolean layoutFullScreen) {
        this.layoutFullScreen = layoutFullScreen;
    }

    public void setStatusBarBackgroundTransparent() {
        setStatusBarBackgroundColor(Color.TRANSPARENT);
    }

    public void setNoSystemUI(boolean noSystemUI) {
        this.noSystemUI = noSystemUI;
    }

    public void setHideNavigation(boolean hideNavigation) {
        this.hideNavigation = hideNavigation;
    }

    public void setLayoutHideNavigation(boolean layoutHideNavigation) {
        this.layoutHideNavigation = layoutHideNavigation;
    }

    public void setNavigationBarColor(int navigationBarColor) {
        this.navigationBarColor = navigationBarColor;
    }

    public void setStatusBarBackgroundColor(int statusBarBackgroundColor) {
        this.statusBarBackgroundColor = statusBarBackgroundColor;
    }

    public void apply(Window window) {
        if (noSystemUI) {
            return;
        }
        flags = window.getDecorView().getSystemUiVisibility();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            applyLightStatusBar23();
        } else {
            applyLightStatusBar(window);
        }

        applyFlags(SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN, layoutFromStatusBar);
        applyFlags(SYSTEM_UI_FLAG_HIDE_NAVIGATION, hideNavigation);
        applyFlags(SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION, hideNavigation);
        applyFlags(SYSTEM_UI_FLAG_IMMERSIVE_STICKY, hideNavigation);
        if (layoutFullScreen) {
            applyFlags(SYSTEM_UI_FLAG_LAYOUT_STABLE, layoutFullScreen);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else {

            int color = Color.TRANSPARENT;
            window.setStatusBarColor(color);
        }
//        applyFlags(SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION, layoutHideNavigation);
//        window.setStatusBarColor(statusBarBackgroundColor);
        if (layoutHideNavigation) {
            window.setNavigationBarColor(navigationBarColor);
        }

        window.getDecorView().setSystemUiVisibility(flags);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void applyLightStatusBar23() {
        if (isLightStatusBar) {
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        } else {
            flags &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        }
    }

    private void applyLightStatusBar(Window window) {
    }

    private void applyFlags(int additionFlag, boolean isAdd) {
        if (isAdd) {
            flags |= additionFlag;
        } else {
            flags &= ~additionFlag;
        }
    }
//    public static void applyFullScreen(Window window) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
//                View decorView = window.getDecorView();
//                //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
//                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                        | SYSTEM_UI_FLAG_LAYOUT_STABLE;
//                decorView.setSystemUiVisibility(option);
//                //window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//                window.setStatusBarColor(Color.TRANSPARENT);
//                //导航栏颜色也可以正常设置
////                window.setNavigationBarColor(Color.TRANSPARENT);
//            } else {
//                WindowManager.LayoutParams attributes = window.getAttributes();
//                int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
//                int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
//                attributes.flags |= flagTranslucentStatus;
////                attributes.flags |= flagTranslucentNavigation;
//                window.setAttributes(attributes);
//            }
//        }
//    }

    public static void initStatusBarHeight(View view) {

        ViewCompat.setOnApplyWindowInsetsListener(view, (v, insets) -> {
            int statusBar = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top;
            int captionBar = insets.getInsets(WindowInsetsCompat.Type.captionBar()).top;
            Log.d(TAG, "apply:" + captionBar + " " + statusBar);
            if (statusBar != 0) {
                SystemUI.setStatusBarHeight(statusBar);
            }
            v.setOnApplyWindowInsetsListener(null);
            return insets;

        });
        view.requestApplyInsets();
    }

    public static void applyStatusBarHeight(View v) {
        int statusBarHeight = getStatusBarHeight();
        Log.d(TAG, "applyStatusBarHeight:" + statusBarHeight);

        v.setPadding(v.getPaddingLeft(), statusBarHeight, v.getPaddingRight(), v.getPaddingBottom());
    }


    public static void setStatusBarHeight(int height) {
        Sumauto.INSTANCE.sharedPreference().edit().putInt(PK.STATUS_BAR_HEIGHT, height).apply();
    }

    public static int getStatusBarHeight() {
        return Sumauto.INSTANCE.sharedPreference().getInt(PK.STATUS_BAR_HEIGHT, 0);
    }

}
