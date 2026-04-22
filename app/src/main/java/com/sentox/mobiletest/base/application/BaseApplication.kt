package com.sentox.mobiletest.base.application

import android.app.Application
import android.content.Context
import com.sentox.mobiletest.service.BookingService

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
        BookingService.init()//初始化模拟的请求层数据，实际应用中不需要
    }
}