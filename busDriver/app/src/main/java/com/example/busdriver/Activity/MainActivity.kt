package com.example.busdriver.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.busdriver.Model.RegisterInfo_D
import com.example.busdriver.Service.RegisterInfo
import com.example.busdriver.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val retrofit = Retrofit.Builder().baseUrl("http://10.0.2.2:3000")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val service = retrofit.create(RegisterInfo::class.java)

        Log.d("시작??", "시작했습니다")



        thread(start = true) {
            while (true) {
                runOnUiThread { // UI에 접근할 수 있음
                    service.getRegisterNum().enqueue(object : Callback<RegisterInfo_D> {
                        override fun onResponse(
                            call: Call<RegisterInfo_D>,
                            response: Response<RegisterInfo_D>
                        ) {
                            if (response.isSuccessful) {
                                Log.d("응답 도착", "응답 도착했습니다")
                                // 여기에 서버로부터 받아온 현재 예약 인원 값을 넣어줘야
                                binding.textView.text = response.body()?.registerNum1.toString()
                                Log.d("응답 내용", response.body().toString())
                            }
                        }

                        override fun onFailure(call: Call<RegisterInfo_D>, t: Throwable) {
                            Log.d("res_d", "onResponse_d 실패")
                        }
                    })
                }

                Thread.sleep(5000) // 5초마다 갱신
            }
        }
    }
}