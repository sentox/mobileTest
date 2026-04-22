package com.sentox.mobiletest.home

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.gson.Gson
import com.sentox.mobiletest.base.activity.BaseActivity
import com.sentox.mobiletest.data.json.BookingData
import com.sentox.mobiletest.databinding.ActivityHomeBinding
import com.sentox.mobiletest.service.BookingService
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.observeOn
import kotlinx.coroutines.launch

/**
 * 描述：测试首页
 * 说明：
 * Created by Sentox
 * Created on 2026/4/20
 */
class HomeActivity : BaseActivity<ActivityHomeBinding>() {

    companion object {
        private const val TAG = "HomeActivity"
    }

    private val mViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mViewModel.mListFlow.collect {
                    //展示数据
                    val gson = Gson()
                    val builder = StringBuilder()
                    it.forEach { data ->
                        builder.append(gson.toJson(data))
                        builder.append("\n\n\n\n")
                    }
                    binding?.mTvTest?.text = builder.toString()


                }
            }
        }
    }
}