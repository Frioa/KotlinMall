package com.kotlin.paysdk.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.alipay.sdk.app.EnvUtils
import com.alipay.sdk.app.PayTask
import com.kotlin.base.ext.execute
import com.kotlin.base.ext.onClick
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.base.ui.activity.BaseActivity
import com.kotlin.base.ui.activity.BaseMvpActivity
import com.kotlin.base.utils.YuanFenConverter
import com.kotlin.paysdk.R
import com.kotlin.paysdk.injection.component.DaggerPayComponent
import com.kotlin.paysdk.injection.module.PayModule
import com.kotlin.paysdk.presenter.PayPresenter
import com.kotlin.paysdk.presenter.view.PayView
import com.kotlin.paysdk.utils.OrderInfoUtil2_0
import com.kotlin.paysdk.utils.PayResult
import com.kotlin.provider.common.ProviderConstant
import com.kotlin.provider.router.RouterPath
import kotlinx.android.synthetic.main.activity_cash_register.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import java.security.Provider

// 测试账号：bcpvri4298@sandbox.com
@Route(path = RouterPath.PaySDK.PATH_PAY)
class CashRegisterActivity:BaseMvpActivity<PayPresenter>(), PayView, View.OnClickListener {

    val SDK_PAY_FLAG = 111
    /** 支付宝支付业务：入参app_id  */
    val APPID = "2016092500596506"
    /** 支付宝账户登录授权业务：入参pid值  */
//        val PID = ""
//        /** 支付宝账户登录授权业务：入参target_id值  */
//        val TARGET_ID = ""
    /** 商户私钥，pkcs8格式 */
    /** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
    /** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
    /** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
    /** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
    /** 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1 */
    val RSA2_PRIVATE = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDQ+m098MmdGYzOQSUg0NBqNtQs9SF4Ks/CkcWVX1FkRSyl2jZ5UqCE8NdK/v2RSiDTKrX8a4tX5J2Mf8Iq6wEr9rOo3fOklLx26gBj+sIXp5py+qq0Crfsru/D6xX39l2ju3NI+1kj0GtzcyVPkk7t5jK/HlPlMNuL0dYIogRrS89qhsBkqPw7CaHDQ94uf91+XjnVC3kxZur0zXEHF8v0YbvpiSvcYmiVxdsivLvf6w523Ib0yEZMW7oo5PaGktfGY+qZbuzrs5RF31b3gomK4C+3Q1+LDufBUnIpX00oQCOHfdl0Fh7ak+BZcv7Cowv50D7OB8VSE/W0rr1LLGQbAgMBAAECggEBAKCzgyA3swKhLDtLs1551VtoUF9GHffHjD7GVFealf9yhnP/yxYe6RtyDU05qwp4I6ffV1UF6oCraFArysewMhV5wAyiqYKtcgRLzAMWSP1hwI6cnnqXJ3rVx8E0XY9yH+4R7wR3bMPyuT5tlIqNKy8cb3eyvbcojBwIP/whgx5yKCunjD3jTQyj3DES9T9zueUDLHa2AgUZ7Y/UXqLwSUyEM63G3Tsp9bmIi7CWQBYQlcxc7e9wvsdw1UrhjJod1i40nRT3wNMjqXf55FFOsriMxEX+Tb4iVHKnlVLDquBd8Hn6YCp/uZt/3LTzSENoHSapvBYHMV3NV5aG4yiAiMECgYEA+FPkgnduaR4g/lhrf0OLYcotuuyulz2spkcYs5J/rIFgKFwotWcCKFBQM7K/tCfIHm3WO7OU4DybjAhUPkT3Wf+ob2IvsHZoxQj4nhNlIuOZeEh0/pFhj5SDQivCjm5O3sj96SaaheX4PFRgGGWZOpXZ5ttriH+c0kx5kxT2fvsCgYEA129OTQlWQQvOU5c5ud396Mk1cN+FlRDPGLxOKfnUR7d+Ji6aSiSx1MH1+wXQQSnkaFo+qVk7+3GFYmdM7I7oVHaQ6tVeJFSObpqo8RtTX0/iFtcicECyEhX7LCN1/8gOAUgQH9LXAWw8oSp+7EhnH55VVFuKIbOp7D9j38CEJWECgYAnKCNaJzbzwFp3gL3CnYX274hydyu4kXMN7RwKBMm5C9V7x7xrjkiazr82x7LO148IrU0gsNqC2Uu1swKFpx5RxKxsk9DtRF6U4ytA0dIaxETI4LQCCC6YE1T3NCtDhkVNf+f5waqP/ok8Chn0/uBAiqyHaIX2ShkSWBi7hoyvzQKBgH6BaeVq2i+QEzSa1NcEOJl9rdf+KZ8DI9lOmkej4LfMVFNvgkKPka1xyFQDcesSAoIUnD8tmz9nxf+m0VEICf2vMiHz8Tg8PvdL8nDCrw6FBiqLYjmwg+CC799XY28ztqWh/3XwhTjcqi2pmqZ9TbEPdOP4bQClU97ayncVwYOBAoGBAIrRuFm/9bTk7Y3mP0TYLvJxMgaKYQtXS6CSDghNEIpj6AUDC3GwuzQQWKmYnjDTPnHVhbBIMV8/z6mKDW/JNFqdkr5CRj7JGmItubxH4GJQwk8xDZqSBZdxvfbILhwOjEklRYMlptU4izeIb+qDmx2RuCoL8zwhl7UWwIzPRzPF"
    val RSA_PRIVATE = ""

    @SuppressLint("HandlerLeak")
    private val mHandler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                SDK_PAY_FLAG -> {
                    val payResult = PayResult(msg.obj as Map<String, String>)
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    val resultInfo = payResult.result// 同步返回需要验证的信息
                    val resultStatus = payResult.resultStatus
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(this@CashRegisterActivity, "支付成功", Toast.LENGTH_SHORT).show()
                        mPresenter.payOrder(mOrderId) // 向服务器更新订单成功
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(this@CashRegisterActivity, "支付失败原因： ${payResult.memo}", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }

            }
        }
    }

    override fun onGetSignResult(result: String) {
        toast(result)
        Log.d("result = ", result)
        doAsync {// 子线程处理
            val resultMap:Map<String, String> = PayTask(this@CashRegisterActivity).payV2(result, true) // 传入签名，
            // 这里需要到主线程处理。
            uiThread {

                if (resultMap["resultStatus"].equals("9000")) {
                    mPresenter.payOrder(mOrderId) // 向服务器更新订单成功
                } else {
                    toast("支付失败原因：${resultMap["memo"]}")
                    finish()
                }
            }

        }
    }

    override fun injectComponent() {
        DaggerPayComponent.builder()
                .activityComponent(activityComponent)
                .payModule(PayModule())
                .build().inject(this)
        mPresenter.mView = this
    }
    // 订单ID , 价格
    var mOrderId: Int = 0
    var mTotalPrice: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX) // 配置沙箱环境
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cash_register)

//        ARouter.getInstance().inject(this)
        initData()
        initView()

    }

    private fun initData() {1
        mOrderId = intent.getIntExtra(ProviderConstant.KEY_ORDER_ID, -1)
        mTotalPrice = intent.getLongExtra(ProviderConstant.KEY_ORDER_PRICE, -1)
        toast("id = $mOrderId")
    }

    private fun initView() {
        updatePayType(isAliPay = true, isWeixinPay = false, isBankCardPay = false)// 默认选择支付宝
        mAlipayTypeTv.onClick(this)
        mWeixinTypeTv.onClick(this)
        mBankCardTypeTv.onClick(this)
        mTotalPriceTv.text = YuanFenConverter.changeF2YWithUnit(mTotalPrice)

        mPayBtn.onClick(this)
    }



    override fun onPayOrderResult(result: Boolean) {
        toast("支付成功")
        finish()
    }


    override fun onClick(v: View) {
        when(v.id) {
            R.id.mAlipayTypeTv -> {
                updatePayType(true, false, false)
            }
            R.id.mWeixinTypeTv -> {
                updatePayType(false, true, false)

            }
            R.id.mBankCardTypeTv -> {
                updatePayType(false, false, true)

            }
            R.id.mPayBtn -> {
                getPaySign(mOrderId, mTotalPrice)
            }
        }
    }

    fun getPaySign(orderId: Int, totolPrice: Long){
//        if (!checkNetWork()){
//            return
//        }
//        mView.showLoading()

        val rsa2 = RSA2_PRIVATE.isNotEmpty()
        val params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2,  YuanFenConverter.changeF2Y(totolPrice), orderId.toString())
        val orderParam = OrderInfoUtil2_0.buildOrderParam(params)

        val privateKey = if (rsa2) RSA2_PRIVATE else RSA_PRIVATE
        val sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2)
        val orderInfo = "$orderParam&$sign"

        val payRunnable = Runnable {
            val alipay = PayTask(this@CashRegisterActivity)
            val result = alipay.payV2(orderInfo, true)
            Log.i("msp", result.toString())

            val msg = Message()
            msg.what = SDK_PAY_FLAG
            msg.obj = result
            mHandler.sendMessage(msg)
        }

        val payThread = Thread(payRunnable)
        payThread.start()

    }

    private fun updatePayType(isAliPay:Boolean, isWeixinPay:Boolean, isBankCardPay: Boolean) {
        mAlipayTypeTv.isSelected = isAliPay
        mWeixinTypeTv.isSelected = isWeixinPay
        mBankCardTypeTv.isSelected = isBankCardPay
    }

}