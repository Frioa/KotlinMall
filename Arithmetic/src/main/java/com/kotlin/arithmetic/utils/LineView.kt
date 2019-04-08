package com.kotlin.arithmetic.utils

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator

class LineView : View {
    private val TAG = "LineView"
    /*
        View 高
     */
    private var mViewHeight = 0
    /*
        View 宽
     */
    private var mViewWidth = 0
    /*

     */
    var mArray_i_ = 0
    var endY = 0f

    /**
     * 柱状图笔
     */
    lateinit var mPaint: Paint


    constructor(context: Context) : this(context, null)

    constructor(context: Context,attrs: AttributeSet? = null ): this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0): super(context, attrs, defStyleAttr)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setDimension(widthMeasureSpec,heightMeasureSpec)
    }

    /*
     * 重新赋予宽高
     */
    private fun setDimension(widthMeasureSpec:Int, heightMeasureSpec: Int) {
        mViewWidth =  View.MeasureSpec.getSize(widthMeasureSpec)
        mViewHeight = View.MeasureSpec.getSize(heightMeasureSpec)
        Log.i(TAG, "mViewWidth=" + mViewWidth + "px")
        Log.i(TAG, "mViewHeight=" + mViewHeight + "px")
        setMeasuredDimension(mViewWidth, mViewHeight)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        endY = mViewHeight  - mArray_i_ * (mViewHeight).toFloat() / LineChatView.yMax
        canvas.drawLine(mViewWidth / 2f, mViewHeight.toFloat(), mViewWidth / 2f, endY, mPaint)
    }

    fun updateView(mPaint: Paint) {
        this.mPaint = mPaint
        invalidate()
    }

    fun startAnimation(end: Int) {
//        val set = AnimatorSet()
        Log.d(TAG, "name = ${Thread.currentThread().name}  $mArray_i_   ${end} ")

        val animator = ValueAnimator.ofInt(mArray_i_, end) // 开始与结束位置
        animator.duration = LineChatView.mAnimationDuration
        animator.interpolator = LinearInterpolator()
        animator.addUpdateListener { valueAnimator ->
            mArray_i_ = valueAnimator.animatedValue as Int
            if (mArray_i_ == end) mPaint = LineChatView.mPaint
            invalidate()
        }
        animator.start()
//        set.playTogether(animator)
//        set.interpolator = LinearInterpolator() // 匀速
//        set.duration = LineChatView.mAnimationDuration   // 设置动画时间
//        set.start()
    }

    fun keyView(boolean: Boolean) {// true Key View
        return if (boolean){
            mPaint = LineChatView.mPaintRed
            invalidate()
        }
        else{
            mPaint = LineChatView.mPaintBlue
            invalidate()
        }

    }

}