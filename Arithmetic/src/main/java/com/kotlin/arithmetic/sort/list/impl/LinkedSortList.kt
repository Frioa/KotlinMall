package com.kotlin.arithmetic.sort.list.impl

import com.kotlin.arithmetic.sort.list.SortList
import java.util.*
import java.util.Collections.swap

class LinkedSortList constructor(private val link: LinkedList<Int>): SortList {
    override fun swapIndex(index1: Int, index2: Int) {
        val temp = link[index1]
        link[index1] = link[index2]
        link[index2] = temp
    }

    override fun print() {
        println("LinkedList print():")
        link.forEach {
            print("$it ")
        }
    }

    override fun size():Int {
        return this.link.size
    }

    override fun set(var1: Int, var2: Int):Int {
        return link.set(var1, var2)
    }

    override fun withIndex(): Iterable<IndexedValue<Int>> {
        return link.withIndex()
    }

    override fun get(index: Int):Int {
        return link[index]
    }
    override fun swap(index1: Int, index2: Int){
        swap(link, index1, index2)
    }
}
