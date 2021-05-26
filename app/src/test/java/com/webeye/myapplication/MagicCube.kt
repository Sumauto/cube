package com.webeye.myapplication

class MagicCube(private val order: Int) {
    var sideSize = order * order;
    val topInfo: Array<Int> = Array(sideSize, init = { it })
    val bottomInfo: Array<Int> = Array(sideSize, init = { it })
    val leftInfo: Array<Int> = Array(sideSize, init = { it })
    val rightInfo: Array<Int> = Array(sideSize, init = { it })
    val frontInfo: Array<Int> = Array(sideSize, init = { it })
    val backInfo: Array<Int> = Array(sideSize, init = { it })

    fun setSideInfo(side: Side, vararg info: Int): Unit {
        when (side) {
            Side.TOP -> copySideInfo(topInfo, *info)
            Side.BOTTOM -> copySideInfo(bottomInfo, *info)
            Side.LEFT -> copySideInfo(leftInfo, *info)
            Side.RIGHT -> copySideInfo(rightInfo, *info)
            Side.FRONT -> copySideInfo(frontInfo, *info)
            Side.BACK -> copySideInfo(backInfo, *info)
        }
    }

    fun matrix(side: Side, index: Int) {

    }

    private fun copySideInfo(info: Array<Int>, vararg newInfo: Int) {

        for (i in newInfo.indices) {
            info[i] = newInfo[i]
        }
    }

    override fun toString(): String {
        return "MagicCube(order=$order, " +
                "topInfo=${sideStr(topInfo)}, " +
                "bottomInfo=${sideStr(bottomInfo)}, " +
                "leftInfo=${sideStr(leftInfo)}, " +
                "rightInfo=${sideStr(rightInfo)}, " +
                "frontInfo=${sideStr(frontInfo)}, " +
                "backInfo=${sideStr(backInfo)})"
    }

    fun sideStr(sideInfo: Array<Int>): String {
        var str = "\n"
        for (i in sideInfo.indices) {
            str += "${sideInfo[i]} "
            if (i % order == order - 1) {
                str += "\n"
            }
        }
        return str
    }


}