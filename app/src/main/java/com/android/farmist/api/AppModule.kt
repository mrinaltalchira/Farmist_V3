package com.android.farmist.api

import android.app.Application
import android.content.Context
import com.android.farmist.RoomDatabase.NewsDao
import com.android.farmist.RoomDatabase.appDatabase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


//@Module
//@InstallIn(SingletonComponent::class)
class AppModule {

//    @Provides
//    @Singleton
    fun getAppDatabase(context: Application): appDatabase {
        return appDatabase.getAppDBInstance(context)
    }

//    @Provides
//    @Singleton
    fun getAppDao(appDatabase: appDatabase): NewsDao {
        return appDatabase.getAppDao()
    }

    val BASE_URL = "https://api.github.com/search/"
    val BASE_URL2 = "https://myfarmapp1.herokuapp.com/api/v1/"

//    @Provides
//    @Singleton
    fun getRetroServiceInstance(retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }

//    @Provides
//    @Singleton
    fun getRetroInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL2)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}