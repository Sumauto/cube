package com.sumauto

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sumauto.cube.R
import com.sumauto.helper.utils.SystemUI

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE

        SystemUI.initStatusBarHeight(window.decorView)

    }




}