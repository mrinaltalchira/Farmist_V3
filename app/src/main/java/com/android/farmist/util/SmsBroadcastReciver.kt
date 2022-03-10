package com.android.farmist.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status

class SmsBroadcastReciver:BroadcastReceiver() {

    var smsBroadcastReciverListner: SmsBroadcastReciverListner?= null
    override fun onReceive(context: Context?, intent: Intent?) {
        if (SmsRetriever.SMS_RETRIEVED_ACTION == intent?.action){

            val extra = intent.extras
            val smsRetriveStatus = extra?.get(SmsRetriever.EXTRA_STATUS)as Status

            when(smsRetriveStatus.statusCode){
                CommonStatusCodes.SUCCESS -> {val messageIntent = extra.getParcelable<Intent>(SmsRetriever.EXTRA_CONSENT_INTENT)
                smsBroadcastReciverListner?.onSuccedd(messageIntent)}
                CommonStatusCodes.TIMEOUT -> {
                    smsBroadcastReciverListner?.onFailure()
                }
            }
        }
    }

    interface SmsBroadcastReciverListner{
        fun onSuccedd(intent: Intent?)
        fun onFailure()

    }
}