package com.android.farmist.api

import androidx.lifecycle.LiveData
import com.android.farmist.RoomDatabase.NewsDao
import com.android.farmist.model.alertsResponse.GetNewsAlert
import com.android.farmist.model.alertsResponse.New
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RetroRepository  constructor(private val retroServiceInterface: ApiInterface,
                                           private val appDao: NewsDao) {

    fun getAllRecords(): LiveData<List<New>> {
        return appDao.getnews()
    }

    fun insertRecord(repositoryData: List<New>) {
        appDao.insertAll(repositoryData)
    }


    //get the data from github api...
    fun makeApiCall() {

        val call: Call<GetNewsAlert> = retroServiceInterface.getNewsAlertRoomDb()
        call?.enqueue(object : Callback<GetNewsAlert>{
            override fun onResponse(
                call: Call<GetNewsAlert>,
                response: Response<GetNewsAlert>
            ) {
                if(response.isSuccessful) {
                    val responseData=response.body()!!.news
                    appDao.deleteAllRecords()
                    insertRecord(responseData)

//                    response.body()?.news?.forEach {
//                        insertRecord(it)
//                    }
                }
            }

            override fun onFailure(call: Call<GetNewsAlert>, t: Throwable) {
                //
            }
        })
    }
}