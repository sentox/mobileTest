package com.sentox.mobiletest.home

import android.view.View
import android.widget.Toast
import androidx.appcompat.view.menu.MenuAdapter
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sentox.mobiletest.base.application.BaseApplication
import com.sentox.mobiletest.base.log.L
import com.sentox.mobiletest.data.database.DataProvider
import com.sentox.mobiletest.data.database.entity.BookingListData
import com.sentox.mobiletest.data.json.BookingData
import com.sentox.mobiletest.service.BookingService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/**
 * 描述：首页的viewmodel
 * 说明：
 * Created by Sentox
 * Created on 2026/4/22
 */
class HomeViewModel : ViewModel() {

    companion object {
        private const val TAG = "HomeViewModel"
        const val REQ_STATUS_NO_REQ = -1
        const val REQ_STATUS_ING = 0
        const val REQ_STATUS_SUCCESS = 200
    }

    private val mDataListStateFlow = MutableStateFlow<ArrayList<BookingListData>>(arrayListOf())
    var mListFlow: StateFlow<ArrayList<BookingListData>> = mDataListStateFlow.asStateFlow()
    private val mRequestStateFlow = MutableStateFlow<Int>(REQ_STATUS_NO_REQ)
    var mReqFlow: StateFlow<Int> = mRequestStateFlow.asStateFlow()

    init {
        getLocalData()
    }

    /**
     *  获取本地数据库数据
     * **/
    private fun getLocalData() {
        viewModelScope.launch {
            DataProvider.getAllBookingListData().collect {
                mDataListStateFlow.value = ArrayList(it)
            }
        }

    }

    /**
     *  模拟请求接口
     * **/
    fun getNewBookingData(testCode: Int) {
        mRequestStateFlow.value = REQ_STATUS_ING
        BookingService.getBookingData(testCode)
            .flowOn(Dispatchers.IO)
            .onEach {
                if (it.isSuccess) {
                    //请求成功
                    compareAndSaveData(it.data)
                    mRequestStateFlow.value = REQ_STATUS_SUCCESS
                } else {
                    //请求失败
                    mRequestStateFlow.value = it.rspCode
                    when (it.rspCode) {
                        443 -> {
                            //建立连接时发生错误
                        }

                        500501 -> {
                            //todo 服务端约定的其他错误码，对应处理，通常会打印错误信息的msg
                            //举例 token校验失败需要重新登录的，当然，这可能在请求的框架层就做退出处理了，不会留到这里
                        }
                    }
                    L.info(TAG, it.msg)
                    mRequestStateFlow.value = REQ_STATUS_NO_REQ
                }
            }
            .launchIn(viewModelScope)
    }

    /**
     *  同步接口获取数据和本地数据
     * **/
    private suspend fun compareAndSaveData(list: ArrayList<BookingData>) {
        if (list.isEmpty()) {
            //服务端已经没有任何在有效期内的数据，清空数据库
            mDataListStateFlow.value.forEach {
                DataProvider.deleteBookingListData(it.booking.shipReference)
            }
            mDataListStateFlow.value = arrayListOf()
        } else {
            val map = list.associateBy { it.shipReference }
            val result = arrayListOf<BookingListData>()
            map.forEach {
                val data = it.value.toEntityData()
                result.add(data)
                //插入或更新服务端下发的数据
                DataProvider.insertOrUpdateBookListData(data)
            }
            for (data in mDataListStateFlow.value) {
                if (map[data.booking.shipReference] == null) {
                    //从数据库删除服务端已经没有的数据
                    DataProvider.deleteBookingListData(data.booking.shipReference)
                }
            }
            mDataListStateFlow.value = result
        }
    }
}