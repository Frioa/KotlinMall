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
import com.kotlin.user.presenter.ResetPwdPresenter
import com.kotlin.user.presenter.view.ResetPwdView
import kotlinx.android.synthetic.main.activity_reset_pwd.*
import org.jetbrains.anko.clearTop
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.singleTop
import org.jetbrains.anko.toast
import javax.inject.Inject
/*
    忘记密码
 */

class ResetPwdActivity @Inject constructor(): BaseMvpActivity<ResetPwdPresenter>(), ResetPwdView, View.OnClickListener {



    private var preeTime:Long = 0

    override fun injectComponent() {// Dagger2 的注册
        DaggerUserComponent.builder().activityComponent(activityComponent).userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_pwd)

        initView()

    }
    /*
        初始化视图
     */
    private fun initView() {
        // 监听4个输入框，判断 注册 按钮是否可用
        mConfirmBtn.enable(mPwdEt,{isBtnEnable()})
        mConfirmBtn.enable(mPwdConfirmEt,{isBtnEnable()})

        mConfirmBtn.onClick(this)
    }



    override fun onClick(view: View) {//传统实现点击事件的方法
        when(view.id){
            R.id.mConfirmBtn ->{
                if (mPwdEt.text.toString() != mPwdConfirmEt.text.toString()){
                    toast("密码不一致")
                    return
                }
                mPresenter.resetPwd(intent.getStringExtra("mobile"),mPwdConfirmEt.text.toString())
                toast("发送修改的密码")
            }
        }
    }

    // 判断4个输入框是否不为空
    private fun isBtnEnable():Boolean{
        return mPwdEt.text.isNotEmpty() &&
                mPwdConfirmEt.text.isNotEmpty()
    }

    override fun onResetPwdResult(result: String) {
        toast(result)
        startActivity(intentFor<LoginActivity>().singleTop().clearTop())
    }
}
