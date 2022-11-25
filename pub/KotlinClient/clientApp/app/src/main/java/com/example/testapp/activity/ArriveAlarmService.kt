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

    val builder = NotificationCompat.Builder(this, "default")
    val builder2 = NotificationCompat.Builder(this, "default2")

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

        Thread.sleep(2000)
        createNotification()
        mThread!!.start()

    }

override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
    Log.d("뭐지","안되는건가")
    alarmMsg = intent!!.getLongExtra("alarmMsg",0)
    stName = intent!!.getStringExtra("stName")!!
    routeNum = intent!!.getStringExtra("routeNum")!!
    //return START_REDELIVER_INTENT
    return super.onStartCommand( intent , flags , startId )
}

private var mThread: Thread? = object : Thread("My Thread") {
    override fun run() {
        super.run()

        for (i in 0 until ( (alarmMsg.toInt() - 2) * 60 ) ) {
            Log.d("스레드진행중", "count : ${i}")

            try {
                sleep( 1000 )
            } catch (e: InterruptedException) {
                currentThread().interrupt()
                //break
            }

        }
        /*왜 안띄워지지*/

        //builder.setContentTitle("3분이 남았을때 알림이 울려요!")
        //builder.setContentText("버스가 곧 도착합니다!!")
        builder2.setSmallIcon( R.drawable.ic_launcher_foreground )
        builder2.setContentTitle("3분이 남았을때 알림이 울려요!")
        builder2.setContentText("버스가 곧 도착합니다!!")
        builder2.color = Color.RED

        val notificationIntent = Intent(applicationContext, MainActivity::class.java)
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        val pendingIntent = PendingIntent.getActivity(applicationContext, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)
        builder.setContentIntent(pendingIntent) // 알림 클릭 시 이동

        // 알림 표시

        val notificationManager = applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(
                NotificationChannel(
                    "default2",
                    "기본 채널",
                    NotificationManager.IMPORTANCE_HIGH
                )
            )
        }
        notificationManager.notify(NOTI_ID, builder2.build()) // id : 정의해야하는 각 알림의 고유한 int값
        val notification = builder2.build()
        startForeground(NOTI_ID, notification)

        /*왜 안띄워지지*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator?.vibrate(vibratorEffect)
        }
    }
}


private fun createNotification() {
    //builder = NotificationCompat.Builder(this, "default")
    //val builder = NotificationCompat.Builder(this, "default")
    builder.setSmallIcon( R.drawable.ic_launcher_foreground )
    //builder.setContentTitle(stName+"\n"+routeNum)
    builder.setContentTitle("3분이 남았을때 알림이 울려요!")
    builder.setContentText("버스가 이동하는중입니다")

    //builder.color = Color.RED
    builder.color = Color.GREEN
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
        private const val NOTI_ID2 = 2
    }

}