package com.hann.disasterguard.coreapp.utils

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.hann.disasterguard.coreapp.R

object Utils {


    fun showLoading(dialog : Dialog){
        dialog.setContentView(R.layout.alert_loading)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.show()
    }

    fun hideLoading(dialog : Dialog){
        dialog.dismiss()
    }
}