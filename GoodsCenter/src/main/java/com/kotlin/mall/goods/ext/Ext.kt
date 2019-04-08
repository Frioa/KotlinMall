package com.kotlin.mall.goods.ext

import android.widget.EditText
import com.kotlin.mall.goods.R
import org.jetbrains.anko.find
import ren.qinc.numberbutton.NumberButton

fun NumberButton.getEditText(): EditText{
    return find(R.id.text_count)    //获得 NumberButton 私有成员
}