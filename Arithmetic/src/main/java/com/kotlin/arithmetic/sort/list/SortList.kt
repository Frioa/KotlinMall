package com.kotlin.arithmetic.sort.list

interface SortList {
    fun print()
    fun size():Int
    fun swapIndex(index1: Int, index2:Int)
    fun set(var1: Int, var2: Int):Int
    fun withIndex(): Iterable<IndexedValue<Int>>
    fun get(index:Int):Int
    fun swap(index1: Int, index2: Int)
}