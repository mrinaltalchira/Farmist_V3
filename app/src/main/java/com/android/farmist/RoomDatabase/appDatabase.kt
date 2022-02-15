package com.android.farmist.RoomDatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.farmist.model.alertsResponse.New

@Database(entities = [New::class],version = 1)
abstract class appDatabase:RoomDatabase() {
    abstract fun  newsDao():NewsDao
}