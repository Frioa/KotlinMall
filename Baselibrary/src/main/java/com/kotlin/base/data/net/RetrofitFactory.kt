package com.kotlin.base.data.net

import com.kotlin.base.common.BaseConstant
import com.kotlin.base.utils.AppPrefsUtils
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitFactory  private constructor(){
    //单例模式
    companion object {//修饰
        val instance:RetrofitFactory by lazy { RetrofitFactory() }
    }

    private var retrofit:Retrofit
    private val interceptor:Interceptor     //请求网络向 header 里面加入数据，

    //初始化 retrofit 方法
    init {
        interceptor = Interceptor {
            chain ->
             val request = chain.request()
                    .newBuilder()
                    .addHeader("Content-Type","application/json")
                    .addHeader("charset","utf-8")
                     .addHeader("token",AppPrefsUtils.getString(BaseConstant.KEY_SP_TOKEN))
                     .build()
            chain.proceed(request)

        }
        retrofit = Retrofit.Builder()
                .baseUrl(AppPrefsUtils.getString(BaseConstant.KEY_SERVICE, BaseConstant.SERVER_ADDRESS))
                .addConverterFactory(GsonConverterFactory.create())// 数据解析器，转化为Java对象
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())// 支持 RXJava，创建 RXjava 调度器
                .client(initClient())   // Okhttp 客户端
                .build()
    }

    public fun setBaseUrl() {

        retrofit = Retrofit.Builder()
                .baseUrl(AppPrefsUtils.getString(BaseConstant.KEY_SERVICE, BaseConstant.SERVER_ADDRESS))
                .addConverterFactory(GsonConverterFactory.create())// 数据解析器，转化为Java对象
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())// 支持 RXJava，创建 RXjava 调度器
                .client(initClient())   // Okhttp 客户端
                .build()
    }


    //构建 okhttp 客户端
    private fun initClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(interceptor)    //加入初始化的head拦截器
                .addInterceptor(initLogInterceptor())//日志拦截器
                .connectTimeout(10, TimeUnit.SECONDS)//链接时间与超时时间
                .readTimeout(10, TimeUnit.SECONDS)
                .build()
    }

    //日志拦截器
    private fun initLogInterceptor(): Interceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY// 日志等级
        return interceptor
    }

    fun <T>create(service:Class<T>):T{
        return retrofit.create(service)
    }

}