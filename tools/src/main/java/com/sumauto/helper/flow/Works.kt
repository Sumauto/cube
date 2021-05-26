package com.sumauto.helper.flow

import androidx.lifecycle.LifecycleOwner
import com.sumauto.helper.log.XLog


class Works {

    companion object {
        const val TAG = "WorkFlow"

        @JvmStatic
        fun newInstance(): Works {
            return Works()
        }
    }

    private var flowList = mutableListOf<WorkFlow>()
    private var owner: LifecycleOwner? = null

    fun start() {
        XLog.d(TAG,"works start")

        execNext()
    }

    private fun execNext() {
        if (flowList.isNotEmpty()) {
            XLog.d(TAG,"execNext work flow")
            flowList[0].run()
        }else{
            XLog.d(TAG,"nor more work flow")
        }
    }

    fun addFlow(flow: WorkFlow): Works {
        flow.complete = Runnable {
            XLog.d(TAG,"work flow complete")
            flowList.remove(flow)
            execNext()
        }
        flowList.add(flow)
        return this
    }

}