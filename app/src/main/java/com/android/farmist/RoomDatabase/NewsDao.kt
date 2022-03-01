package com.android.farmist.RoomDatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.farmist.model.alertsResponse.GetNewsAlert
import com.android.farmist.model.alertsResponse.New

@Dao
interface NewsDao {

    @Query("SELECT * FROM News")
    fun getnews():LiveData<List<New>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( getNewsAlert:List<New>)

    @Query("DELETE FROM News")
    fun deleteAllRecords()





}