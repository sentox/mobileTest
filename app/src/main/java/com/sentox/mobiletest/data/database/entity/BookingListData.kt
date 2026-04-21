package com.sentox.mobiletest.data.database.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.sentox.mobiletest.data.json.BookingData
import com.sentox.mobiletest.data.json.Location
import com.sentox.mobiletest.data.json.OriginAndDestinationData
import com.sentox.mobiletest.data.json.SegmentData

/**
 * 描述：用于查询booking主表和segment从表的
 * 说明：
 * Created by Sentox
 * Created on 2026/4/21
 */
data class BookingListData(

    @Embedded
    val booking: BookingEntity,

    @Relation(
        parentColumn = "shipReference",
        entityColumn = "shipReference"
    )
    val segments: List<SegmentEntity>
) {
    fun toBookingData(): BookingData {
        val list = arrayListOf<SegmentData>()
        for (data in segments) {
            data.apply {
                list.add(
                    SegmentData(
                        id, OriginAndDestinationData(
                            Location(destinationCode, destinationDisplayName, destinationUrl),
                            destinationCity,
                            Location(originCode, originDisplayName, originUrl),
                            originCity
                        )
                    )
                )
            }
        }
        return BookingData(
            booking.shipReference,
            booking.shipToken,
            booking.canIssueTicketChecking,
            booking.expiryTime,
            booking.duration,
            list
        )
    }
}
