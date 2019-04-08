package com.kotlin.arithmetic.sort.list.impl

import java.util.*


class MyLinkedList (private val mSize : Int = 20, private val position :Boolean = true)  {

    private val array = LinkedList<Int>()

     fun generateOrderArrayList(): MyLinkedList {
        for (i in 0 until mSize) {
            if (position) {
                array.add(i)
            } else {
                array.add(mSize - i + 1)
            }
        }
         return this
    }

    fun generateAlmostArrayList(chaos : Int): MyLinkedList {
        if (array.size == 0) generateOrderArrayList()
        for (i in 1..chaos) {
            val random1 = Random().nextInt(mSize) + 1
            val random2 = Random().nextInt(mSize) + 1
            Collections.swap(array, random1, random2)
        }
        return this
    }

    fun getInstance() :LinkedList<Int>{
        return array
    }
}


