package com.android.farmist.util

import android.content.Context
import android.graphics.Color
import cn.pedant.SweetAlert.SweetAlertDialog

object SweetAlert {

    lateinit var pDialog:SweetAlertDialog
    public fun showDialog(context: Context) {
        pDialog= SweetAlertDialog(context,SweetAlertDialog.PROGRESS_TYPE)
        pDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
        pDialog.titleText = "Loading"
        pDialog.setCancelable(false)
        pDialog.show()

    }
    public fun SuccessShowDialog(context: Context) {
        pDialog= SweetAlertDialog(context,SweetAlertDialog.SUCCESS_TYPE)
        pDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
        pDialog.show()


    }

    public fun hidediloag() {
        pDialog.dismiss()


    }
}