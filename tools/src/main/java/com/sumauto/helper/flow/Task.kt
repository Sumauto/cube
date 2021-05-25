package com.sumauto.helper.flow

open abstract class Task<R>(var name: String = "", var id: Int = 0) {

    enum class State {
        Init, Attached, Started, DONE
    }

    private var result: R? = null
    private var workFlow: WorkFlow? = null
    var state: State = State.Init

    fun onAttach(workFlow: WorkFlow) {
        this.workFlow = workFlow
        state = State.Attached
    }


    fun requestStart() {
        state = State.Started
        println("$name onStart")
        onStart()
    }

    open fun canStart(): Boolean {

        return true
    }

    abstract fun onStart();

    fun done(r: R) {
        state = State.DONE
        workFlow?.onComplete(this)
    }

    fun isDone(): Boolean {
        return state == State.DONE
    }

    fun getResult(): R? {
        return result
    }

}