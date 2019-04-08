package com.kotlin.arithmetic.sort.ext


fun <T>ArrayList<T>.print(step : Int) {
    print("$step 次循环")
    this.forEach {
        print("$it ")
    }
    println()
}

fun <T>ArrayList<T>.print() {
    this.forEach {
        print("$it ")
    }
    println()
}

fun Int.compareTo(i: Int, positive:Boolean): Int {
    if (this > i) return if (!positive) -1 else 1
    if (this < i) return if (!positive) 1 else -1
    return 0
}


