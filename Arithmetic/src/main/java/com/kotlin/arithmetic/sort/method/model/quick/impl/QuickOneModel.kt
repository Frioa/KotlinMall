package com.kotlin.arithmetic.sort.method.model.quick.impl

import com.kotlin.arithmetic.sort.ext.compareTo
import com.kotlin.arithmetic.sort.method.model.AbsSortModel
import com.kotlin.arithmetic.sort.method.model.insert.InsertModel.Companion.logic_
import com.kotlin.arithmetic.ui.activity.UpdateView
import java.util.*
import java.util.Collections.swap

class QuickOneModel constructor(view: UpdateView): AbsSortModel(view) {
    override var TAG = "快速排序"

    override fun logic(mArray: ArrayList<Int>) {
        _quickSort(mArray, 0, mArray.size - 1) // [] 进行排序
    }

    private fun _quickSort(mArray: ArrayList<Int>, left: Int, right: Int) {
        if (right - left <= 2) {
            logic_(mArray, left, right, positive, this)
            return
        }

        val p = _partition(mArray, left, right)
        _quickSort(mArray, left, p)
        _quickSort(mArray, p + 1, right)
    }

    /*
        返回值：返回值 p 是数组中正确的位置，p 前面的元素小于p， p后面的元素大于p
     */
    private fun _partition(mArray: ArrayList<Int>, left: Int, right: Int): Int {
        var temp = Random().nextInt(right - left + 1) + left
        while(temp == left) {
            temp = Random().nextInt(right - left + 1) + left
        }

        mySwap(left, temp, true)
        val key = mArray[left]
        keyView(left, true, time = sleepTime) // 更新视图

        var j = left

        // mArray[l+1...j] < key  arr[j+1..i] > key
        for (i in left + 1..right) {
            if (mArray[i].compareTo(key, positive) < 0) {
                mySwap( ++j, i)
            }
        }
        mySwap( j, left, true)

        return j
    }

}
