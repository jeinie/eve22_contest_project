package com.example.testapp.activity

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.testapp.R
import java.lang.UnsupportedOperationException
class ArriveAlarmService : Service() {

    var alarmMsg : Long = 0
    lateinit var stName : String
    lateinit var routeNum : String
    var vibrator : Vibrator? = null
    lateinit var vibratorEffect : VibrationEffect

    override fun onBind(intent: Intent?): IBinder? {
        throw UnsupportedOperationException("Not yet implemented")
    }



    override fun onCreate() {
        super.onCreate()

        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.O )
        {
            vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibratorEffect = VibrationEffect.createOneShot(1000, 100)
        }

        Thread.sleep(3000)

        mThread!!.start()
    }

override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
    Log.d("뭐지","안되는건가")
    alarmMsg = intent!!.getLongExtra("alarmMsg",0)
    stName = intent!!.getStringExtra("stName")!!
    routeNum = intent!!.getStringExtra("routeNum")!!
    return START_REDELIVER_INTENT
}

private var mThread: Thread? = object : Thread("My Thread") {
    override fun run() {
        super.run()

        for (i in 0 until ( (alarmMsg.toInt() - 3) * 60 ) ) {
            Log.d("스레드진행중", "count : ${i}")


            try {
                sleep( 1000 )
            } catch (e: InterruptedException) {
                currentThread().interrupt()
                //break
            }

        }
        createNotification()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator?.vibrate(vibratorEffect)
        }

    }
}


private fun createNotification() {
    val builder = NotificationCompat.Builder(this, "default")
    builder.setSmallIcon( R.drawable.ic_launcher_foreground )
    builder.setContentTitle("버스를 탑승하세요!")
    builder.setContentText("버스탑승을 할 시간이에요!")

    builder.color = Color.RED
    val notificationIntent = Intent(this, MainActivity::class.java)
    notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP)
    val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)
    builder.setContentIntent(pendingIntent) // 알림 클릭 시 이동

    // 알림 표시
    val notificationManager = this.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        notificationManager.createNotificationChannel(
            NotificationChannel(
                "default",
                "기본 채널",
                NotificationManager.IMPORTANCE_HIGH
            )
        )
    }
    notificationManager.notify(NOTI_ID, builder.build()) // id : 정의해야하는 각 알림의 고유한 int값
    val notification = builder.build()
    startForeground(NOTI_ID, notification)
}




    override fun onDestroy() {
        super.onDestroy()
        if (mThread != null) {
            mThread!!.interrupt()
            mThread = null
        }
        Log.d(TAG, "onDestroy")

    }
    companion object {
        private const val TAG = "MyServiceTag"

        // Notification
        private const val NOTI_ID = 1
    }

}