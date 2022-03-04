package com.android.farmist.RoomDatabase

import android.content.Context
import androidx.annotation.NonNull
import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.farmist.model.alertsResponse.New
import androidx.room.Room
import androidx.sqlite.db.SupportSQLiteDatabase
import android.os.AsyncTask
import com.android.farmist.model.CropPriceResponse.Crop
import com.android.farmist.model.CropPriceResponse.Data
import com.android.farmist.model.alertsResponse.Scheme
import com.android.farmist.model.location.Roomdata


@Database(entities = [New::class,Crop::class,Data::class, Scheme::class,Roomdata::class], version = 10, exportSchema = false)
abstract class appDatabase : RoomDatabase() {
    abstract fun getAppDao(): NewsDao

    companion object {
        private var DB_INSTANCE: appDatabase?= null

        fun getAppDBInstance(context: Context): appDatabase {
            if (DB_INSTANCE == null) {
                DB_INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    appDatabase::class.java,
                    "APP_DB"
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return DB_INSTANCE!!
        }
    }

}