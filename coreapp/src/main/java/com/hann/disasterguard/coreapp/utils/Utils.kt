package com.hann.disasterguard.coreapp.utils

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.hann.disasterguard.coreapp.R
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

object Utils {


    fun showLoading(dialog : Dialog){
        dialog.setContentView(R.layout.alert_loading)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.show()
    }

    fun hideLoading(dialog : Dialog){
        dialog.dismiss()
    }

    fun toastInfo(context: Activity, title : String, message: String){
        MotionToast.createToast(
            context,
            title,
            message,
            MotionToastStyle.INFO,
            MotionToast.GRAVITY_BOTTOM,
            MotionToast.SHORT_DURATION,
            null
        )
    }

    fun toastFailed(context: Activity,title : String, message: String){
        MotionToast.createToast(
            context,
            title,
            message,
            MotionToastStyle.ERROR,
            MotionToast.GRAVITY_BOTTOM,
            MotionToast.SHORT_DURATION,
            null
        )
    }
}