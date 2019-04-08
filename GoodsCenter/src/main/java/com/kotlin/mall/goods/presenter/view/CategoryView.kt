package com.kotlin.mall.goods.presenter.view

import com.kotlin.base.persenter.view.BaseView
import com.kotlin.goods.data.protocol.Category


interface CategoryView:BaseView {
    fun onGetCategoryResult(result:MutableList<Category>?)

}