package com.kotlin.arithmetic.sort.list.impl

import java.util.*


class MyArrayList (private val mSize : Int = 20, private val position :Boolean = true)  {

    val array = ArrayList<Int>()

    // 数组中最大值与最小值
    var mMax = Int.MIN_VALUE
    var mMin = Int.MAX_VALUE

     fun generateOrderArrayList(): MyArrayList {
         mMax = mSize - 1
         mMin = 0
        for (i in 0 until mSize) {
            if (position) {
                array.add(i)
            } else {
                array.add(mSize - i )
            }
        }
         return this
    }

    fun generateRandomArrayList(left: Int, right: Int): MyArrayList {
        for (i in 0 until mSize) {
            val temp = Random().nextInt(right - left + 1) + left
            if (temp > mMax) mMax = temp
            if (temp < mMin) mMin = temp
            array.add(temp)
        }
        return this
    }

    fun generateAlmostArrayList(chaos : Int): MyArrayList {
        assert(chaos >= 0)
        if (chaos == 0) return this
        if (array.size == 0) generateOrderArrayList()
        for (i in 1..chaos) {
            val random1 = Random().nextInt(mSize)
            val random2 = Random().nextInt(mSize)
            Collections.swap(array, random1, random2)
        }
        return this
    }



    fun getInstance() :MyArrayList{
        return this
    }

}


