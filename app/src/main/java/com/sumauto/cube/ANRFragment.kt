package com.sumauto.cube

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sumauto.cube.databinding.FragmentAnrBinding
import com.sumauto.helper.log.XLog
import com.sumauto.helper.utils.SystemUI

class ANRFragment : Fragment() {
    private var mBinding: FragmentAnrBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentAnrBinding.inflate(inflater, container, false)
        return mBinding!!.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        SystemUI.applyStatusBarHeight(view)
        mBinding!!.apply {
            //触摸事件5秒
            inputAnr.setOnTouchListener { v, event ->
                XLog.d("anr","event_start:"+event.action)
                Thread.sleep(6000)
                XLog.d("anr","event_end:"+event.action)
                return@setOnTouchListener false
            }

            inputLess.setOnTouchListener { v, event ->
                XLog.d("anr","event_start:"+event.action)
                Thread.sleep(4000)
                XLog.d("anr","event_end:"+event.action)
                return@setOnTouchListener false
            }

            startServiceAnr.setOnClickListener {

            }
        }

    }

}