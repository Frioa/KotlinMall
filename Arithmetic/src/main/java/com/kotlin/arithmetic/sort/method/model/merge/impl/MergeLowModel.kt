package com.kotlin.arithmetic.sort.method.model.merge.impl

import com.kotlin.arithmetic.sort.ext.compareTo
import com.kotlin.arithmetic.sort.method.model.AbsSortModel
import com.kotlin.arithmetic.ui.activity.UpdateView
import kotlin.math.min

/*
    非递归
    特点：没有通过索引直接获取元素，进而可以作用在链表中实现 O（nLogn）排序
    与自顶向上比较：（自低向上） 慢
 */
class MergeLowModel constructor(view: UpdateView): AbsSortModel(view) {
    override var TAG = "归并排序（自低向上）"

    override fun logic(mArray: ArrayList<Int>) {
        var sz = 1
        while( sz<=mArray.size ) {   //归并元素的个数
            var j = 0
            while( j + sz < mArray.size ) {// 防止越界
                // 对 arr[i...i+sz-1] 与 arr[i+sz....i+2*sz-1] 进行归并
                if (mArray[j + sz - 1].compareTo(mArray[j + sz], positive)>0) { // 优化
                    _merge(mArray, j, j + sz - 1, min(j + sz + sz -1, mArray.size - 1)) // min 处理最后元素不够的情况
                }

                j+= sz + sz // 每次平移两个 size
            }
            sz += sz
        }
    }

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
