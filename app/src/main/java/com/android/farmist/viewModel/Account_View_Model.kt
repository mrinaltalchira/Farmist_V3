package com.android.farmist.viewModel

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModel
import com.android.farmist.api.Api_Controller
import com.android.farmist.model.profileImgResponse.GetUserImagResponse
import com.bumptech.glide.Glide
import com.mikhaellopez.circularimageview.CircularImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Account_View_Model:ViewModel() {
    var imageUrl: String = ""
    var nameStr: String = ""
    lateinit var userId: String
    lateinit var preferences: SharedPreferences

    public fun getprofile(context: Context) {

        preferences =
            context.getSharedPreferences("userMassage", Context.MODE_PRIVATE)
        userId = preferences.getString("message", "").toString()

        val call: Call<GetUserImagResponse>
        call = Api_Controller().getInstacne().getUserProfile(userId)
        call.enqueue(object : Callback<GetUserImagResponse> {
            override fun onResponse(
                call: Call<GetUserImagResponse>,
                response: Response<GetUserImagResponse>
            ) {
                Toast.makeText(context, imageUrl, Toast.LENGTH_SHORT).show()


                context?.let {

                    if (response != null) {
                        val data = response.body()
                        if (data != null) {
                            if (data.latestPic.size != 0)
                            {
                                imageUrl = data.latestPic[0].image

                            }
                           // nameStr = data.name
                        }
                    }
                }

            }

            override fun onFailure(call: Call<GetUserImagResponse>, t: Throwable) {
                Log.d("getProfileError", t.toString())
            }
        })

    }
}