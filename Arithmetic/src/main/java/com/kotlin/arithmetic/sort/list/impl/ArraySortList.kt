package com.kotlin.arithmetic.sort.list.impl

import com.kotlin.arithmetic.sort.list.SortList
import java.util.Collections.swap

class ArraySortList constructor(private val array: ArrayList<Int>) : SortList {


    override fun swapIndex(index1: Int, index2: Int) {
        val temp = array[index1]
        array[index1] = array[index2]
        array[index2] = temp
    }


    override fun print() {
        println("ArrayList print():")
        array.forEach {
            print("$it ")
        }
    }

    override fun size(): Int {
        return this.array.size
    }

    override fun set(var1: Int, var2: Int):Int {
        return array.set(var1, var2)
    }

    override fun withIndex(): Iterable<IndexedValue<Int>> {
        return array.withIndex()
    }
    override fun get(index: Int):Int {
        return array[index]
    }
    override fun swap(index1: Int, index2: Int){
        swap(array, index1, index2)
    }
}