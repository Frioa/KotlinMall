package com.kotlin.base.ext

import android.graphics.drawable.AnimationDrawable
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.kennyc.view.MultiStateView
import com.kotlin.base.R
import com.kotlin.base.data.protocol.BaseResp
import com.kotlin.base.rx.BaseFunc
import com.kotlin.base.rx.BaseFuncBoolean
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.base.utils.GlideUtils
import com.kotlin.base.widgets.DefaultTextWatcher
import com.trello.rxlifecycle.LifecycleProvider
import org.jetbrains.anko.find
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


//扩展
fun <T> Observable<T>.execute(subscriber: BaseSubscriber<T>, lifecycleProvider:LifecycleProvider<*>){
     this.observeOn(AndroidSchedulers.mainThread())    // 主线程中
             .compose(lifecycleProvider.bindToLifecycle())  //生命周期观察者
             .subscribeOn(Schedulers.io())              //主要用于读写文件、网络请求等，功能上和newThread()差不多，
                                                       // 但是他是用了无上限线程池，并能够复用空闲的线程。
             .subscribe(subscriber)
}

//UserServiceImp 中的扩展
fun <T>Observable<BaseResp<T>>.convert():Observable<T>{
     return this.flatMap(BaseFunc())
}

fun <T>Observable<BaseResp<T>>.convertBoolean():Observable<Boolean>{
     return this.flatMap(BaseFuncBoolean())
}


//扩展点击事件
fun View.onClick(listener:View.OnClickListener):View{
     setOnClickListener(listener)
     return this
}
//扩展点击事件 Labmmda
fun View.onClick(method: ()->Unit):View{
     setOnClickListener { method() }
     return this
}

fun Button.enable(et: EditText, method: () -> Boolean ){
     val btn = this
     et.addTextChangedListener(object :DefaultTextWatcher(){// 监听文字发生改变
          override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
               // 判断 button 是否可变，取决于传入的 method()
               btn.isEnabled = method()
          }
     })
}
fun ImageView.loadUrl(url: String) {
     GlideUtils.loadUrlImage(context, url, this)
}
fun MultiStateView.startLoading(){
     viewState = MultiStateView.VIEW_STATE_LOADING
     val loadingView = getView(MultiStateView.VIEW_STATE_LOADING)
     val animBackground = loadingView!!.find<View>(R.id.loading_anim_view).background
     (animBackground as AnimationDrawable).start()
}

fun View.setVisible(visible:Boolean){
     this.visibility = if (visible) View.VISIBLE else View.GONE
}
