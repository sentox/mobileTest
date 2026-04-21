package com.sentox.mobiletest.home

import android.os.Bundle
import com.sentox.mobiletest.base.activity.BaseActivity
import com.sentox.mobiletest.data.json.BookingData
import com.sentox.mobiletest.databinding.ActivityHomeBinding
import com.sentox.mobiletest.service.BookingService

/**
 * 描述：测试首页
 * 说明：
 * Created by Sentox
 * Created on 2026/4/20
 */
class HomeActivity : BaseActivity<ActivityHomeBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BookingService
    }
}