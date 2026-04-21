package com.sentox.mobiletest.service

import android.media.audiofx.DynamicsProcessing.Mbc
import com.sentox.mobiletest.base.log.L
import com.sentox.mobiletest.data.json.BookingData
import com.sentox.mobiletest.data.json.BookingListResponse
import com.sentox.mobiletest.data.json.Location
import com.sentox.mobiletest.data.json.OriginAndDestinationData
import com.sentox.mobiletest.data.json.SegmentData
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

    init {
        //service初始化，读取本地数据库订单并放入虚拟缓存的在线订单列表
        mBookingDataList.add(createBookingData())
        for(data in mBookingDataList){
            L.info(TAG, data)
        }
        val response = BookingListResponse(mBookingDataList)
        L.info(TAG, "response=${response.toJson()}")

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
            System.currentTimeMillis() / (1000L * 1000L) + 2 * 60L,
            Random.nextInt(20, 50) * 60L,
            segments
        )
    }
}