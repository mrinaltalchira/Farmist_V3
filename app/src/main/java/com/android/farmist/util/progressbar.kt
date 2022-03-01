package com.android.farmist.util

import android.content.Context
import android.graphics.Color
import androidx.core.content.ContentProviderCompat.requireContext
import cn.pedant.SweetAlert.SweetAlertDialog
import com.android.farmist.activity.MainActivity
import kotlin.coroutines.coroutineContext

open class progressbars(context: Context) {

    val pDialog = SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE)

    public fun showDialog() {
        pDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
        pDialog.titleText = "Loading"
        pDialog.setCancelable(false)
        pDialog.show()

    }

    public fun hidediloag() {
        pDialog.dismiss()


    }
}