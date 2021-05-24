package com.webeye.myapplication


fun main(args: Array<String>) {

    printMsg("Hello World")
    printMsgPrefix("hh")
    printMsgPrefix("hh", "oo")
    printMsgPrefix(sum(3, 4), "3+4=")
    printMsgPrefix(sum2(3, 4), "3+4=")

    infix fun Int.repeat(string: String) = string.repeat(this)
    printMsgPrefix(3 repeat "ha")

    val pair = "TK" to "FB"
    println(pair)

    infix fun String.onto(string: String) = string to this
    operator fun Person.plus(name: String) = this like Person(name)

    println("FB" onto "TK2")

    var personA = Person("Person A")
    personA like Person("Person B")
    println(personA.likePerson)
    personA + "lincoln"
    println(personA.likePerson)

    printArgs("dd", "cc", "ee", "ff", prefix = "fix")

    var nonNullStr = ""

    for (i in 1..4){
        print("$i")
    }
    print(" ")

    for(i in 0 until 4){
        print("$i")
    }
    print(" ")

    val x = 2
    if (x !in 6..10) {          // 2
        print("x is not in range from 6 to 10")
    }
}

fun printMsg(msg: String): Unit {
    println(msg)
}

fun printMsgPrefix(msg: Any, prefix: String = "Info"): Unit {
    println("[$prefix]:$msg")
}

fun sum(a: Int, b: Int): Int {
    return a + b
}

fun sum2(a: Int, b: Int): Int = a + b

fun printArgs(vararg args: String, prefix: String) {
    for (arg in args) {
        println("$prefix->$arg")
    }
}

fun passArgs(vararg args: String) {
    printArgs(*args, prefix = "hello")
}

open class Person(val name: String) {

    val likePerson = mutableListOf<Person>()
    infix fun like(p: Person) {
        likePerson.add(p)
    }

    override fun toString(): String {

        return "Person($name)"
    }

}

class BlackPerson(name: String) : Person(name) {
    operator fun iterator(): Iterator<String> {             // 1
        return ArrayList<String>().iterator()                          // 2
    }

}
