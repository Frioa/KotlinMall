package com.kotlin.arithmetic.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.kotlin.arithmetic.R
import com.kotlin.arithmetic.sort.method.model.AbsSortModel
import com.kotlin.arithmetic.ui.activity.MainActivity.Companion.SIZE

/*
    1. LinearLayout 中 onDraw 方法无作用，加入背景颜色解决
 */
class LineChatView  : LinearLayout {

    companion object {
        var yMax = 0
        /**
         * 动画时长,默认1秒
         */
        var mAnimationDuration = 300L
        /**
         * 柱状图笔
         */
        var mPaint: Paint = Paint()
        /**
         * 蓝色
         */
        var mPaintBlue: Paint = Paint()
        /**
         * 红色
         */
        var mPaintRed: Paint = Paint()
    }

    val TAG = "DoubleLineChatView"
    var mContext : Context
    lateinit var mModel: AbsSortModel
    /*
        管理 view
     */
    lateinit var viewList : ArrayList<LineView>

    lateinit var  mArray :ArrayList<Int>
    /**
     * Y轴文字宽度
     */
    private var mYDistance: Float = 0f
    /**
     * X轴底部高度
     */
    private var mXDistance: Float = 0f
    /**
     * 柱状图之间的距离（偏大距离）
     */
    private var mBigDistance: Float = 0f
    /**
     * 两个柱状图之间的距离（偏小距离）
     */
    private var mSmallDistance: Float = 1f

    /**
     * 柱状图宽度
     */
    private var mLineWidth: Float = 0f
    /**
     * View高度
     */
    private var mViewHeight: Int = 0
    /**
     * View宽度
     */
    private var mViewWidth: Int = 0

    /**
     * XY轴画笔
     */
    private val mPaintTextXY: Paint = Paint()


    var yMin = 0
    var xMax = 0
    var xMin = 0
    /**
     * x 轴第一个数的坐标
     */

    /**
     * 颜色字体等变量
     */
    private var mLeftLineBackGroundColor = Color.GRAY
    private var mRightLineBackGroundColor = Color.BLUE
    private val mLeftLineTextColor = Color.RED
    private val mRightLineTextColor = Color.BLUE
    private val mLineTextSize: Float = 0.toFloat()
    private var mLineXYColor = Color.BLACK
    private var mLineXYSize: Float = 0.toFloat()
    /**
     * 是否显示XY轴箭头
     */
    private var mIsShowArrow = true
    /**
     * 是否显示Y轴间隔标记
     */
    private var mIsShowArrowYInterval = true


    /**
     * y 轴数字数量
     */
    private val Y_NUMBER_NUM = 4
    /**
     * 点击事件的坐标
     */
    var onClickIndex = -1
    constructor(context: Context) : this(context, null)

    constructor(context: Context,attrs: AttributeSet? = null ): this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0): super(context, attrs, defStyleAttr) {

        mContext = context
        //获取自定义属性
        val array = context.obtainStyledAttributes(attrs, R.styleable.DoubleLineChatView)
        //左边柱状图的背景色
        mLeftLineBackGroundColor = array.getColor(R.styleable.DoubleLineChatView_chatview_left_background_color, mLeftLineBackGroundColor);
        //右边柱状图的背景色
        mRightLineBackGroundColor = array.getColor(R.styleable.DoubleLineChatView_chatview_right_background_color, mRightLineBackGroundColor);
        //是否显示XY轴的箭头
        mIsShowArrow = array.getBoolean(R.styleable.DoubleLineChatView_chatview_show_arrow, true)
        //是否显示Y轴的数据间隔标志
        mIsShowArrowYInterval = array.getBoolean(R.styleable.DoubleLineChatView_chatview_show_y_interval, true)
        //柱状图的宽度
        // 初始化 柱状图的宽度
        mLineWidth = if (SIZE <= 10) array.getDimension(R.styleable.DoubleLineChatView_chatview_line_width, dip2px(mContext, 25f))
        else if (SIZE >=21) dip2px(mContext, 12f)
        else dip2px(mContext, (21 - SIZE) * 1.04f + 12)
        mLineWidth = mLineWidth.toInt().toFloat() // 防止后面精度丢失

        //两组柱状图之间的距离（偏大距离）
        mBigDistance = array.getDimension(R.styleable.DoubleLineChatView_chatview_line_big_distance, dip2px(mContext, 20f))
        //两个柱状图之间的距离（偏小距离）
        mSmallDistance = array.getDimension(R.styleable.DoubleLineChatView_chatview_line_small_distance, dip2px(mContext, 1f)).toInt().toFloat()
        //Y轴数据的宽度，也就是Y轴距离左边的宽度，这个要根据数据字符串的长度进行调整
        mYDistance = array.getDimension(R.styleable.DoubleLineChatView_chatview_y_distance, dip2px(mContext, 40f))
        //X轴数据的高度，也就是X轴距离底部的距离
        mXDistance = array.getDimension(R.styleable.DoubleLineChatView_chatview_x_distance, dip2px(mContext, 40f))
        //柱状图生长动画时间，默认1秒
        mAnimationDuration = array.getInteger(R.styleable.DoubleLineChatView_chatview_animation_duration, 1000).toLong()
        //XY轴的字体大小
        mLineXYSize = array.getDimension(R.styleable.DoubleLineChatView_chatview_text_xy_size, sp2px(mContext, 14f).toFloat())
        //XY轴的颜色以及字体颜色
        mLineXYColor = array.getColor(R.styleable.DoubleLineChatView_chatview_text_xy_color, mLineXYColor)
        array.recycle()
        initView()
        // 处理 onDraw 方法失效
        this.background = resources.getDrawable(R.drawable.line_chart_bg)
//        setDividerDrawable(resources.getDrawable(R.drawable.abc_ab_share_pack_mtrl_alpha))
        setWillNotDraw(false)

    }
    /**
     * 初始化画笔
     */
    private fun initView() {
        // 柱状图
        mPaint.color = mLeftLineBackGroundColor
        mPaint.strokeWidth = mLineWidth
        mPaint.isAntiAlias = true
        mPaint.alpha = 200

        // 柱状图 红画笔
        mPaintRed.color = mLeftLineTextColor
        mPaintRed.strokeWidth = mLineWidth
        mPaintRed.isAntiAlias = true
        mPaintRed.alpha = 200

        mPaintBlue.color =  mRightLineBackGroundColor
        mPaintBlue.strokeWidth = mLineWidth
        mPaintBlue.isAntiAlias = true

         // XY轴画笔
        mPaintTextXY.strokeWidth = 3f
        mPaintTextXY.color = mLineXYColor
        mPaintTextXY.textSize = mLineXYSize
        mPaintTextXY.isAntiAlias = true



    }
    /**
     * 开启动画，并且绘制图表
     */
 /*   fun start() {
        val set = AnimatorSet()
        for (i in 0 until mArray.size) {
            val animator = ValueAnimator.ofInt(0, mArray[i])
            val finalI = i
            animator.addUpdateListener { valueAnimator ->
                mArray[finalI] = valueAnimator.animatedValue as Int
                invalidate()

            }
            set.playTogether(animator)
        }

        set.interpolator = DecelerateInterpolator()
        set.duration = mAnimationDuration   // 设置动画时间
        set.start()
    }*/
    fun start(i:Int) {
        Log.d("冒泡排序 view", "name = ${Thread.currentThread().name}  第 $i 个view = ${mArray[i]} ")
        viewList[i].startAnimation(mArray[i])
    }

    fun startAll() {
        viewList.forEach {
            it.visibility = View.VISIBLE
        }
    }

    fun keyView(i:Int,boolean: Boolean) {
        viewList[i].keyView(boolean)
    }



    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setDimension(heightMeasureSpec)
    }

    /**
     * 重新赋予宽高
     */
    private fun setDimension(heightMeasureSpec: Int) {
        mViewWidth = (mYDistance +  mBigDistance + (mArray.size + 1) * (mLineWidth + mSmallDistance)).toInt()
        mViewHeight = View.MeasureSpec.getSize(heightMeasureSpec)
        Log.i(TAG, "mViewWidth=" + mViewWidth + "px")
        Log.i(TAG, "mViewHeight=" + mViewHeight + "px")
        setMeasuredDimension(mViewWidth, mViewHeight)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        /**
         * 绘制柱状图
         */
//        drawLineData(canvas)
        /**
          *绘制坐标轴
         */
        drawLineXY(canvas)
        /**
          绘制X坐标值
         */
        drawLineX(canvas)
        /**
         * 绘制Y坐标值
         */
        drawLineY(canvas)
    }


    /**
     * 加载数据
     * @param mArray  柱状图数据
     */
  /*  fun setData(model: AbsSortModel) {
        this.mArray = model.mArray
        yMax = model.maxArray
        yMin = model.minArray

        xMax = model.mArray.size - 1

        while (yMax % 5 != 0) yMax++
        Log.i(TAG, "yMax = $yMax" );
    }*/

    fun setData(model: AbsSortModel) {
        mModel = model
        this.mArray = mModel.mArray
        yMax = mModel.maxArray
        yMin = mModel.minArray

        xMax = mModel.mArray.size - 1

        while (yMax % Y_NUMBER_NUM != 0) yMax++

        Log.i(TAG, "yMax = $yMax" )
        // 初始化 viewList
        viewList = ArrayList(mArray.size)
        initLineView()
    }

    private fun initLineView() {

        for (i in 0 until mArray.size) {
            val view = LineView(context)
            val lp =  LinearLayout.LayoutParams(mLineWidth.toInt(), LinearLayout.LayoutParams.MATCH_PARENT)
            view.visibility = View.GONE
            lp.gravity = bottom

            if (i == 0) lp.leftMargin += (mYDistance + mBigDistance ).toInt()
            else  lp.leftMargin = mSmallDistance.toInt()
            view.mArray_i_ = mArray[i]
            view.mPaint = mPaint

            viewList.add(view)
            addView(view, lp)
        }

    }

    fun starSort() {
        Log.d(TAG, ""+mModel.isStart)
        if (!mModel.isStart){
            mModel.startSort("")
        }else {

            mModel.mView.showLog()


        }
    }

    /**
     * 绘制柱状图
     */
    private fun drawLineData(canvas: Canvas) {

        for (i in 0 until mArray.size) {
            val startX = mYDistance + mBigDistance + mLineWidth / 2 + i * (mLineWidth  + mSmallDistance)
            val endY = mViewHeight - mXDistance - mArray[i] * (mViewHeight - 2 * mXDistance) / yMax

            if (onClickIndex == i) {
                canvas.drawLine(startX, mViewHeight - mXDistance, startX, endY, mPaintBlue)
            }else {
                canvas.drawLine(startX, mViewHeight - mXDistance, startX, endY, mPaint)
            }
            val text =  "${mArray[i]}"
            val textWidth = mPaint.measureText(text, 0, text.length)
            canvas.drawText(text, startX - textWidth / 2, endY - 15, mPaint)
        }
    }
    /**
     * 绘制坐标轴
     */
    private fun drawLineXY(canvas: Canvas) {
        canvas.drawLine(mYDistance, mViewHeight - mXDistance, mYDistance, 15f, mPaintTextXY)
        canvas.drawLine(mYDistance, mViewHeight - mXDistance, (mViewWidth - 15).toFloat(), mViewHeight - mXDistance, mPaintTextXY)

        if (mIsShowArrow) {
            //Y轴箭头
            canvas.drawLine(mYDistance, 15f, mYDistance - 10, 25f, mPaintTextXY)
            canvas.drawLine(mYDistance, 15f, mYDistance + 10, 25f, mPaintTextXY)
            //X轴箭头
            canvas.drawLine((mViewWidth - 15).toFloat(), mViewHeight - mXDistance, (mViewWidth - 25).toFloat(), mViewHeight - mXDistance - 10, mPaintTextXY);
            canvas.drawLine((mViewWidth - 15).toFloat(), mViewHeight - mXDistance, (mViewWidth - 25).toFloat(), mViewHeight - mXDistance + 10, mPaintTextXY);
        }
    }
    /**
     * 绘制X坐标值
     */
    private fun drawLineX(canvas: Canvas) {

        val xNum = 1 + (xMax * 0.25).toInt()

        val num = (xMax / xNum)
        for (i in 1 .. xNum ) {
            //绘制进度数字
            var text = "${(num * i)}"
            if (i == xNum)  text = "$xMax"

            //获取文字宽度
            val textWidth = mPaintTextXY.measureText(text, 0, text.length)
//            mYDistance + mBigDistance + mLineWidth / 2 + i * (mLineWidth  + mSmallDistance)
            val dx = mYDistance + mBigDistance  + mLineWidth / 2 + text.toInt() * ( mLineWidth + mSmallDistance ) - textWidth / 2
            val fontMetricsInt = mPaintTextXY.fontMetricsInt
            val dy = ((fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom).toFloat()
            val baseLine = mViewHeight - mXDistance / 2 + dy

            canvas.drawText(text, dx, baseLine, mPaintTextXY)
        }

    }

    /**
     * 绘制Y坐标值
     * 这里的坐标值是根据最大值计算出来对应的间隔，然后从0显示出6个数据
     */
    private fun drawLineY(canvas: Canvas) {

        for (i in 0..Y_NUMBER_NUM) {
            //绘制进度数字
            val text =  "${(yMax / Y_NUMBER_NUM) * i}"
            //获取文字宽度
            val textWidth = mPaintTextXY.measureText(text, 0, text.length)
            val dx = mYDistance / 2 - textWidth / 2
            val fontMetricsInt = mPaintTextXY.fontMetricsInt
            val dy = ((fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom).toFloat()
            val baseLine = mViewHeight - mXDistance - i * (mViewHeight - 2 * mXDistance) / Y_NUMBER_NUM + dy
            canvas.drawText(text, dx, baseLine, mPaintTextXY)

            if (mIsShowArrowYInterval)
                canvas.drawLine(mYDistance, mViewHeight - mXDistance - i * (mViewHeight - 2 * mXDistance) / Y_NUMBER_NUM, mYDistance + 10,
                        mViewHeight - mXDistance - i * (mViewHeight - 2 * mXDistance) / Y_NUMBER_NUM, mPaintTextXY)
        }
    }

    /**
     * sp转px
     */
    private fun sp2px(context: Context, spValue: Float): Int {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }

    /**
     * dp转px
     */
    private fun dip2px(context: Context, dpValue: Float): Float {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f)
    }

    private fun px2dip(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue / scale + 0.5f).toInt()
    }

    override fun toString(): String {
        return "LineChatView(TAG='$TAG', mContext=$mContext, mArray=$mArray, mYDistance=$mYDistance, mXDistance=$mXDistance, mBigDistance=$mBigDistance, mSmallDistance=$mSmallDistance, mLineWidth=$mLineWidth, mViewHeight=$mViewHeight, mViewWidth=$mViewWidth, mPaint=$mPaint, mPaintTextXY=$mPaintTextXY, yMax=$yMax, yMin=$yMin, xMax=$xMax, xMin=$xMin, mLeftLineBackGroundColor=$mLeftLineBackGroundColor, mRightLineBackGroundColor=$mRightLineBackGroundColor, mLeftLineTextColor=$mLeftLineTextColor, mRightLineTextColor=$mRightLineTextColor, mLineTextSize=$mLineTextSize, mLineXYColor=$mLineXYColor, mLineXYSize=$mLineXYSize, mIsShowArrow=$mIsShowArrow, mIsShowArrowYInterval=$mIsShowArrowYInterval, mAnimationDuration=$mAnimationDuration)"
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action){
            MotionEvent.ACTION_MOVE -> {}
            MotionEvent.ACTION_DOWN -> {
                // 计算横坐标的index
                Log.d(TAG, "(${event.x}, ${event.y})")
                // 减去左边空白，计算具体的 index
                onClickIndex = (((event.x -  mYDistance - mBigDistance ) / (mLineWidth  + mSmallDistance)) ).toInt()
                val y = event.y

                if(onClickIndex >= 0 && onClickIndex < mArray.size){
                    //点击后，获取坐标代表的单词的含义
                    Toast.makeText(mContext, "Array[$onClickIndex] = ${mArray[onClickIndex]}", Toast.LENGTH_SHORT).apply { show() }
                    viewList[onClickIndex].updateView(mPaintBlue)
                    return true
                }
            }

            // 点击事件更新 View
            MotionEvent.ACTION_UP -> {
                // 更新 view
                viewList[onClickIndex].updateView(mPaint)
                onClickIndex = -1
                return true
            }
            MotionEvent.ACTION_CANCEL -> {
                viewList[onClickIndex].updateView(mPaint)
                onClickIndex = -1
                return true
            }

        }
        //这句话不要修改
        return super.onTouchEvent(event)

    }
}
