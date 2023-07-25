package com.hann.disasterguard.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.hann.disasterguard.R
import com.hann.disasterguard.coreapp.utils.NOTIFICATION_CHANNEL_ID
import com.hann.disasterguard.coreapp.utils.TypeConverterEntity
import com.hann.disasterguard.presentation.main.MainActivity


class NotificationWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {

    private val dataFlood = inputData.getString(NOTIFICATION_CHANNEL_ID)

    private fun getPendingIntent(): PendingIntent? {
        val intent = Intent(applicationContext, MainActivity::class.java)
        return TaskStackBuilder.create(applicationContext).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        }
    }


    override fun doWork(): Result {
        val flood = TypeConverterEntity().toGeometryFlood(dataFlood)
        val mNotificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val builder = NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL_ID)
            .setContentIntent(getPendingIntent())
            .setSmallIcon(R.drawable.ic_notifications)
            .setContentTitle(flood?.properties?.city_name)
            .setContentText(flood?.properties?.let { getFloodCategoryDescription(applicationContext, it.state) })

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            /* Create or update. */
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, dataFlood, NotificationManager.IMPORTANCE_DEFAULT)

            builder.setChannelId(NOTIFICATION_CHANNEL_ID)

            mNotificationManager.createNotificationChannel(channel)
        }

        val notif = builder.build()
        mNotificationManager.notify(0, notif)

        return Result.success()
    }

    private fun getFloodCategoryDescription(context: Context, state: Int): String {
        return when (state) {
            1 -> context.getString(R.string.status_code_flood_1)
            2 -> context.getString(R.string.status_code_flood_2)
            3 -> context.getString(R.string.status_code_flood_3)
            4 -> context.getString(R.string.status_code_flood_4)
            else -> context.getString(R.string.status_code_flood_not_valid)
        }
    }
}