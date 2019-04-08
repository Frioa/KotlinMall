package com.kotlin.base.widgets

import android.text.Editable
import android.text.TextWatcher
/*
    默认 TextWatcher，空实现
 */
open class DefaultTextWatcher: TextWatcher{
    override fun afterTextChanged(p0: Editable?) {
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

}