package com.example.sample

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.sample.data.Bus
import com.example.sample.databinding.ArriveBusBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Query

// 도착예정 버스 페이지
class ArrivalBus : AppCompatActivity() {
    lateinit var retrofit: Retrofit
    lateinit var service: SeoulOpenService
    private lateinit var binding : ArriveBusBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ArriveBusBinding.inflate(layoutInflater)
        setContentView(binding.root)

        retrofit = Retrofit.Builder()
            .baseUrl(SeoulOpenApi.DOMAIN)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // 인텐트로 정류소 아이디, 노선 아이디, 정류소 순번 받아오기
        // stId, busRouteId, ord(정류소 순번)
        val stId = intent.getStringExtra("stId").toString()
        val busRouteId = intent.getStringExtra("busRouteId").toString()
        val ord = intent.getStringExtra("seq").toString()

        service = retrofit.create(SeoulOpenService::class.java)
        service.getArrive(stId, busRouteId, ord, "json").enqueue(object : Callback<Bus>{
            override fun onResponse(call: Call<Bus>, response: Response<Bus>) {
                if (response.isSuccessful){
                    println(response.body().toString())
                    println(response.body()?.msgBody.toString())
                    if(response.body()?.msgBody?.itemList != null) {
                        getFirstBusId(response.body()!!)
                    }
                }
            }

            override fun onFailure(call: Call<Bus>, t: Throwable) {
                Log.e("retrofit onFailure", "${t.printStackTrace()}")
            }
        })
    }

    // 첫번째로 도착하는 버스 아이디 가져오기
    fun getFirstBusId(body: Bus) {
        binding.arrivalBusNum.text = body.msgBody.itemList[0].plainNo1 // 차량번호
        binding.busId.text = body.msgBody.itemList[0].vehId1 // 버스 ID
    }
}