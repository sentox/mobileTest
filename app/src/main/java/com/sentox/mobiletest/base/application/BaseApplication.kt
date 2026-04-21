package com.sentox.mobiletest.base.application

import android.app.Application
import android.content.Context

/**
 * 描述：自定义application
 * 说明：
 * Created by Sentox
 * Created on 2026/4/21
 */
class BaseApplication : Application() {
    companion object {
        private lateinit var sInstance: Context
        fun getAppContext() = sInstance
    }

    override fun onCreate() {
        super.onCreate()
        sInstance = this
    }
}