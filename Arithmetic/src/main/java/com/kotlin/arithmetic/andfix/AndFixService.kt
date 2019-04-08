package com.kotlin.arithmetic.andfix

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.inputmethodservice.ExtractEditText
import android.os.Handler
import android.os.IBinder
import android.os.Message
import java.io.File

/**
 * @function: 1. 检查 patch 文件  2. 下载 patch 文件 3. 加载下载好的patch文件
 */
class AndFixService : Service(){
    private val TAG = AndFixService::javaClass.name
    lateinit var mPatchFileDir:String
    lateinit var mPatchFile:File

    companion object {
        val UPDATA_PATCH = 0x02
        val DOWNLOAD_PATCH = 0x01
        val FILE_END = ".apatch"
    }

    private val mHandler = @SuppressLint("HandlerLeak")
    object :Handler() {
        override fun handleMessage(msg: Message?) {
            when (msg!!.what) {
                UPDATA_PATCH -> {
                    checkPatchUpdate()
                }
                DOWNLOAD_PATCH -> {

                }

            }
            super.handleMessage(msg)
        }
    }


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        init()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // 调用 Handler 检查更新
        mHandler.sendEmptyMessage(UPDATA_PATCH)
        return START_NOT_STICKY // 系统回收不会重启
    }

    // 文件初始化
    private fun init() {
        mPatchFileDir = "${externalCacheDir.absolutePath}/apatch/"
        val patchDir = File(mPatchFileDir)

        // 不存在创建文件夹
        try {
            if (patchDir == null || !patchDir.exists()) {
                patchDir.mkdir()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            stopSelf()
        }
    }

    // 检查服务器是否有patch文件
    private fun checkPatchUpdate() {

    }

}