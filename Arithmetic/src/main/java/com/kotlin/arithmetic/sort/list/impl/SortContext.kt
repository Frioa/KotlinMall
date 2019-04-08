package com.kotlin.arithmetic.sort.list.impl

import com.kotlin.arithmetic.sort.list.SortList

class SortContext constructor(val sortList: SortList) {


    fun print() {
        sortList.print()
    }

    fun swapIndex(index1:Int, index2:Int) {
        sortList.swapIndex(index1, index2)
    }

    fun size():Int {
        return sortList.size()
    }

    operator fun set(i: Int, value: Int) {
        sortList.set(i, value)
    }

    fun withIndex(): Iterable<IndexedValue<Int>> {
        return sortList.withIndex()
    }

    operator fun get(j: Int) {
        sortList.get(j)
    }

    fun swap(index1: Int, index2: Int) {
        if (sortList.get(1) > sortList.get(2))
        sortList.swap(index1, index2)
    }



}