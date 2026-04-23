package com.sentox.mobiletest.data.json

import com.sentox.mobiletest.data.database.entity.BookingEntity
import com.sentox.mobiletest.data.database.entity.BookingListData
import com.sentox.mobiletest.data.database.entity.SegmentEntity

/**
 * 描述：Booking订单数据
 * 说明：用于json转换
 * Created by Sentox
 * Created on 2026/4/21
 */
data class BookingData(
    /**
     *  订单编号
     * **/
    var shipReference: String,
    /**
     *  订单token
     * **/
    var shipToken: String,
    /**
     *  是否可以出票
     * **/
    var canIssueTicketChecking: Boolean,
    /**
     *  过期时间，单位：秒
     * **/
    var expiryTime: Long,
    /**
     *  飞行总时间
     * **/
    var duration: Long,
    /**
     *  航段信息
     * **/
    val segments: ArrayList<SegmentData>

) {
    fun toEntityData(): BookingListData {
        val booking = BookingEntity(shipReference, shipToken, canIssueTicketChecking, expiryTime, duration)
        val segmentList = arrayListOf<SegmentEntity>()
        for (data in segments) {
            data.originAndDestinationPair.apply {
                segmentList.add(
                    SegmentEntity(
                        0,
                        shipReference,
                        data.id,
                        origin.code,
                        origin.displayName,
                        origin.url,
                        originCity,
                        destination.code,
                        destination.displayName,
                        destination.url,
                        destinationCity
                    )
                )
            }

        }
        return BookingListData(booking, segmentList)

    }
}
