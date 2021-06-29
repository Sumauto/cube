package com.sumauto.cube.rx

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sumauto.cube.databinding.ActivityRxBinding

class RxActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflate = ActivityRxBinding.inflate(layoutInflater)
        setContentView(inflate.root)
    }
}