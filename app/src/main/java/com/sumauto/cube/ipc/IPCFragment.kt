package com.sumauto.cube.ipc

import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.fragment.app.Fragment
import com.sumauto.cube.remote.RBinder
import com.sumauto.helper.log.XLog

class IPCFragment : Fragment() {

    companion object {
        private const val DESCRIPTOR = "com.sumauto.cube.remote.RBinder"
        const val TAG = "RClient"
    }


    private var service = Intent().apply {
        component = ComponentName(
            "com.sumauto.cube.remote",
            "com.sumauto.cube.remote.RService"
        )

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().startService(service)
        XLog.d(TAG, "onCreate")

        requireActivity().bindService(service, object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                XLog.d(TAG, "onServiceConnected $name $service")
                service?.apply {
                    val queryLocalInterface = queryLocalInterface(DESCRIPTOR)
                    XLog.d(TAG, "queryLocalInterface $queryLocalInterface")

                }
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                XLog.d(TAG, "onServiceDisconnected $name")

            }
        }, Context.BIND_AUTO_CREATE)
    }

    override fun onStart() {
        super.onStart()
        requireActivity().startService(service)
    }

    override fun onStop() {
        super.onStop()
        requireActivity().stopService(service)
    }

}