package com.kotlin.arithmetic.sort.method.model.bubbing


import android.util.Log
import com.kotlin.arithmetic.sort.ext.compareTo
import com.kotlin.arithmetic.sort.method.model.AbsSortModel
import com.kotlin.arithmetic.ui.activity.UpdateView
import java.util.*

class BubbingModel constructor(view: UpdateView) : AbsSortModel(view) {


    override var TAG = "冒泡排序"

    override fun logic(mArray: ArrayList<Int>) {

        for (i in 0 until mArray.size - 1) { // 第一次遍历，最大的数到最后
            var flash = true // 冒泡排序优化

            for (j in 1 until mArray.size - i) {// 初始第一个元素与后面的元素比较

                if ( mArray[j].compareTo(mArray[j-1], positive) < 0) { // 默认顺序
                    isCycNum()
                    mySwap(j, j - 1)
//                    Log.d(TAG, "name = ${Thread.currentThread().name} arr[$j]=${mArray[j]}  arr[${j-1}]=${mArray[j-1]} ")
                    flash = false
                }

            }
            if (flash) return
            isPrint()

        }
    }



}




