package com.android.farmist.api

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class Api_Controller {
    val BASE_URL = "https://myfarmapp1.herokuapp.com/api/v1/"
    var retrofit: Retrofit

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    companion object {
        val BASE_URL = "https://myfarmapp1.herokuapp.com/api/v1/"
        private val retrofitAdmin = Retrofit.Builder()
            .baseUrl(BASE_URL)
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

var burl = "https://2factor.in/API/V1/"
        private val OTP = Retrofit.Builder()
            .baseUrl(burl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
        val OtpInterFace:ApiInterface = OTP.create(ApiInterface::class.java)




    }

    fun getInstacne(): ApiInterface {
        return retrofit.create(ApiInterface::class.java)

    }

    fun getInstacneAdmin(): ApiInterface {
        return retrofitAdmin.create(ApiInterface::class.java)
    }

}
class Api_Controller_Location {
    var URL =
        "https://api.weatherapi.com/v1/forecast.json?key=13497f7823f3433ebb161306220202&q=B-16 Mahalaxmi Nagar " +
                "World Trade Park, D-Block, Malviya Nagar, Jaipur, Rajasthan 302017, India&days=3&aqi=no&alerts=no"
    var API = "13497f7823f3433ebb161306220202"

    var retrofit: Retrofit

    init {
        retrofit = Retrofit.Builder()
            .baseUrl("https://api.weatherapi.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getInstacne_Location(): ApiInterface {
        return retrofit.create(ApiInterface::class.java)

    }
}