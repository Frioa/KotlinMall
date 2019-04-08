package com.kotlin.base.common

import android.app.Activity
import android.app.ActivityManager
import android.content.Context

import java.util.*

/**
    Activity 管理器
*/
open class AppManager private constructor(){

    private val activityStack:Stack<Activity> = Stack()
    companion object {
        val instance:AppManager by lazy { AppManager() }
    }
    /*
        入栈
     */
    fun addActivity(activity: Activity){
        activityStack.add(activity)
    }
    /*
        出栈
     */
    fun finishActivity(activity: Activity){
        activity.finish()
        activityStack.remove(activity)
    }
    /*
        取栈顶元素
     */
    fun currentActivity() = activityStack.lastElement()

    /*
        清理栈
     */
    fun finishAllActivity(){
        for (activity in activityStack){
            activity.finish()
        }
        activityStack.clear()
    }
/*
    退出 APP
 */
    fun exitApp(context: Context){
        finishAllActivity()
        val activityManager = context.getSystemService(Context.ACCESSIBILITY_SERVICE) as ActivityManager
        activityManager.killBackgroundProcesses(context.packageName)
        System.exit(0)
    }
}