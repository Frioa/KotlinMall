package com.kotlin.mall.goods.presenter

import com.kotlin.base.ext.execute
import com.kotlin.base.persenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.goods.data.protocol.Category

import com.kotlin.mall.goods.presenter.view.CategoryView
import com.kotlin.mall.goods.service.CategoryService
import javax.inject.Inject


class CategoryPresenter @Inject constructor():BasePresenter<CategoryView>() {
    @Inject
    lateinit var categoryService:CategoryService

    fun getCategory(parentId:Int){
        /*
            业务逻辑
        */
         //初始化
        if (!checkNetWork()){
            return
        }

        mView.showLoading() //显示加载
        categoryService.getCategory(parentId)
                .execute(object :BaseSubscriber<MutableList<Category>?>(mView){//execute 扩展方法，可以服用方法
                    override fun onNext(t: MutableList<Category>?) {     // 重写 noNext 方法
                        super.onNext(t)
                        mView.onGetCategoryResult(t)

                    }
                },lifecycleProvider)
    }

}