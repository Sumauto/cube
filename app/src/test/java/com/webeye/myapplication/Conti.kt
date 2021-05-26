package com.webeye.myapplication

import java.util.*

fun main() {
    val array = arrayOf(0, 1, 0, 3, 12)
    println(array.contentToString())
    moveToEnd(0, array)
    println(array.contentToString())

}

fun moveToEnd(value: Int, array: Array<Int>) {
    val size = array.size
    for (i in 0 until size) {
        
    }
}

fun removeVal(value: Int, array: Array<Int>): Int {
    val size = array.size
    var findCount = 0
    for (i in (0 until size)) {
        if (array[i] == value) {
            findCount++
        } else {
            array[i - findCount] = array[i]
        }
    }
    return findCount
}

