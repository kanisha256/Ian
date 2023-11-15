package com.example.ian

import android.annotation.SuppressLint
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class NotificationWorker(context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {

    @SuppressLint("MissingPermission")
    override suspend fun doWork(): Result {
        val name = inputData.getString("name")
        val price = inputData.getString("price")
        val date = inputData.getString("date")

        // Создаем уведомление
        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setContentTitle(name)
            .setContentText("Price: $price, Date: $date")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()

        val notificationManager = NotificationManagerCompat.from(applicationContext)
        notificationManager.notify(NOTIFICATION_ID, notification)

        return Result.success()
    }

    companion object {
        private const val CHANNEL_ID = "notification_channel"
        private const val NOTIFICATION_ID = 1
    }
}