package com.kotlin.order.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.kotlin.base.ext.onClick
import com.kotlin.base.ext.setVisible
import com.kotlin.base.injection.component.ActivityComponent
import com.kotlin.base.ui.activity.BaseActivity
import com.kotlin.base.ui.activity.BaseMvpActivity
import com.kotlin.base.utils.YuanFenConverter
import com.kotlin.order.R
import com.kotlin.order.data.protocol.Order
import com.kotlin.order.event.SelectAddressEvent
import com.kotlin.order.injection.component.DaggerOrderComponent
import com.kotlin.order.injection.module.OrderModule
import com.kotlin.order.presenter.OrderConfirmPresenter
import com.kotlin.order.presenter.view.OrderConfirmView
import com.kotlin.order.ui.adapter.OrderGoodsAdapter
import com.kotlin.provider.common.ProviderConstant
import com.kotlin.provider.router.RouterPath
import com.kotlin.provider.router.RouterPath.OrderCenter.Companion.PATH_ORDER_CONFIRM
import kotlinx.android.synthetic.main.activity_order_confirm.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast


@Route(path = PATH_ORDER_CONFIRM)
class OrderConfirmActivity:BaseMvpActivity<OrderConfirmPresenter>(), OrderConfirmView{


    @Autowired(name = ProviderConstant.KEY_ORDER_ID)
    @JvmField       //Kotlin 中使用
    var mOrderId:Int = 0    //通过 ARouter 取到传过来的值

    private lateinit var mAdapter: OrderGoodsAdapter
    private var mOrder:Order? = null

    override fun injectComponent() {
        DaggerOrderComponent.builder().activityComponent(activityComponent).orderModule(OrderModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_confirm)

        initView()
        initObseve()
        loadData()

    }



    private fun initView() {
        mShipView.onClick {
            startActivity<ShipAddressActivity>()
        }
        mSelectShipTv.onClick {
            startActivity<ShipAddressActivity>()
        }

        mSubmitOrderBtn.onClick {
            mOrder?.let {//不为空提交
                mPresenter.submitOrder(it)
            }

        }

        mOrderGoodsRv.layoutManager = LinearLayoutManager(this)
        mAdapter = OrderGoodsAdapter(this)
        mOrderGoodsRv.adapter = mAdapter

    }

    private fun initObseve() {
        Bus.observe<SelectAddressEvent>()
                .subscribe { 
                    t: SelectAddressEvent->
                    run {
                        mOrder?.let {
                            it.shipAddress = t.address
                        }
                        updateAddressView()

                    }

                }.registerInBus(this)
    }



    private fun loadData() {
        mPresenter.getOrderById(mOrderId)
    }

    override fun onGetOrderById(result: Order) {
        mOrder = result
        mAdapter.setData(result.orderGoodsList)
        mTotalPriceTv.text = "合计：${YuanFenConverter.changeF2YWithUnit(result.totalPrice)}"
        updateAddressView()
    }

    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
    }

    private fun updateAddressView() {

        mOrder?.let {
            if (it.shipAddress == null){
                mSelectShipTv.setVisible(true)
                mShipView.setVisible(false)

            }else{
                mSelectShipTv.setVisible(false)
                mShipView.setVisible(true)


                mShipNameTv.text = it.shipAddress!!.shipUserName + "   "+
                        it.shipAddress!!.shipUserMobile
                mShipAddressTv.text = it.shipAddress!!.shipAddress
            }
        }

    }

    override fun onSubmitOrderResult(result: Boolean) {
        toast("订单提交成功")
        ARouter.getInstance().build(RouterPath.PaySDK.PATH_PAY)
                .withInt(ProviderConstant.KEY_ORDER_ID, mOrder!!.id)
                .withLong(ProviderConstant.KEY_ORDER_PRICE, mOrder!!.totalPrice)
                .navigation()
        finish()
    }
}