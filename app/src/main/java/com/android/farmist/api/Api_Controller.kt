package com.android.farmist.api

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class Api_Controller {
    val BASE_URL = "https://myfarmapp1.herokuapp.com/api/v1/"
    val Admin_URl = "https://farmadmin.herokuapp.com/api/v2/"
    var retrofit: Retrofit

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    companion object {
        //    var retrofitAdmin: Retrofit

        val BASE_URL = "https://myfarmapp1.herokuapp.com/api/v1/"
        var Admin_URl = "https://farmadmin.herokuapp.com/api/v2/"
        private val retrofitAdmin = Retrofit.Builder()
            .baseUrl(Admin_URl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
        val apiInterface: ApiInterface = retrofitAdmin.create(ApiInterface::class.java)


        private val retrofit2 = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
        val apiInterface2: ApiInterface = retrofit2.create(ApiInterface::class.java)



    }

    fun getInstacne(): ApiInterface {
        return retrofit.create(ApiInterface::class.java)

    }

    fun getInstacneAdmin(): ApiInterface {
        return retrofitAdmin.create(ApiInterface::class.java)
    }

}