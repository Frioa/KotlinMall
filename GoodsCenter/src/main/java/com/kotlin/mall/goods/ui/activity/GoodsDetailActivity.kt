package com.kotlin.mall.goods.ui.activity

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.TableLayout
import com.alibaba.android.arouter.launcher.ARouter
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.activity.BaseActivity
import com.kotlin.base.utils.AppPrefsUtils
import com.kotlin.goods.common.GoodsConstant
import com.kotlin.mall.goods.R
import com.kotlin.mall.goods.event.AddCartEvent
import com.kotlin.mall.goods.event.UpdateCartSizeEvent
import com.kotlin.mall.goods.ui.adapter.GoodsDetailVpAdapter
import com.kotlin.provider.common.afterLogin
import com.kotlin.provider.common.isLogined
import com.kotlin.provider.router.RouterPath.UserCenter.Companion.PATH_LOGIN
import kotlinx.android.synthetic.main.activity_goods_detail.*
import org.jetbrains.anko.startActivity
import q.rorbin.badgeview.QBadgeView

class GoodsDetailActivity : BaseActivity(){

    private lateinit var mCartBdage:QBadgeView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goods_detail)

        initView()
        initObserve()
        loadCartSize()
    }

    private fun loadCartSize() {
        setCartBadge()
    }

    private fun initView() {
        //mGoodsDetailTab.tabMode = TableLayout.MODE_FIXED
        // TabLayout 与 ViewPager 关联
        mGoodsDetailVp.adapter = GoodsDetailVpAdapter(supportFragmentManager,this)
        mGoodsDetailTab.setupWithViewPager(mGoodsDetailVp)

        mAddCartBtn.onClick {
            afterLogin {
                Bus.send(AddCartEvent())
            }
        }

        mEnterCartTv.onClick {
            startActivity<CartActivity>()
        }

        mLeftIv.onClick   {
            finish()
        }

        mCartBdage = QBadgeView(this)
    }
    private fun initObserve(){
        Bus.observe<UpdateCartSizeEvent>()
                .subscribe{
                    setCartBadge()
                }.registerInBus(this)
    }

    private fun setCartBadge() {
        mCartBdage.badgeGravity = Gravity.END or Gravity.TOP
        mCartBdage.setGravityOffset(22f,-2f,true)
        mCartBdage.setBadgeTextSize(6f,true)
        mCartBdage.bindTarget(mEnterCartTv).badgeNumber = AppPrefsUtils.getInt(GoodsConstant.SP_CART_SIZE)
    }

    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
    }

}