package com.sentox.mobiletest.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import com.sentox.mobiletest.base.activity.BaseActivity
import com.sentox.mobiletest.base.log.L
import com.sentox.mobiletest.data.database.entity.BookingListData
import com.sentox.mobiletest.databinding.ActivityHomeBinding
import com.sentox.mobiletest.service.BookingService
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
    private var mAdapter = BookingListAdapter()
    private var mLayoutManager: LinearLayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUI()
        initLifecycleScope()
    }

    private fun initLifecycleScope() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                //正常页面启动要拉取一次服务端数据，这里为了展示本地缓存代码，不作更新
//                mViewModel.getNewBookingData(BookingService.SIMULATION_SUCCESS_NO_NEW_DATA)
                launch {
                    //监听列表数据更新
                    mViewModel.mListFlow.collect {
                        binding?.apply {
                            if (it.isEmpty()) {
                                mRefreshLayout.visibility = View.GONE
                                mTvEmpty.visibility = View.VISIBLE
                            } else {
                                mRefreshLayout.visibility = View.VISIBLE
                                mTvEmpty.visibility = View.GONE
                                //展示数据
                                logListData(it)
                                mAdapter.setList(it)
                            }
                        }
                    }
                }

                launch {
                    //监听请求状态更新
                    mViewModel.mReqFlow.collect {
                        binding?.apply {
                            when (it) {
                                HomeViewModel.REQ_STATUS_NO_REQ -> {
                                    //没请求，do nothing
                                    mRefreshLayout.finishRefresh()
                                }

                                HomeViewModel.REQ_STATUS_ING -> {
                                    //展示加载动画（如果有）
                                }

                                HomeViewModel.REQ_STATUS_SUCCESS -> {
                                    //请求成功，结束加载动画
                                    mRefreshLayout.finishRefresh()
                                    if (mViewModel.mListFlow.value.isEmpty()) {
                                        mRefreshLayout.visibility = View.GONE
                                        mTvEmpty.visibility = View.VISIBLE
                                    } else {
                                        mRefreshLayout.visibility = View.VISIBLE
                                        mTvEmpty.visibility = View.GONE
                                    }
                                }

                                else -> {
                                    mRefreshLayout.finishRefresh()
                                    if (mAdapter.data.isEmpty()) {
                                        mRefreshLayout.visibility = View.GONE
                                        mTvEmpty.visibility = View.VISIBLE
                                    } else {
                                        mRefreshLayout.visibility = View.VISIBLE
                                        mTvEmpty.visibility = View.GONE
                                    }
                                    //请求出错，根据错误码处理对应问题
                                    if (it == 443) {
                                        Toast.makeText(this@HomeActivity, "网络错误：443", Toast.LENGTH_LONG).show()
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
    }

    private fun logListData(list: List<BookingListData>) {
        if (list.isEmpty()) {
            return
        }
        //打印数据
        val gson = Gson()
        val builder = StringBuilder()
        list.forEach { data ->
            builder.append(gson.toJson(data))
            builder.append("\n\n\n\n")
        }
        L.info(TAG, "打印列表展示数据：\n${builder}")
    }

    private fun initUI() {
        binding?.apply {
            mLayoutManager = LinearLayoutManager(this@HomeActivity, LinearLayoutManager.VERTICAL, false)
            mRvList.layoutManager = mLayoutManager

            mRefreshLayout.apply {
                setEnableRefresh(true)
                setRefreshHeader(ClassicsHeader(this@HomeActivity))
                setEnableFooterFollowWhenNoMoreData(false)
                setEnableLoadMore(false)
            }
            mRvList.adapter = mAdapter
            mAdapter.setList(arrayListOf())
            mRefreshLayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
                override fun onRefresh(refreshLayout: RefreshLayout) {
                    mViewModel.getNewBookingData(BookingService.SIMULATION_SUCCESS_NO_NEW_DATA)
                }

                override fun onLoadMore(refreshLayout: RefreshLayout) {
                    //如果需要分页加载逻辑在这里处理
                }
            })
            mTvEmpty.setOnClickListener {
                //点击刷新，这里使用的是添加两条新数据的请求
                mViewModel.getNewBookingData(BookingService.SIMULATION_SUCCESS_HAS_NEW_DATA)
            }
            mBtnGetNew.setOnClickListener {
                //点击获取新数据，这里使用的是添加两条新数据的请求
                mViewModel.getNewBookingData(BookingService.SIMULATION_SUCCESS_HAS_NEW_DATA)
            }
            mBtnUpdate.setOnClickListener {
                //点击更新，这里只将将原有数据的canIssueTicketChecking改变模拟数据更新
                mViewModel.getNewBookingData(BookingService.SIMULATION_SUCCESS_NO_NEW_DATA)
            }

            mBtnNetworkError.setOnClickListener {
                //点击模拟网络错误
                mViewModel.getNewBookingData(BookingService.SIMULATION_FAILED)
            }

        }
    }
}