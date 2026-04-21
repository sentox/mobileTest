package com.sentox.mobiletest.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sentox.mobiletest.base.application.BaseApplication
import com.sentox.mobiletest.data.database.entity.BookingEntity
import com.sentox.mobiletest.data.database.entity.SegmentEntity

/**
 * 描述：room数据库单例类
 * 说明：
 * Created by Sentox
 * Created on 2026/4/21
 */
@Database(
    entities = [BookingEntity::class, SegmentEntity::class], version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        @Volatile
        private var INSTANSE: AppDatabase? = null

        fun getInstance(): AppDatabase {
            return INSTANSE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    BaseApplication.getAppContext(), AppDatabase::class.java, "mobile_test_db"
                ).build()
                INSTANSE = instance
                instance
            }
        }
    }

    abstract fun getBookingDao(): BookingDao
}