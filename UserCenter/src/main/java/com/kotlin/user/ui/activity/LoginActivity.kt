package com.kotlin.user.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.kotlin.base.common.BaseConstant
import com.kotlin.base.ext.enable
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.activity.BaseMvpActivity
import com.kotlin.base.utils.AppPrefsUtils
import com.kotlin.provider.PushProvider
import com.kotlin.provider.router.RouterPath
import com.kotlin.provider.router.RouterPath.UserCenter.Companion.PATH_LOGIN
import com.kotlin.user.R
import com.kotlin.user.data.protocol.UserInfo
import com.kotlin.user.injection.component.DaggerUserComponent
import com.kotlin.user.injection.module.UserModule
import com.kotlin.user.presenter.LoginPresenter
import com.kotlin.user.presenter.view.LoginView
import com.kotlin.user.utils.UserPrefsUtils
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import javax.inject.Inject
/*
    登陆界面
 */
@Route(path = PATH_LOGIN)
class LoginActivity @Inject constructor(): BaseMvpActivity<LoginPresenter>(), LoginView, View.OnClickListener {

    @Autowired(name = RouterPath.MessageCenter.PATH_MESSAGE_PUSH)
    @JvmField
    var mPushProvider: PushProvider? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initView()

    }
    /*
        初始化视图
     */
    private fun initView() {
        // 监听4个输入框，判断 注册 按钮是否可用
        mLoginBtn.enable(mMobileEt) {isBtnEnable()}
        mLoginBtn.enable(mPwdEt) {isBtnEnable()}

        mHeaderBar.getRightView().onClick(this)
        mLoginBtn.onClick(this)
        mForgetPwdTv.onClick(this)


    }

    override fun onClick(view: View) {//传统实现点击事件的方法
        when(view.id){
            R.id.mRightTv -> {
                startActivity<RegisterActivity>()
            }
            R.id.mLoginBtn -> {   //使用扩展方法

                mPresenter.login(mMobileEt.text.toString(),mPwdEt.text.toString(), AppPrefsUtils.getString(BaseConstant.KEY_SP_JPUSH))
            }
            R.id.mForgetPwdTv -> {
                startActivity<ForgetActivity>()
            }
        }
    }

    override fun injectComponent() {// Dagger2 的注册
        DaggerUserComponent.builder().activityComponent(activityComponent).userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    // 判断4个输入框是否不为空
    private fun isBtnEnable():Boolean{
        return mMobileEt.text.isNotEmpty() &&
                mPwdEt.text.isNotEmpty()
    }
    override fun onLoginResult(result: UserInfo) {
        //接口回调
        toast("登陆成功")
        UserPrefsUtils.putUserInfo(result)
        //startActivity<UserInfoActivity>()
        finish()
    }

}
