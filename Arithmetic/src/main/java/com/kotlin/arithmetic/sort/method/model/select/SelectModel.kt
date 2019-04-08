package com.kotlin.arithmetic.sort.method.model.select

import com.kotlin.arithmetic.sort.ext.compareTo
import com.kotlin.arithmetic.sort.ext.print
import com.kotlin.arithmetic.sort.method.model.AbsSortModel
import com.kotlin.arithmetic.ui.activity.UpdateView
import java.util.*

class SelectModel constructor(view: UpdateView): AbsSortModel(view)  {
    override var TAG = "选择排序"

    override fun logic(mArray: ArrayList<Int>) {
        for (i in 0 until mArray.size - 1) { // 第一次遍历，最大的数到最后
            var element = i
            for (j in i + 1 until mArray.size ) {// 初始第一个元素与后面的元素比较
                isCycNum()
                if ( mArray[element].compareTo(mArray[j], positive) > 0) { // 默认正序
                    element = j
                }

            }
            isCycNum()
            Collections.swap(mArray, i, element)

            if (isPrint)  mArray.print( i + 1)
        }
    }

}
