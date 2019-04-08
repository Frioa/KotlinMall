package com.kotlin.arithmetic.sort.method.model.insert

import com.kotlin.arithmetic.sort.ext.compareTo
import com.kotlin.arithmetic.sort.method.model.AbsSortModel
import com.kotlin.arithmetic.ui.activity.UpdateView

class InsertModel constructor(view: UpdateView): AbsSortModel(view) {
    override var TAG = "插入排序"

    companion object {
        fun logic_(mArray: ArrayList<Int>, left: Int, right: Int, positive: Boolean, model: AbsSortModel) {
            var temp = 0 // 插入排序改进
            for (i in left + 1 until right + 1) {

                temp = mArray[i] // 插入排序优化，临时空间
                var j = i
                while( j > 0 &&  temp.compareTo(mArray[j-1], positive) < 0 ){
                    mArray[j] = mArray[j-1]
                    // 改变颜色
                    model.showChat(j)
                    j--
                }

                if (j != i)  {
                    mArray[j] = temp
                    model.showChat(j)
                }  // 小优化

            }
        }
    }
    override fun logic(mArray: ArrayList<Int>) {
        var temp: Int // 插入排序改进
        for (i in 1 until mArray.size) {

            temp = mArray[i] // 插入排序优化，临时空间
            var j = i
            while( j > 0 &&  temp.compareTo(mArray[j-1], positive) < 0 ){
                isCycNum()
                mArray[j] = mArray[j-1]
                showChat(j)
                j--
            }

            if (j != i) { // 小优化
                isCycNum()
                mArray[j] = temp
                showChat(j)
            }
            // 找到插入的位置，插入元素
            isPrint(i)
        }

    }

}
