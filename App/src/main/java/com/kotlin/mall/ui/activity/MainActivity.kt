package com.kotlin.mall.ui.activity

import android.annotation.SuppressLint
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Vibrator
import android.support.v4.app.Fragment
import android.util.Log
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.kotlin.base.common.AppManager
import com.kotlin.base.utils.AppPrefsUtils
import com.kotlin.goods.common.GoodsConstant
import com.kotlin.goods.ui.fragment.CartFragment
import com.kotlin.goods.ui.fragment.CategoryFragment
import com.kotlin.mall.R
import com.kotlin.mall.goods.event.UpdateCartSizeEvent
import com.kotlin.mall.message.ui.fragment.MessageFragment
import com.kotlin.mall.ui.fragment.HomeFragment
import com.kotlin.mall.ui.fragment.MeFragment
import com.kotlin.provider.event.MessageBadgeEvent
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import java.util.*
import java.util.concurrent.TimeUnit
import android.hardware.SensorEventListener;
import android.os.Handler
import android.os.Message
import android.support.v4.app.FragmentActivity
import android.widget.Toast
import android.R.attr.password
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.widget.EditText
import com.kotlin.base.common.BaseConstant
import com.kotlin.base.common.BaseConstant.Companion.SERVER_ADDRESS
import com.kotlin.base.data.net.RetrofitFactory


class MainActivity : AppCompatActivity() {
    // 摇一摇
    lateinit var sensorManager: SensorManager
    lateinit var vibrator: Vibrator

    // 双击的时间间隔
    private var preeTime:Long = 0
    // 栈数据结构管理 Fragment
    private val mStack = Stack<Fragment>()
    private val mHomeFragment by lazy { HomeFragment() }// Home 主页
    private val mCategoryFragment by lazy { CategoryFragment() }  // 分类
    private val mCartgment by lazy { CartFragment() }// 购物车
    private val mMsgFragment by lazy { MessageFragment() }// 消息
    private val mMeFragment by lazy { MeFragment() }// 我的

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sensorManager = getSystemService(SENSOR_SERVICE) as  (SensorManager)
        vibrator =  getSystemService(VIBRATOR_SERVICE) as (Vibrator)




        val str = stringFromJNI()

        initFragment()
        initBottomNav()
        changeFragment(0)
        initObserve()
        loadCartSize()

    }

    private fun initFragment() {
        val manager = supportFragmentManager.beginTransaction()
        manager.add(R.id.mContaier,mHomeFragment)
        manager.add(R.id.mContaier,mCategoryFragment)
        manager.add(R.id.mContaier,mCartgment)
        manager.add(R.id.mContaier,mMsgFragment)
        manager.add(R.id.mContaier,mMeFragment)
        manager.commit()

        mStack.add(mHomeFragment)
        mStack.add(mCategoryFragment)
        mStack.add(mCartgment)
        mStack.add(mMsgFragment)
        mStack.add(mMeFragment)
    }

    override fun onResume() {
        super.onResume()
        if (sensorManager != null) {// 注册监听器
            sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
            // 第一个参数是Listener，第二个参数是所得传感器类型，第三个参数值获取传感器信息的频率
            }
    }

    override fun onPause() {
        super.onPause()
        if (sensorManager != null) {// 取消监听器
            sensorManager.unregisterListener(sensorEventListener)
        }
    }

    private fun initBottomNav(){
        mBottomNavBar.setTabSelectedListener(object :BottomNavigationBar.OnTabSelectedListener{
            override fun onTabReselected(position: Int) {
            }

            override fun onTabUnselected(position: Int) {
            }

            override fun onTabSelected(position: Int) {
                changeFragment(position)
            }

        })
        mBottomNavBar.checkMsgBadeg(false)
    }
     fun changeFragment(position: Int) {
        val manager = supportFragmentManager.beginTransaction()
        for (fragment in mStack){
            manager.hide(fragment)
        }

        manager.show(mStack[position])
        manager.commit()
    }

    private fun initObserve(){
        Bus.observe<UpdateCartSizeEvent>()
                .subscribe{
                    loadCartSize()
                }.registerInBus(this)

        Bus.observe<MessageBadgeEvent>()
                .subscribe{
                   t: MessageBadgeEvent ->
                    run {
                        mBottomNavBar.checkMsgBadeg(t.isVisible)
                    }
                }.registerInBus(this)
    }
    private fun loadCartSize(){
        mBottomNavBar.checkCartBadge(AppPrefsUtils.getInt(GoodsConstant.SP_CART_SIZE))
    }

    override fun onBackPressed() {//双击两次 Back 退出 APP
        val time = System.currentTimeMillis()
        if ( time - preeTime  > 2000){ // 当前时间减去记录时间
            toast("再按一次退出")
            preeTime = time
        }else{
            AppManager.instance.exitApp(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
    }

    private external fun stringFromJNI(): String

    companion object {
        init {
            System.loadLibrary("native-lib")
        }

        private val SENSOR_SHAKE = 10
        var isshowDialog = false
    }

    /**
     * 重力感应监听
     */
    private val sensorEventListener = object : SensorEventListener {

        override fun onSensorChanged(event: SensorEvent) {
            // 传感器信息改变时执行该方法
            val values = event.values
            val x = values[0] // x轴方向的重力加速度，向右为正
            val y = values[1] // y轴方向的重力加速度，向前为正
            val z = values[2] // z轴方向的重力加速度，向上为正
//            Log.i("MainActivity", "x轴方向的重力加速度$x；y轴方向的重力加速度$y；z轴方向的重力加速度$z")
            // 一般在这三个方向的重力加速度达到40就达到了摇晃手机的状态。
            val medumValue = 19// 三星 i9250怎么晃都不会超过20，没办法，只设置19了
            if (Math.abs(x) > medumValue || Math.abs(y) > medumValue || Math.abs(z) > medumValue) {
                vibrator.vibrate(200)
                val msg = Message()
                msg.what = SENSOR_SHAKE
                handler.sendMessage(msg)
            }
        }

        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {

        }
    }

    /**
     * 动作执行
     */
    var handler: Handler = @SuppressLint("HandlerLeak")
    object : Handler() {

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                SENSOR_SHAKE -> {
                    Toast.makeText(this@MainActivity, "设置Service地址", Toast.LENGTH_SHORT).show()
                    if (!isshowDialog) {
                         showDialog()
                    }

                }
            }
        }

    }

    fun showDialog() {
        isshowDialog = true
        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setIcon(R.mipmap.ic_launcher)
        builder.setTitle("请输入用户名和密码")
        //    通过LayoutInflater来加载一个xml的布局文件作为一个View对象
        val view = LayoutInflater.from(this@MainActivity).inflate(R.layout.dialog_adress, null)
        //    设置我们自己定义的布局文件作为弹出框的Content
        builder.setView(view)

        val ipaddress = view.findViewById(R.id.mIPAddress) as EditText

        val a = AppPrefsUtils.getString(BaseConstant.KEY_SERVICE, SERVER_ADDRESS)

        ipaddress.setText(a)
        ipaddress.setSelection(a.length)

        builder.setPositiveButton("确定") { _, _ ->
            //    将输入的用户名和密码打印出来
            val a = ipaddress.text.toString().trim()

            AppPrefsUtils.putString(BaseConstant.KEY_SERVICE, a)
            SERVER_ADDRESS = a
            Toast.makeText(this@MainActivity, "Service 地址: $SERVER_ADDRESS", Toast.LENGTH_SHORT).show()
            RetrofitFactory.instance.setBaseUrl()
            isshowDialog = false
        }
        builder.setNegativeButton("取消") { _, _ ->
            val a = ipaddress.text.toString().trim()
            isshowDialog = false
            Toast.makeText(this@MainActivity, "Service 地址: $SERVER_ADDRESS", Toast.LENGTH_SHORT).show()
        }

        builder.show()
    }

}
