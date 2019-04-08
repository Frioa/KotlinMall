package com.kotlin.arithmetic.sort.method.model

import android.util.Log
import com.kotlin.arithmetic.sort.ext.print
import com.kotlin.arithmetic.ui.activity.UpdateView
import java.lang.StringBuilder
import java.util.*
import java.util.Collections.swap
import kotlin.collections.ArrayList

abstract class AbsSortModel constructor(view: UpdateView)  {
    var positive = true  // 默认 正序排序

    open var TAG = "AbsSortModel"// 名字

    var cycleNum = 0 // 循环次数
    var keyNum = 0  // 关键位置比较次数
    var isCyc = true
    var sleepTime = 350L // view 动画的时间

    var maxArray = Int.MIN_VALUE
    var minArray = Int.MAX_VALUE

    var startTime: Long = 0
    var endTime: Long = 0


    var isArrayList = true
    var isNative = false // 默认不适用 Native 方法
    var isPrint = false // 打印排序 Log
    var isDraw = true // 是否柱状图展示
    var isStart = false // 是否正在排序

    // 排序的数据结构
    lateinit var mArray: ArrayList<Int>
    lateinit var mLinkList: LinkedList<Int>
    var mView: UpdateView = view

/*    // 队列声明

    data class StepRes(val index:Int, val value:Int)
    val mQueue = LinkedList<StepRes>()*/

    abstract fun logic(mArray: ArrayList<Int>)

     open fun startSort(str: String) {
         if (isPrint) println("开始${TAG}了")
         isStart = true // 开始排序

         startTime = System.nanoTime()   //获取开始时间
         logic(mArray)
         endTime = System.nanoTime() //获取结束时间

         isStart = false// 结束排序
         if (isPrint) println("结束${TAG}了")
    }

    // 遍历数组情况
    fun printmList() {
        if (!isPrint) return
        print("$TAG 结果:")
        for ((i,value) in mArray.withIndex()) {
            print("$value ")
        }
        println()
    }

    // 返回多少秒
    fun getTime():StringBuilder {
       return getTime(endTime - startTime)
    }

    // 返回多少秒
    fun getTime(tTime: Long):StringBuilder {
        val time = StringBuilder((tTime).toString())

        while (time.length <= 9) {
            time.insert(0, "0")
        }
        val index = time.length - 9
        time.insert(index, ".")

        if (time.length == 10)  time.insert(0, "0")

        return time
    }

    // 交换函数
    fun mySwap(i: Int, j: Int, boolean: Boolean = false) {

        mView.keyView(i, boolean) // 绘制颜色
        mView.keyView(j, boolean)
        swap(mArray, i, j)

        if (isDraw) {
            showChat(i, 0)
            if (i != j) showChat(j, sleepTime)
        }
    }

    // key 值更新

    fun keyView(i : Int, boolean: Boolean = false, time: Long = sleepTime) {
        mView.keyView(i, boolean)
        Thread.sleep(time)
    }

    fun showChat(i: Int, time: Long = 0) {
        mView.updateView(i)
        Thread.sleep(time)
    }

    fun isCycNum(){
        if (isCyc) cycleNum++
    }

    fun isKeyNum() {
        if (isCyc) keyNum++
    }

    fun isPrint() {
        if (isPrint) mArray.print()

    }

    fun isPrint(i :Int) {
        if (isPrint) mArray.print(i)
    }


}
