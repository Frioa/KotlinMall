package com.kotlin.arithmetic.sort.method.model.quick.impl

import com.kotlin.arithmetic.sort.ext.compareTo
import com.kotlin.arithmetic.sort.method.model.AbsSortModel
import com.kotlin.arithmetic.ui.activity.UpdateView
import java.util.*
import java.util.Collections.swap

class QuickTwoModel constructor(view: UpdateView): AbsSortModel(view) {
    override var TAG = "快速排序(双路)"

    override fun logic(mArray: ArrayList<Int>) {
        _quickSort(mArray, 0, mArray.size - 1) // [] 进行排序
    }

    private fun _quickSort(mArray: ArrayList<Int>, left: Int, right: Int) {
        if (left >= right) return

        val p = _partition(mArray, left, right)
        _quickSort(mArray, left, p)
        _quickSort(mArray, p + 1, right)
    }

    /*
        返回值：返回值 p 是数组中正确的位置，p 前面的元素小于p， p后面的元素大于p
     */
    private fun _partition(mArray: ArrayList<Int>, left: Int, right: Int): Int {

        mySwap(left, Random().nextInt(right - left + 1) + left)
        val key = mArray[left]

        // mArray[l+1...i) < key  arr(j...r] > key
        var i = left + 1
        var j = right

        while( true ) {
            while ( i <= right && mArray[i].compareTo(key, positive) < 0) i++
            while ( j >= left + 1 && mArray[j].compareTo(key, positive) > 0) j--
            if (i > j) break
            mySwap(i++, j--)
        }
        mySwap(left, j)

        return j
    }

}
