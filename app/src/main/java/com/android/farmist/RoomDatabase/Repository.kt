package com.android.farmist.RoomDatabase

import androidx.lifecycle.LiveData
import android.app.Application
import com.android.farmist.model.alertsResponse.New
import android.os.AsyncTask
import com.android.farmist.api.ApiInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Repository{



    fun getAppDatabase(context: Application): appDatabase {
        return appDatabase.getAppDBInstance(context)
    }


    fun getAppDao(appDatabase: appDatabase): NewsDao {
        return appDatabase.getAppDao()
    }

    val BASE_URL = "https://api.github.com/search/"
    val BASE_URL2 = "https://myfarmapp1.herokuapp.com/api/v1/"


    fun getRetroServiceInstance(retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }


    fun getRetroInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL2)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}