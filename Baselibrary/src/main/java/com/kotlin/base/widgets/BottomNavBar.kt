package com.kotlin.base.widgets

import android.content.Context
import android.util.AttributeSet
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.ashokvarma.bottomnavigation.ShapeBadgeItem
import com.ashokvarma.bottomnavigation.TextBadgeItem
import com.kotlin.base.R
/*
    底部导航栏
 */

class BottomNavBar @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BottomNavigationBar(context, attrs, defStyleAttr) {

    private val mCartBadge:TextBadgeItem =  TextBadgeItem()   //设置小红点

    private val mMsgBadge:ShapeBadgeItem = ShapeBadgeItem()

    init{

        //首页
        val homeItem = BottomNavigationItem(R.drawable.btn_nav_home_press,resources.getString(R.string.nav_bar_home))
                .setInactiveIconResource(R.drawable.btn_nav_home_normal)    //未选中图标
                .setActiveColorResource(R.color.common_blue)                // 选中颜色
                .setInActiveColorResource(R.color.text_normal)              //未选中字体颜色
        //分类
        val categoryItem = BottomNavigationItem(R.drawable.btn_nav_category_press,resources.getString(R.string.nav_bar_category))
                .setInactiveIconResource(R.drawable.btn_nav_category_normal)    //未选中图标
                .setActiveColorResource(R.color.common_blue)                // 选中颜色
                .setInActiveColorResource(R.color.text_normal)              //未选中字体颜色
        //购物车
        val cartItem = BottomNavigationItem(R.drawable.btn_nav_cart_press,resources.getString(R.string.nav_bar_cart))
                .setInactiveIconResource(R.drawable.btn_nav_cart_normal)    //未选中图标
                .setActiveColorResource(R.color.common_blue)                // 选中颜色
                .setInActiveColorResource(R.color.text_normal)              //未选中字体颜色
                .setBadgeItem(mCartBadge)


 //       cartItem.setBadgeItem(mCartBadge)
        mCartBadge.setText("101")

        //消息
        val messageItem = BottomNavigationItem(R.drawable.btn_nav_msg_press,resources.getString(R.string.nav_bar_msg))
                .setInactiveIconResource(R.drawable.btn_nav_msg_normal)    //未选中图标
                .setActiveColorResource(R.color.common_blue)                // 选中颜色
                .setInActiveColorResource(R.color.text_normal)              //未选中字体颜色
                .setBadgeItem(mMsgBadge)

        mMsgBadge.setShape(ShapeBadgeItem.SHAPE_OVAL)


        //个人
        val userItem = BottomNavigationItem(R.drawable.btn_nav_user_press,resources.getString(R.string.nav_bar_user))
                .setInactiveIconResource(R.drawable.btn_nav_user_normal)    //未选中图标
                .setActiveColorResource(R.color.common_blue)                // 选中颜色
                .setInActiveColorResource(R.color.text_normal)              //未选中字体颜色
        //设置属性
        setMode(BottomNavigationBar.MODE_FIXED)
        setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
        setBarBackgroundColor(R.color.common_white)

        addItem(homeItem)
                .addItem(categoryItem)
                .addItem(cartItem)
                .addItem(messageItem)
                .addItem(userItem)
                .setFirstSelectedPosition(0)    //默认选择第一个
                .initialise()           //初始化
    }

    fun checkCartBadge(count:Int){
        if (count ==0 ){
            mCartBadge.hide()
        }else {
            mCartBadge.show()
            mCartBadge.setText("${count}")
        }
    }

    fun checkMsgBadeg(isVisiable:Boolean){
        if (isVisiable){
            mMsgBadge.show()
        }else{
            mMsgBadge.hide()
        }
    }
}