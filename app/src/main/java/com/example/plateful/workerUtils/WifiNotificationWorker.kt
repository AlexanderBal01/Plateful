package com.example.plateful.workerUtils

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.plateful.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

/**
 * This class defines a coroutine worker for Plateful that runs in the background and displays notifications.
 * The worker simulates some work being done for 10 seconds and then notifies the user that the work is finished.
 */
class WifiNotificationWorker(
    context: Context,
    params: WorkerParameters,
) : CoroutineWorker(context, params) {
    /**
     * This function defines the coroutine that runs the worker's background task.
     * It first creates a notification with a "starting the worker" message and displays it.
     * Then it simulates some work by delaying for 10 seconds.
     * Finally, it creates another notification with a "work finished successfully" message and displays it.
     * If any error occurs during the process, the worker returns a failure result.
     *
     * @return Result - The result of the worker execution. Result.success() if successful, Result.failure() otherwise.
     */
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

    /**
     * This function creates a notification with the specified message and displays it to the user.
     * It creates a notification channel for Plateful if it doesn't already exist.
     *
     * @param message - The message to be displayed in the notification.
     * @param context - The context of the application.
     */
    private fun makeStatusNotification(
        message: String,
        context: Context,
    ) {
        val name = "Plateful"
        val description = "Plateful notifications"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel("platefulApp", name, importance)
        channel.description = description

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

        notificationManager?.createNotificationChannel(channel)

        val builder =
            NotificationCompat
                .Builder(context, "platefulApp")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Plateful")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVibrate(LongArray(0))

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        NotificationManagerCompat.from(context).notify(1, builder.build())
    }
}
