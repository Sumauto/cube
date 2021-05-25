package com.sumauto.helper.flow

import com.sumauto.helper.log.XLog

open class Task(var name: String = "") {

    private val nextTasks = mutableSetOf<Task>()
    private var condition: Condition? = null
    private var isDone:Boolean=false

    fun setCondition(condition: Condition) {
        this.condition = condition
    }


    fun requestStart() {
        if (condition == null) {
            println("$name condition is null" )
            onStart()
        } else {
            if (condition!!.canStart()) {
                println("$name condition canStart" )

                onStart()
            }
        }

    }

    open fun onStart() {
        println("${name} onStart")
    }

    fun done() {
        isDone=true
        nextTasks.forEach {
            it.requestStart()
        }
    }

    fun isDone(): Boolean {
        return isDone
    }
}