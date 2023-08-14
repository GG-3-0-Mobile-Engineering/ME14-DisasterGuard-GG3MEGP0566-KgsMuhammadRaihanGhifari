package com.hann.disasterguard.coreapp.utils

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
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

    fun getRegionCode(region: String): String {
        return when (region) {
            "Aceh" -> "ID-AC"
            "Bali" -> "ID-BA"
            "Kep Bangka Belitung" -> "ID-BB"
            "Banten" -> "ID-BT"
            "Bengkulu" -> "ID-BE"
            "Jawa Tengah" -> "ID-JT"
            "Kalimantan Tengah" -> "ID-KT"
            "Sulawesi Tengah" -> "ID-ST"
            "Jawa Timur" -> "ID-JI"
            "Kalimantan Timur" -> "ID-KI"
            "Nusa Tenggara Timur" -> "ID-NT"
            "Gorontalo" -> "ID-GO"
            "DKI Jakarta" -> "ID-JK"
            "Jambi" -> "ID-JA"
            "Lampung" -> "ID-LA"
            "Maluku" -> "ID-MA"
            "Kalimantan Utara" -> "ID-KU"
            "Maluku Utara" -> "ID-MU"
            "Sulawesi Utara" -> "ID-SA"
            "Sumatera Utara" -> "ID-SU"
            "Papua" -> "ID-PA"
            "Riau" -> "ID-RI"
            "Kep Riau" -> "ID-KR"
            "Sulawesi Tenggara" -> "ID-SG"
            "Kalimantan Selatan" -> "ID-KS"
            "Sulawesi Selatan" -> "ID-SN"
            "Sumatera Selatan" -> "ID-SS"
            "DI Yogyakarta" -> "ID-YO"
            "Jawa Barat" -> "ID-JB"
            "Kalimantan Barat" -> "ID-KB"
            "Nusa Tenggara Barat" -> "ID-NB"
            "Papua Barat" -> "ID-PB"
            "Sulawesi Barat" -> "ID-SR"
            "Sumatera Barat" -> "ID-SB"
            else -> "ID-JK"
        }
    }

    fun getCategoryDisaster(disasterType: String): Int {
        return when (disasterType) {
            "flood" -> R.drawable.flood
            "earthquake" -> R.drawable.earthquake
            "fire" -> R.drawable.fire
            "haze" ->R.drawable.haze
            "wind" ->R.drawable.wind
            "volcano" ->R.drawable.volcano
            else -> R.drawable.flood
        }
    }

    fun getTypeDisaster(disasterType: String, view: View): Drawable? {
        return when (disasterType) {
            "flood" -> ContextCompat.getDrawable(view.context, R.color.azure)
            "earthquake" -> ContextCompat.getDrawable(view.context, R.color.orange)
            "fire" -> ContextCompat.getDrawable(view.context, R.color.red)
            "haze" -> ContextCompat.getDrawable(view.context, R.color.violet)
            "wind" -> ContextCompat.getDrawable(view.context, R.color.cyan)
            "volcano" -> ContextCompat.getDrawable(view.context, R.color.yellow)
            else -> ContextCompat.getDrawable(view.context, R.color.black)
        }
    }
}