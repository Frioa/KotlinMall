package com.kotlin.arithmetic.sort.method.model.quick.impl

import com.kotlin.arithmetic.sort.ext.compareTo
import com.kotlin.arithmetic.sort.method.model.AbsSortModel
import com.kotlin.arithmetic.ui.activity.UpdateView
import java.util.*
import java.util.Collections.swap

class QuickThreeModel constructor(view: UpdateView): AbsSortModel(view) {
    override var TAG = "快速排序(三路)"

    override fun logic(mArray: ArrayList<Int>) {
        _partition(mArray, 0, mArray.size - 1) // [] 进行排序
    }

    /*
        返回值：返回值 p 是数组中正确的位置，p 前面的元素小于p， p后面的元素大于p
     */
    private fun _partition(mArray: ArrayList<Int>, left: Int, right: Int) {
        if (left >= right) return

        mySwap(left, Random().nextInt(right - left + 1) + left)
        val key = mArray[left]


        // mArray[l+1...i) < key  arr(j...r] > key
        var lt = left
        var gt = right + 1
        var i = left + 1

        while( i < gt ) {
            when {
                mArray[i].compareTo(key, positive) < 0 -> mySwap( i++, lt++ + 1)
                mArray[i].compareTo(key, positive) > 0 -> mySwap( i, gt-- - 1)
                else -> i++
            }
        }
        mySwap( lt, left)
        _partition(mArray, left, lt - 1)
        _partition(mArray, gt, right)

    }

}
