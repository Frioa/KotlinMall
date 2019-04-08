package com.kotlin.arithmetic.sort.method.builder

import com.kotlin.arithmetic.sort.list.impl.MyArrayList
import com.kotlin.arithmetic.sort.method.model.AbsSortModel
import java.util.*
import kotlin.collections.ArrayList

abstract class AbsSortBuilder  {
    // 得到实例
    abstract fun builder(): AbsSortModel
    // 排序顺序
    abstract fun setPosition(position:Boolean): AbsSortBuilder
    // 数组
    abstract fun setList(mList: MyArrayList): AbsSortBuilder
    // 名字
    abstract fun setName(name: String) : AbsSortBuilder
    // 是否使用 native 方法
    abstract fun setNative(native: Boolean) : AbsSortBuilder
    // 是否打印 Log
    abstract fun setPrintLog(print: Boolean) : AbsSortBuilder
    // 是否打印 Cyc
    abstract fun setCyc(cyc: Boolean) : AbsSortBuilder
}