package com.android.farmist.viewModel

import androidx.lifecycle.LiveData

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import com.android.farmist.RoomDatabase.appDatabase
import com.android.farmist.adapter.Adapter_Alerts_News
import com.android.farmist.api.Api_Controller
import com.android.farmist.model.alertsResponse.GetNewsAlert
import com.android.farmist.model.alertsResponse.New
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//@HiltViewModel
public class NewsViewModel  (context: Context)
    :ViewModel() {
    val context=context
//    lateinit var repository: RetroRepository
//
//    fun getAllRepositoryList(): LiveData<List<New>> {
//        return repository.getAllRecords()
//    }
//
//    fun makeApiCall() {
//        repository.makeApiCall()
//    }
//lateinit var appDatabaseObj:appDatabase

    val appDatabaseObj=com.android.farmist.RoomDatabase.appDatabase
    fun getAllRecords(): LiveData<List<New>> {
//        appDatabase.getAppDBInstance(context).getAppDao()

        val newsdao=appDatabaseObj.getAppDBInstance(context).getAppDao().getnews()
        return newsdao
    }

    fun insertRecord(repositoryData: List<New>) {
        val inserData=appDatabaseObj.getAppDBInstance(context).getAppDao().insertAll(repositoryData)

    }
        fun makeApiCall()
    {
        val call: Call<GetNewsAlert>
        call= Api_Controller().getInstacneAdmin().getNewsAlert()
        call.enqueue(object : Callback<GetNewsAlert> {
            override fun onResponse(call: Call<GetNewsAlert>, response: Response<GetNewsAlert>) {

                if(response.isSuccessful) {
                    val responseData=response.body()!!.news
                    val inserData=appDatabaseObj.getAppDBInstance(context).getAppDao().deleteAllRecords()
                    insertRecord(responseData)

//                    response.body()?.news?.forEach {
//                        insertRecord(it)
//                    }
                }
            }

            override fun onFailure(call: Call<GetNewsAlert>, t: Throwable) {
                Log.d("getNewsError",t.toString())
            }
        })
        }




}