package com.android.farmist.RoomDatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.farmist.model.alertsResponse.GetNewsAlert
import com.android.farmist.model.alertsResponse.New

@Dao
interface NewsDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg getNewsAlert: List<New>)

    @Query("SELECT * FROM new")
    fun getnews():List<New>



}