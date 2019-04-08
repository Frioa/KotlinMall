package com.kotlin.base.persenter.view

interface BaseView {
    fun showLoading() //显示加载
    fun hideLoading() //隐藏加载
    fun onError(text:String)     //错误处理
}