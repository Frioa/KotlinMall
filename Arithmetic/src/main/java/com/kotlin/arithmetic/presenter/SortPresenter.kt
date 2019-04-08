package com.kotlin.arithmetic.presenter

import com.kotlin.arithmetic.presenter.view.SortView
import com.kotlin.base.ext.execute
import com.kotlin.base.persenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger


import javax.inject.Inject


class SortPresenter @Inject constructor():BasePresenter<SortView>() {

    fun initArray(runnable: Runnable) {
        mView.showLoading()
        execute(runnable)
    }

    fun startSort(runnable: Runnable) {
        execute(runnable)
    }

    fun execute(runnable: Runnable) {
        THREAD_POOL_EXECUTOR.execute(runnable)
    }

//    @Inject
//    lateinit var cartService:CartService

  /*  fun getCategory(){
        *//*if (!checkNetWork()){
            return
        }*//*

        mView.showLoading() //显示加载
        cartService.getCartList()
                .execute(object :BaseSubscriber<MutableList<CartGoods>?>(mView){//execute 扩展方法，可以服用方法
                    override fun onNext(t: MutableList<CartGoods>?) {     // 重写 noNext 方法
                        super.onNext(t)
                        mView.onGetCartListResult(t)

                    }
                },lifecycleProvider)
    }*/

    // 线程池
    companion object {
        val CPU_COUNT = Runtime.getRuntime().availableProcessors()
        val CORE_POOL_SIZE = CPU_COUNT + 1
        val MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1
        val KEEP_ALIVE = 10L
        val sTheadFactory = object: ThreadFactory{
            private val mCount = AtomicInteger(1)
            override fun newThread(r: Runnable): Thread? {
                return object :Thread(r, "SortActivty# ${mCount.getAndIncrement()}"){}
            }
        }
        val THREAD_POOL_EXECUTOR = ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAXIMUM_POOL_SIZE,
                KEEP_ALIVE,
                TimeUnit.SECONDS,
                object :LinkedBlockingDeque<Runnable>(){},
                sTheadFactory
        )
    }

}