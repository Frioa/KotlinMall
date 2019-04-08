package com.kotlin.arithmetic.sort.method.builder

import com.kotlin.arithmetic.sort.list.impl.MyArrayList
import com.kotlin.arithmetic.sort.method.model.AbsSortModel
import java.util.*
import kotlin.collections.ArrayList

open class BaseBuider constructor(val baseModel : AbsSortModel): AbsSortBuilder() {

    override fun setPosition(position: Boolean): BaseBuider {
        this.baseModel.positive = position
        return this
    }

    override fun setList(mList: MyArrayList): BaseBuider {
        // 深拷贝
        this.baseModel.mArray = ArrayList(mList.array)
        setMaxValue(mList.mMax, mList.mMin)
        return this
    }



    fun setMaxValue(max:Int, min:Int) {
        this.baseModel.maxArray = max
        this.baseModel.minArray = min
    }

    override fun setName(name: String): AbsSortBuilder {
        this.baseModel.TAG = name
        return this
    }

    override fun setNative(native: Boolean): AbsSortBuilder {
        this.baseModel.isNative = native
        return this
    }

    override fun setPrintLog(print: Boolean): AbsSortBuilder {
        this.baseModel.isPrint = print
        return this
    }

    override fun builder(): AbsSortModel {
        initArray()
        return this.baseModel
    }

    override fun setCyc(cyc: Boolean): AbsSortBuilder {
        this.baseModel.isCyc = cyc
        return this
    }


    private fun initArray() {
        if (baseModel.mArray == null) {// 初始化 ArrayList
            val mList = ArrayList<Int>(20)
            for (i in 20 downTo 1) {
                mList.add(i)
            }
            baseModel.mArray = mList
        }
    }


}