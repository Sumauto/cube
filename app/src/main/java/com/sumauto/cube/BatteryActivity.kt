package com.sumauto.cube

import android.os.BatteryManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class BatteryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_battery)
        var manager: BatteryManager = getSystemService(BATTERY_SERVICE) as BatteryManager
        manager.getLongProperty(BatteryManager.BATTERY_PROPERTY_STATUS)
    }
}