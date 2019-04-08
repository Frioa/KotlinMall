package com.kotlin.base.common

import android.content.Context
import com.alipay.euler.andfix.patch.PatchManager


class AndFixManager private constructor(){
    lateinit var mPatchManager: PatchManager
    companion object {
        val instance:AndFixManager = SingletonHolder.holder
    }

    private object SingletonHolder {
        val holder= AndFixManager()
    }

    // 初始化 AndFix 方法
    fun initPatch(context: Context) {
        mPatchManager = PatchManager(context)
        mPatchManager.init(Utils.getVersionName(context))
        mPatchManager.loadPatch()
    }

    // 加载 Patch 文件
    fun addPatch(path: String) {
        try {
            if (mPatchManager != null) {
                mPatchManager.addPatch(path)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

object Utils{
    var versionName = "1.0.0"
    /*
     * 获取应用的 versionname
     */
    fun getVersionName(context: Context):String {
        val pm = context.packageManager
        val pi = pm.getPackageInfo(context.packageName, 0)
        versionName = pi.versionName
        return versionName
    }

}