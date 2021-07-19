package com.sumauto.cube

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sumauto.cube.databinding.FragmentMemoryLeakBinding
import com.sumauto.helper.log.XLog
import java.lang.ref.WeakReference

class MemoryLeakFragment : Fragment() {
    companion object {
        const val TAG = "memory-leak"
        fun checkRefer(fragment: MemoryLeakFragment) {
            val weakReference = WeakReference(fragment)

            Thread {
                while (weakReference.get() != null) {
                    XLog.d(TAG, "check, in refer")
                    Thread.sleep(3000)
                }
                XLog.d(TAG, "no refer")
            }.start()
        }
    }

    private var handler = Handler(Looper.getMainLooper()) {
        XLog.d(TAG, "handler--->" + it.what)
        testRefer()
        return@Handler true
    }

    private var mBinding: FragmentMemoryLeakBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentMemoryLeakBinding.inflate(inflater, container, false)
        mBinding!!.start.setOnClickListener {
            startActivity(Intent(it.context,LeakActivity::class.java))
        }

        mBinding!!.gc.setOnClickListener {
            System.gc()
        }
        return mBinding!!.root
    }

    private fun testRefer() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkRefer(this)
        Thread {
            for (i in (0..10)) {
                handler.sendEmptyMessage(i)
                Thread.sleep(3000)
            }
        }.start()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }


}