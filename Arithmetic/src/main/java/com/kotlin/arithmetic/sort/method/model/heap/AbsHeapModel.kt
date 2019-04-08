package com.kotlin.arithmetic.sort.method.model.heap

import com.kotlin.arithmetic.sort.ext.compareTo
import com.kotlin.arithmetic.sort.method.model.AbsSortModel
import com.kotlin.arithmetic.ui.activity.UpdateView
import java.util.Collections.swap

abstract class AbsHeapModel constructor(view: UpdateView) : AbsSortModel(view) , HeapMethod {
    var count = 0
    override var TAG = "堆排序"
            get() {
                return if (positive) "$field(最小堆)"
                else "$field(最大堆)"
            }

    override fun startSort(str: String) {
        _setPosition()
        super.startSort(str)
    }

    /*
        构建一个堆，比insert效率高
     */
    override fun heapify() {
        count = mArray.size
        if (count == 0) return

        for (i in (count - 1)/2  downTo  0) {
            shiftDown(i)
        }
    }

    override fun insert(item: Int) {
//        assert( mArray.size + 1 <= capacity) // 防止越界
        mArray.add(item)
        shiftUp(count++)
    }

    override fun extractMax() {
        if (count <= 0) return
        swap(mArray, 0, --count)
        shiftDown(0)
    }


    private fun shiftUp(item: Int) {   // k 位置元素尝试向上移动
        var k = item
        while (k > 0 &&  mArray[k/2].compareTo(mArray[k], positive) > 0) {
            swap(mArray, k/2, k)
            k/=2
        }
    }

    /*
        取出根节点的元素，然后将最后元素放到根节点
     */
    private fun shiftDown(index: Int) {
        var k = index
        while ( 2 * k + 1 < count) { // 有左孩子
            var j = 2 * k + 1
            if ( j + 1 < count && mArray[j+1].compareTo(mArray[j], positive) < 0) j++

            if (mArray[k].compareTo(mArray[j], positive) <= 0) break // 父节点大于子节点不交换

            swap( mArray , k, j)

            k = j
        }
    }

    protected fun _setPosition() {
        positive = positive.not()
    }

}
