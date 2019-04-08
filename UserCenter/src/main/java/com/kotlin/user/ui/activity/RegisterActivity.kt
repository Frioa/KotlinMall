package com.kotlin.user.ui.activity

import android.os.Bundle
import android.view.View
import com.kotlin.base.common.AppManager
import com.kotlin.base.ext.enable
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.activity.BaseMvpActivity
import com.kotlin.base.widgets.VerifyButton
import com.kotlin.user.R
import com.kotlin.user.injection.component.DaggerUserComponent
import com.kotlin.user.injection.module.UserModule
import com.kotlin.user.presenter.RegisterPresenter
import com.kotlin.user.presenter.view.RegisterView
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.toast
import javax.inject.Inject
/*
    注册界面功能
 */

class RegisterActivity @Inject constructor(): BaseMvpActivity<RegisterPresenter>(), RegisterView, View.OnClickListener {


    override fun injectComponent() {// Dagger2 的注册
        DaggerUserComponent.builder().activityComponent(activityComponent).userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }
    /*
        注册回调
     */
    override fun onRegisterResult(result: String) {        //接口回调
        toast(result)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        initView()
        //实例化
        //mPresenter = RegisterPresenter()  通过 Dagger2 @Inject 实例化

//        mRegisterBtn.setOnClickListener {//原方法
//            mPresenter.register(mMoblieEt.text.toString(),mVerifyCodeEt.text.toString(),mPwdEt.text.toString())
//        }



//        mGetVerifyCodeBtn.setOnVerifyBtnClick(object :VerifyButton.OnVerifyBtnClick{
//            override fun onClick() {
//                toast("获取验证码")
//            }
//        })
//        mGetVerifyCodeBtn.onClick {
//            mGetVerifyCodeBtn.requestSendVerifyNumber()
//        }
    }
    /*
        初始化视图
     */
    private fun initView() {
        // 监听4个输入框，判断 注册 按钮是否可用
        mRegisterBtn.enable(mMobileEt, {isBtnEnable()})
        mRegisterBtn.enable(mVerifyCodeEt, {isBtnEnable()})
        mRegisterBtn.enable(mPwdEt, {isBtnEnable()})
        mRegisterBtn.enable(mPwdConfirmEt, {isBtnEnable()})

        mVerifyCodeBtn.onClick(this)
        mRegisterBtn.onClick(this)
    }



    override fun onClick(view: View) {//传统实现点击事件的方法
        when(view.id){
            R.id.mVerifyCodeBtn ->{
                mVerifyCodeBtn.requestSendVerifyNumber()    //Button 改变
                toast("发送验证码成功")
            }

            R.id.mRegisterBtn ->{   //使用扩展方法
                mPresenter.register(mMobileEt.text.toString(),mVerifyCodeEt.text.toString(),mPwdEt.text.toString())
            }
        }
    }

    // 判断4个输入框是否不为空
    private fun isBtnEnable():Boolean{
        return mMobileEt.text.isNotEmpty() &&
                mVerifyCodeEt.text.isNotEmpty() &&
                mPwdEt.text.isNotEmpty() &&
                mPwdConfirmEt.text.isNotEmpty()
    }
}
