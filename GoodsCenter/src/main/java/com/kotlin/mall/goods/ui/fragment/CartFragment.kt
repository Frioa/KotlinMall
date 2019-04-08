package com.kotlin.goods.ui.fragment


import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.launcher.ARouter
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.kennyc.view.MultiStateView
import com.kotlin.base.ext.onClick
import com.kotlin.base.ext.setVisible
import com.kotlin.base.ext.startLoading
import com.kotlin.base.ui.fragment.BaseMvpFragment
import com.kotlin.base.utils.AppPrefsUtils
import com.kotlin.base.utils.YuanFenConverter
import com.kotlin.goods.common.GoodsConstant
import com.kotlin.goods.data.protocol.CartGoods
import com.kotlin.mall.goods.R
import com.kotlin.mall.goods.event.CartAllCheckedEvent
import com.kotlin.mall.goods.event.UpdateCartSizeEvent
import com.kotlin.mall.goods.event.UpdateTotalPriceEvent
import com.kotlin.mall.goods.injection.component.DaggerCartComponent
import com.kotlin.mall.goods.injection.component.DaggerCatetgoryComponent
import com.kotlin.mall.goods.injection.module.CartModule
import com.kotlin.mall.goods.presenter.CartListPresenter
import com.kotlin.mall.goods.presenter.view.CartListView
import com.kotlin.mall.goods.ui.adapter.CartGoodsAdapter
import com.kotlin.provider.common.ProviderConstant
import com.kotlin.provider.router.RouterPath
import kotlinx.android.synthetic.main.fragment_cart.*
import org.jetbrains.anko.support.v4.toast

/*
    商品分类 Fragment
 */
class CartFragment : BaseMvpFragment<CartListPresenter>(), CartListView{


    //一级分类Adapterm
    lateinit var mAdapter: CartGoodsAdapter
    private var mTotalPrice :Long = 0
    //
    /*
        Dagger注册
     */
    override fun injectComponent() {
        DaggerCartComponent.builder().activityComponent(activityComponent).cartModule(CartModule()).build().inject(this)
        mPresenter.mView = this
    }
//

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater?.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()

        initObserve()

    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    /*
        初始化视图
     */
    private fun initView() {
        mCartGoodsRv.layoutManager = LinearLayoutManager(context)
        mAdapter = CartGoodsAdapter(context!!)
        mCartGoodsRv.adapter = mAdapter

        mHeaderBar.getRightView().onClick {
            refreshEditStaus()
        }

        mAllCheckedCb.onClick {
            for (item in mAdapter.dataList){
                item.isSelected = mAllCheckedCb.isChecked
            }
            updateTotalPrice()
            mAdapter.notifyDataSetChanged()
        }

        mDeleteBtn.onClick {
            val cartIdList:MutableList<Int> = arrayListOf()
            mAdapter.dataList.filter { it.isSelected }
                    .mapTo(cartIdList){it.id}
            if(cartIdList.size == 0)  {
                toast("请选择需要删除的数据")
            }else{
                mPresenter.deleteCartList(cartIdList)
            }
        }

        mSettleAccountsBtn.onClick {
            val cartGoodsList:MutableList<CartGoods> = arrayListOf()
            mAdapter.dataList.filter { it.isSelected }
                    .mapTo(cartGoodsList){it}
            if(cartGoodsList.size == 0)  {
                toast("请选择需要结算的数据")
            }else{
                mPresenter.submitCartList(cartGoodsList, mTotalPrice )
            }
        }
    }

    //编辑按钮
    private fun refreshEditStaus() {
        val isEditStaus = getString(R.string.common_edit) == mHeaderBar.getRightText()  //是否是编辑
        mTotalPriceTv.setVisible(isEditStaus.not())
        mSettleAccountsBtn.setVisible(isEditStaus.not())
        mDeleteBtn.setVisible(isEditStaus)
        mHeaderBar.getRightView().text = if (isEditStaus) getString(R.string.common_complete) else getString(R.string.common_edit)
    }

    /*
        加载数据
     */
    private fun loadData() {
        mMultiStateView.startLoading()
        mPresenter.getCategory()
    }


    override fun onGetCartListResult(result: MutableList<CartGoods>?) {
        if(result != null && result.size > 0){
            mAdapter.setData(result)
            mHeaderBar.getRightView().setVisible(true)
            mAllCheckedCb.isChecked = false
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
        }else{
            mHeaderBar.getRightView().setVisible(false)
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
        }
        AppPrefsUtils.putInt(GoodsConstant.SP_CART_SIZE,result?.size?:0)
        Bus.send(UpdateCartSizeEvent())
        updateTotalPrice()
    }

    private fun initObserve() {
        Bus.observe<CartAllCheckedEvent>().subscribe {
            t: CartAllCheckedEvent? ->
                run {
            mAllCheckedCb.isChecked = t!!.isAllChecked
            updateTotalPrice()
            }
        }.registerInBus(this)

        Bus.observe<UpdateTotalPriceEvent>().subscribe {
            updateTotalPrice()
        }.registerInBus(this)

    }

    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
    }

    private fun updateTotalPrice(){
        mTotalPrice = mAdapter.dataList.filter { it.isSelected }//选中的商品求和
                .map { it.goodsCount * it.goodsPrice }
                .sum()

        mTotalPriceTv.text = "合计：${YuanFenConverter.changeF2YWithUnit(mTotalPrice)}"
    }

    override fun onDeleteCartListResult(result: Boolean?) {
        toast("删除成功")
        refreshEditStaus()
        loadData()
    }

    override fun onSubmitCartListResult(result: Int) {
        toast("Arouter"+result)
        ARouter.getInstance().build(RouterPath.OrderCenter.PATH_ORDER_CONFIRM)
                .withInt(ProviderConstant.KEY_ORDER_ID, result)
                .navigation()
    }

    fun setBackVisible(isVisBle:Boolean){
        mHeaderBar.getLeftView().setVisible(isVisBle)
    }
}
