package com.kotlin.arithmetic.ui.activity

import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Message
import android.util.Log
import android.widget.Toast

import com.kotlin.arithmetic.R
import com.kotlin.arithmetic.presenter.SortPresenter
import com.kotlin.arithmetic.presenter.view.SortView
import com.kotlin.arithmetic.sort.list.impl.MyArrayList
import com.kotlin.arithmetic.sort.method.builder.BaseBuider
import com.kotlin.arithmetic.sort.method.model.AbsSortModel
import com.kotlin.arithmetic.sort.method.model.quick.impl.QuickOneModel
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.activity.BaseMvpActivity
import com.kotlin.user.injection.component.DaggerSortComponent
import kotlinx.android.synthetic.main.activity_main.*

import org.jetbrains.anko.toast
import java.lang.ref.WeakReference
import javax.inject.Inject

class MainActivity @Inject constructor() : BaseMvpActivity<SortPresenter>(), UpdateView, SortView {

    override fun injectComponent() {
        DaggerSortComponent.builder().activityComponent(activityComponent).build().inject(this)
        mPresenter.mView = this
    }

    lateinit var mArray: MyArrayList
    lateinit var  model: AbsSortModel


    lateinit var mainHandler : MainHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initArray()
        toast(stringFromJNI())

    }

    private fun initArray() {
        // 初始化 , 发送初始化数组的 Message
        val runnable = Runnable {
            mArray = MyArrayList(SIZE, false)
                    .generateRandomArrayList(1,25)
                    .generateAlmostArrayList(0)
                    .getInstance()

            model = BaseBuider(QuickOneModel (this))
                    .setPosition(position)
                    .setList(mArray)
                    .setPrintLog(false)
                    .builder()

            val message = mainHandler.obtainMessage()
            message.what = MSG_THREAD_INIT // 通知主线程的 Handler 初始化柱状图
            mainHandler.sendMessage(message)
            line_chat.setData(model)

            mPresenter.mView.hideLoading()
        }
        mPresenter.initArray(runnable)
    }

    private fun initView() {
        // 初始化 主线程 Handler
        mainHandler = MainHandler(this)

        BtAdd.onClick {
            model.mArray[0] += 10
            line_chat.start(0)
        }
        BtReduce.onClick {
            model.mArray[0] -= 10
            line_chat.start(0)
        }
        BtStart.onClick {
            val runnable = Runnable{
                line_chat.starSort()
            }
            mPresenter.startSort(runnable)
        }
    }

    private fun initData() {
        line_chat.startAll()
        Log.d("LineChatView", line_chat.toString())
    }

    external fun stringFromJNI() :String

    companion object {
        val SIZE = 20
        var position = true
        val TAG = "MainActivityTAG"

        val MSG_THREAD_INIT = 2
        val MSG_UPDATEVIEW = 4
        val MSG_KEYVIEW = 5
        val LOG_ERROR_REPET = 6
        init {
            // 加载 Nactive 库
            System.loadLibrary("native-lib")
        }

        class MainHandler(activity: MainActivity): Handler() {
            private var mActivity: WeakReference<MainActivity> = WeakReference(activity)
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                val activity = mActivity.get()
                if (mActivity.get() == null) return
                when(msg.what) {
                    // 初始化，LineChatView
                    MSG_THREAD_INIT -> {
                        activity!!.initData()
                    }
                    // 图形化展示
                    MSG_UPDATEVIEW -> {
                        activity!!.line_chat.start(msg.arg1)
                    }
                    // KEY view
                    MSG_KEYVIEW -> {
                        activity!!.line_chat.keyView(msg.arg1, msg.obj as Boolean)
                    }
                    LOG_ERROR_REPET -> {
                        Toast.makeText(activity, "无法重复排序", Toast.LENGTH_SHORT).apply { show() }
                    }
                }
            }
        }
    }

    override fun showLog() {
        val message = mainHandler.obtainMessage()
        message.what = LOG_ERROR_REPET
        mainHandler.sendMessage(message)
    }

    override fun updateView(i: Int) {
//        Log.d("冒泡排序", "name = ${Thread.currentThread().name} arr[$i]=${model.mArray[i]} ")
        val message = mainHandler.obtainMessage()
        message.what = MSG_UPDATEVIEW
        message.arg1 = i
        mainHandler.sendMessage(message)
    }

    override fun keyView(i: Int, boolean: Boolean) {
        val message = mainHandler.obtainMessage()
        message.what = MSG_KEYVIEW
        message.arg1 = i

        message.obj = boolean
        mainHandler.sendMessage(message)
    }

    override fun onDestroy() {
        super.onDestroy()
        MainHandler(this).removeCallbacksAndMessages(null)
    }
}



