package com.kotlin.arithmetic.sort.method.model.heap.impl

import com.kotlin.arithmetic.sort.method.model.heap.AbsHeapModel
import com.kotlin.arithmetic.ui.activity.UpdateView

class HeapModul constructor(view: UpdateView) : AbsHeapModel(view) {
    var start = 0L
    var end = 0L

    override fun logic(mArray: ArrayList<Int>) {
        // 构建一个堆
        start = System.nanoTime()
        heapify()
        end = System.nanoTime()
        if (isPrint) println( "构建堆的时间: ${getTime(end - start)} ")   // 构建堆的时间
        // 堆排序
        while (count > 0) extractMax()

    }

}