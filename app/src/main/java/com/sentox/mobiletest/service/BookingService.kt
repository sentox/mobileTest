package com.sentox.mobiletest.service

import android.media.audiofx.DynamicsProcessing.Mbc
import com.google.gson.Gson
import com.sentox.mobiletest.base.log.L
import com.sentox.mobiletest.data.database.AppDatabase
import com.sentox.mobiletest.data.json.BookingData
import com.sentox.mobiletest.data.json.BookingListResponse
import com.sentox.mobiletest.data.json.Location
import com.sentox.mobiletest.data.json.OriginAndDestinationData
import com.sentox.mobiletest.data.json.SegmentData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

/**
 * 描述：虚拟的订单请求服务
 * 说明：
 * Created by Sentox
 * Created on 2026/4/21
 */
object BookingService {


    private const val TAG = "BookingService"

    /**
     *  虚拟的在线订单列表
     * **/
    val mBookingDataList = arrayListOf<BookingData>()

    /**
     *  虚拟数据
     * **/
    val mRandomLocationList =
        arrayListOf(
            Location("JFK", "New York"),
            Location("LAX", "Los Angeles"),
            Location("PEK", "BeiJing"),
            Location("CAN", "GuangZhou"),
            Location("SHA", "ShangHai"),
            Location("HKG", "HongKong")
        )

    val mDb = AppDatabase.getInstance()
    val mDao = mDb.getBookingDao()

    init {
        //service初始化，读取本地数据库订单并放入虚拟缓存的在线订单列表
        mBookingDataList.clear()
        CoroutineScope(Dispatchers.IO).launch {
            val gson = Gson()
            var dbList = mDao.getAllBookingListData()
            L.info(TAG, "展示数据库数据")
            if (dbList.isNotEmpty()) {
                for (data in dbList) {
                    if (data.booking.expiryTime * 1000 > System.currentTimeMillis()) {
                        //没有超时
                        mBookingDataList.add(data.toBookingData())
                        L.info(TAG, "未过期：" + gson.toJson(data))
                    } else {
                        //数据已超时，但不删除，因为此处是借助数据库获取过去的数据模拟服务端下发，
                        //真正的同步应该放在初始化界面或者拉取服务端数据更新后（取决于需求）
                        L.info(TAG, "已过期：" + gson.toJson(data))
                    }
                }
            }
            if (mBookingDataList.isEmpty()) {
                //没有未过期数据，自动创建两条
                val data1 = createBookingData()
                mBookingDataList.add(data1)
                mDao.insertOrUpdateBookListData(data1.toEntityData())
                delay(100)
                val data2 = createBookingData()
                mBookingDataList.add(data2)
                mDao.insertOrUpdateBookListData(data2.toEntityData())
            }
            L.info(TAG, "展示虚拟服务端数据")
            for (data in mBookingDataList) {
                L.info(TAG, gson.toJson(data))
            }
        }


    }

    /**
     *  创建一个新的订单信息
     * **/
    fun createBookingData(): BookingData {
        val segments = arrayListOf<SegmentData>()
        //随机生成航段
        val locationSize = mRandomLocationList.size
        val startPos = Random.nextInt(locationSize)
        for (i in 0 until Random.nextInt(1, locationSize - 1)) {
            val fromLocation = mRandomLocationList[(startPos + i) % locationSize]
            val toLocation = mRandomLocationList[(startPos + i + 1) % locationSize]
            segments.add(
                SegmentData(
                    i,
                    OriginAndDestinationData(
                        toLocation, toLocation.displayName,
                        fromLocation, fromLocation.displayName
                    )
                )
            )

        }
        //由于是虚拟数据，因此只采用当前系统时间作为主键，并且token也使用这个数据，如果真实情况下需要服务端生成唯一的订单号和token
        val shipReference = System.currentTimeMillis().toString()
        return BookingData(
            shipReference,
            shipReference,
            true,
            //过期时间是当前创建时间的2分钟后
            System.currentTimeMillis() / 1000L + 2 * 60L,
            Random.nextInt(20, 50) * 60L,
            segments
        )
    }
}