package com.android.farmist.util

import android.content.Context
import android.graphics.Color
import cn.pedant.SweetAlert.SweetAlertDialog
import com.android.farmist.R

object SweetAlert {

    lateinit var pDialog:SweetAlertDialog
    public fun showDialog(context: Context) {
        pDialog= SweetAlertDialog(context,SweetAlertDialog.PROGRESS_TYPE)
        pDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
        pDialog.titleText =context.getString(R.string.loading)
        pDialog.setCancelable(false)
        pDialog.show()

    }
    public fun SuccessShowDialog(context: Context) {
        pDialog= SweetAlertDialog(context,SweetAlertDialog.SUCCESS_TYPE)
        pDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
        pDialog.show()


    }  public fun failedShowDialog(context: Context,errorStr:String) {
        pDialog= SweetAlertDialog(context,SweetAlertDialog.ERROR_TYPE)
        pDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
        pDialog.confirmButtonBackgroundColor=Color.RED
        pDialog.titleText=errorStr
        pDialog.show()


    }

    public fun hidediloag() {
        pDialog.dismiss()


    }
}