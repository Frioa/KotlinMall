package com.kotlin.arithmetic.sort.method.model.merge.impl

import com.kotlin.arithmetic.sort.ext.compareTo
import com.kotlin.arithmetic.sort.method.model.AbsSortModel
import com.kotlin.arithmetic.ui.activity.UpdateView

class MergeTopModel constructor(view: UpdateView): AbsSortModel(view) {
    override var TAG = "归并排序（自顶向下）"

    override fun logic(mArray: ArrayList<Int>) {
        _loginc(mArray, 0, mArray.size - 1)
    }
    // [left, right] 进行排序
    private fun _loginc(mArray: ArrayList<Int> ,left: Int, right: Int) {

//        if (right - left <= 15) {
//            InsertModel.logic_(mArray, left, right, positive)
//            return
//        }

        if (left >= right) {
            return
        }

        val mid = (left + right) / 2
        _loginc(mArray, left, mid)// 先归并
        _loginc(mArray, mid + 1, right)

        if (mArray[mid].compareTo(mArray[mid+1], positive) > 0)  // 优化
            _merge(mArray, left, mid, right)
    }

    // [left, right] 进行merge

    private fun _merge(mArray: ArrayList<Int>, left: Int, mid: Int ,right: Int) {
        val tArray = arrayOfNulls<Int>(right - left + 1)
        for (i in left..right) {
            tArray[i - left] = mArray[i]
        }

        var mLeft = left
        var mRight = mid + 1

        for (k in left..right) {

            when {
                mLeft > mid -> {
                    mArray[k] = tArray[mRight - left]!!
                    mRight++

                }
                mRight > right -> {
                    mArray[k] = tArray[mLeft - left]!!
                    mLeft++

                }
                tArray[mLeft - left]!!.compareTo(tArray[mRight - left]!!, positive) < 0 -> {
                    mArray[k] = tArray[mLeft - left]!!
                    mLeft++

                }
                else -> {
                    mArray[k] = tArray[mRight - left]!!
                    mRight++
                }
            }
            showChat(k)
            isPrint()
        }
    }

}
