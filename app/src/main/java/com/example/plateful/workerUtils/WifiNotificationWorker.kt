package com.example.plateful.workerUtils

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.plateful.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

private const val TAG = "WithWifiWorker"
class WifiNotificationWorker(context: Context, params: WorkerParameters): CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        makeStatusNotification("starting the worker", applicationContext)
        return withContext(Dispatchers.IO) {
            return@withContext try {
                delay(10000L)
                makeStatusNotification("work finished successfully", applicationContext)
                Result.success()
            } catch (throwable: Throwable) {
                Result.failure()
            }
        }
    }

    fun makeStatusNotification(message: String, context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Plateful"
            val description = "Plateful notifications"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("platefulApp", name, importance)
            channel.description = description

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

            notificationManager?.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(context, "platefulApp")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Plateful")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVibrate(LongArray(0))

        if ( ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        NotificationManagerCompat.from(context).notify(1, builder.build())
    }
}