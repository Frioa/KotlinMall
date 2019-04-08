package com.kotlin.user.ui.activity

import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.view.View
import com.kotlin.base.common.AppManager
import com.kotlin.base.ext.enable
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.activity.BaseMvpActivity
import com.kotlin.base.widgets.VerifyButton
import com.kotlin.user.R
import com.kotlin.user.injection.component.DaggerUserComponent
import com.kotlin.user.injection.module.UserModule
import com.kotlin.user.presenter.ForgetPwdPresenter
import com.kotlin.user.presenter.view.ForgetPwdView
import kotlinx.android.synthetic.main.activity_forget_pwd.*
import org.jetbrains.anko.startActivity

import org.jetbrains.anko.toast
import javax.inject.Inject
/*
    忘记密码
 */

class ForgetActivity @Inject constructor(): BaseMvpActivity<ForgetPwdPresenter>(), ForgetPwdView, View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_pwd)

        initView()

    }
    override fun injectComponent() {// Dagger2 的注册
        DaggerUserComponent.builder().activityComponent(activityComponent).userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }
    /*
        初始化视图
     */
    private fun initView() {
        // 监听4个输入框，判断 注册 按钮是否可用
        mNextBtn.enable(mMobileEt,{isBtnEnable()})
        mNextBtn.enable(mVerifyCodeEt,{isBtnEnable()})

        mNextBtn.onClick(this)
        mVerifyCodeBtn.onClick(this)
    }



    override fun onClick(view: View) {//传统实现点击事件的方法
        when(view.id){
            R.id.mVerifyCodeBtn ->{
                mVerifyCodeBtn.requestSendVerifyNumber()    //Button 改变
                toast("发送验证码成功")
                startActivity<ResetPwdActivity>()
            }

            R.id.mNextBtn ->{   //使用扩展方法
                mPresenter.forgetPwd(mMobileEt.text.toString(),mVerifyCodeEt.text.toString())
            }
        }
    }

    // 判断4个输入框是否不为空
    private fun isBtnEnable():Boolean{
        return mMobileEt.text.isNotEmpty() &&
                mVerifyCodeEt.text.isNotEmpty()
    }

    override fun onForgetPwdResult(result: String) {
        toast(result)
        startActivity<ResetPwdActivity>("mobile" to mMobileEt.text.toString())
    }
}
