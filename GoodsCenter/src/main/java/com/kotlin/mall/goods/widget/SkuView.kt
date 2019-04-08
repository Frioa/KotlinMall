package com.kotlin.goods.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.eightbitlab.rxbus.Bus

import com.kotlin.goods.common.GoodsConstant
import com.kotlin.goods.data.protocol.GoodsSku
import com.kotlin.mall.goods.event.SkuChangedEvent
import com.kotlin.mall.goods.R
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.layout_sku_view.view.*
import java.util.HashSet

/*
    单个SKU
 */
class SkuView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : FrameLayout(context, attrs, defStyle) {
    private lateinit var mGoodsSku: GoodsSku
    private var mPostion:Int = 0
    init {
         View.inflate(context, R.layout.layout_sku_view, this)
    }

    /*
        动态设置SKU数据
     */
    fun setSkuData(goodsSku: GoodsSku) {
        mGoodsSku = goodsSku
        mSkuTitleTv.text = goodsSku.skuTitle

        //FlowLayout设置数据
        mSkuContentView.adapter = object :TagAdapter<String>(goodsSku.skuContent){
            override fun getView(parent: FlowLayout?, position: Int, t: String?): View {

                val view = LayoutInflater.from(context)
                        .inflate(R.layout.layout_sku_item,parent,false) as TextView
                Log.d("FlowLayout设置数据()",t)
                view.text = t
                return view
            }


        }



        mSkuContentView.adapter.setSelectedList(0)

        mSkuContentView.setOnTagClickListener { view1, position, parent ->


            if(mPostion==position){
                mSkuContentView.adapter.setSelectedList(position)
                //Log.d("SkuView: mPostion",""+mPostion+" "+position)
                false
            }else{
                mPostion = position
                Bus.send(SkuChangedEvent())
                true
            }

        }
    }

    /*
        获取选中的SKU
     */
    fun getSkuInfo(): String {
        Log.d("getSkuInfo() 获取选中的SKU:",mSkuTitleTv.text.toString() + GoodsConstant.SKU_SEPARATOR +
                mGoodsSku.skuContent[mSkuContentView.selectedList.first()])
        return mSkuTitleTv.text.toString() + GoodsConstant.SKU_SEPARATOR +
                mGoodsSku.skuContent[mSkuContentView.selectedList.first()]  // first() 选中第一个
    }
}
