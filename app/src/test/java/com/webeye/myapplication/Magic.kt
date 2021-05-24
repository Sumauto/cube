package com.webeye.myapplication

fun main() {
    val magicCube = MagicCube(2)

    val red = SideCreator(1, "red")
    val blue = SideCreator(2, "blue")
    val yellow = SideCreator(3, "yellow")
    val white = SideCreator(4, "white")
    val orange = SideCreator(5, "orange")
    val pink = SideCreator(6, "pink")

    magicCube.setSideInfo(Side.TOP, red.id, red.id, red.id, red.id)
    magicCube.setSideInfo(Side.BOTTOM, blue.id, blue.id, blue.id, blue.id)
    magicCube.setSideInfo(Side.BACK, yellow.id, yellow.id, yellow.id, yellow.id)
    magicCube.setSideInfo(Side.FRONT, white.id, white.id, white.id, white.id)
    magicCube.setSideInfo(Side.LEFT, orange.id, orange.id, orange.id, orange.id)
    magicCube.setSideInfo(Side.RIGHT, pink.id, pink.id, pink.id, pink.id)
    println(magicCube)
}