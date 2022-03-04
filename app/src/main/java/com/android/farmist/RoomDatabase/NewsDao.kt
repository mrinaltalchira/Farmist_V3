package com.android.farmist.RoomDatabase

import androidx.lifecycle.LiveData
import androidx.room.*
import com.android.farmist.model.CropPriceResponse.Crop
import com.android.farmist.model.CropPriceResponse.Data
import com.android.farmist.model.alertsResponse.GetNewsAlert
import com.android.farmist.model.alertsResponse.New
import com.android.farmist.model.alertsResponse.Scheme
import com.android.farmist.model.location.Roomdata
import com.android.farmist.model.getUserInfo.getUserModel2
import com.android.farmist.model.profileImgResponse.LatestPic

@Dao
interface NewsDao {

    @Query("SELECT * FROM News")
    fun getnews():LiveData<List<New>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( getNewsAlert:List<New>)

    @Query("DELETE FROM News")
    fun deleteAllRecords()

// mrinal ji ke dwara bnaya gya price DB

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPrice(cropprice:List<Crop>)

    @Query("Select * from CropPrice")
    fun gelAllPrice(): LiveData<List<Crop>>

    @Query("DELETE FROM CropPrice")
    fun deleteAllPrice()

    // PriceFragment by mrinall ji
    //account Fragment
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAccountInfo(latestPic: List<LatestPic>)

    @Query("SELECT *From `user account info`")
    fun getAccountInfo():LiveData<List<LatestPic>>

    @Query("DELETE FROM `user Account Info`")
    fun deeletAccountInfo()

    //Personal Information
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPersonalInfo(getUserModel2: getUserModel2)

    @Query("SELECT *From personalInfo")
    fun getPersonalInfo():LiveData<getUserModel2>

    @Query("DELETE FROM personalInfo")
    fun deletPersonalInfo()





    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMyPrice(cropprice:List<Data>)

    @Query("Select * from MyCropPrice")
    fun getAllMyPrice(): LiveData<List<Data>>

    @Query("DELETE FROM MyCropPrice")
    fun deleteAllmyPrice()

    //gov scheme

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGov(cropprice:List<Scheme>)

    @Query("Select * from GovScheme")
    fun getGov(): LiveData<List<Scheme>>

    @Query("DELETE FROM GovScheme")
    fun deleteGov()

    // locatiopn

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLocation(cropprice:Roomdata)

    @Query("Select * from weather")
    fun getLocation(): LiveData<Roomdata>

    @Query("DELETE FROM weather")
    fun deleteLocation()


}