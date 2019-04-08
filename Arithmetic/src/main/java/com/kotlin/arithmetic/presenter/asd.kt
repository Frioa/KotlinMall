package com.kotlin.arithmetic.presenter

import java.util.concurrent.ThreadFactory
import java.util.concurrent.atomic.AtomicInteger

object asd {
    private val s = object : ThreadFactory {
        private val mCount = AtomicInteger(1)
        override fun newThread(r: Runnable): Thread? {
            return null
        }
    }
}
