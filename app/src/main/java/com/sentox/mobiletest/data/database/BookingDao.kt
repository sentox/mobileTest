package com.sentox.mobiletest.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.sentox.mobiletest.base.log.L
import com.sentox.mobiletest.data.database.entity.BookingEntity
import com.sentox.mobiletest.data.database.entity.BookingListData
import com.sentox.mobiletest.data.database.entity.SegmentEntity

/**
 * 描述：数据库操作
 * 说明：
 * Created by Sentox
 * Created on 2026/4/21
 */
@Dao
interface BookingDao {

    //================= BookingEntity操作 ===================

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateBookingEntity(booking: BookingEntity): Long

    @Query("DELETE FROM bookingentity WHERE shipReference = :shipReference")
    suspend fun deleteBookingByReference(shipReference: String)

    //================= SegmentEntity操作 ===================

    @Insert
    suspend fun insertSegments(segments: List<SegmentEntity>)

    @Query("DELETE FROM segmententity WHERE shipReference = :shipReference")
    suspend fun deleteSegmentsByShipReference(shipReference: String)

    //================= 统一操作 ===================

    /**
     *  获取整个bookingListData列表
     * **/
    @Transaction
    @Query("SELECT * FROM bookingentity ORDER BY expiryTime DESC")
    suspend fun getAllBookingListData(): List<BookingListData>

    /**
     *  插入或更新单个数据
     * **/
    @Transaction
    suspend fun insertOrUpdateBookListData(bookingListData: BookingListData) {
        val result = insertOrUpdateBookingEntity(bookingListData.booking)
        L.info("zhr", "result=$result")
        deleteSegmentsByShipReference(bookingListData.booking.shipReference)
        if (bookingListData.segments.isNotEmpty()) {
            insertSegments(bookingListData.segments)
        }
    }

    /**
     *  删除数据
     * **/
    @Transaction
    suspend fun deleteBookingListData(shipReference: String) {
        deleteBookingByReference(shipReference)
    }


}