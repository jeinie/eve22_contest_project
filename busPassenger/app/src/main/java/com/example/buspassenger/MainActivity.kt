package com.example.buspassenger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.buspassenger.Model.RegisterInfo
import com.example.buspassenger.Service.BusPassengersInfo
import com.example.buspassenger.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {
    // 코루틴
    private lateinit var job : Job // Job 객체는 코루틴 동작을 제어하기 위한 객체
    override val coroutineContext : CoroutineContext
        get() = Dispatchers.Main + job // Dispatcher는 코루틴이 어떤 쓰레드에서 동작할지 지정하는 역할
    // 코루틴

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        job = Job()

        // 예약버튼 누르면 증가될 것
        var registerNum = 0

        val retrofit = Retrofit.Builder().baseUrl("http://10.0.2.2:3000")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val service = retrofit.create(BusPassengersInfo::class.java)

        /*launch {*/
            binding.button1.setOnClickListener {
                //registerNum += 1
                service.getBusStatus("111000", "부평구청").enqueue(object : Callback<RegisterInfo> {
                    override fun onResponse(call: Call<RegisterInfo>, response: Response<RegisterInfo>) {
                        Log.d("버튼 눌림 3 ", "버튼 눌렸습니다 3")
                        if (response.isSuccessful) {
                            Log.d("res", response.body().toString())
                            Log.d("registerNum", "현재 예약 인원 ${registerNum}")
                        }
                    }

                    override fun onFailure(call: Call<RegisterInfo>, t: Throwable) {
                        Log.d("버튼 눌림 4", "버튼 눌렸습니다 4")
                        Log.d("res", "onResponse 실패")

                    }
                })
            }
        //}

    }
}