package com.kotlin.base.ui.activity

import android.app.AlertDialog
import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import com.kotlin.base.common.BaseApplication
import com.kotlin.base.injection.component.ActivityComponent
import com.kotlin.base.injection.component.DaggerActivityComponent
import com.kotlin.base.injection.module.ActivityModule
import com.kotlin.base.injection.module.LifecycleProviderModule
import com.kotlin.base.persenter.BasePresenter
import com.kotlin.base.persenter.view.BaseView
import com.kotlin.base.widgets.ProgressLoading
import org.jetbrains.anko.toast
import javax.inject.Inject

abstract class BaseTakePhotoActivity<T : BasePresenter<*>>:BaseActivity(), BaseView {

    //使用 Inject 标注的属性
    @Inject
    lateinit var mPresenter:T
    lateinit var activityComponent: ActivityComponent
    lateinit var mLoadingDialog: ProgressLoading
    val PERMISSION_CAMERA:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initActivityInjection()
        injectComponent()

        mLoadingDialog = ProgressLoading.create(this)
        ARouter.getInstance().inject(this)  //ARouter 注册

    }
    abstract fun injectComponent()

    private fun initActivityInjection() {
        activityComponent = DaggerActivityComponent.builder().appComponent((application as BaseApplication).appComponent).activityModule(ActivityModule(this))
                .lifecycleProviderModule(LifecycleProviderModule(this))
                .build()
    }

    override fun showLoading() = mLoadingDialog.showLoading()


    override fun hideLoading() = mLoadingDialog.hideLoading()


    override fun onError(text:String) {
        toast(text)
    }
}