package com.example.testapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.example.testapp.SeoulOpenApi
import com.example.testapp.SeoulOpenService
import com.example.testapp.data.Bus
import com.example.testapp.databinding.ActivityArrivalBinding
import com.example.testapp.myStationViewModel.MyStationViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ArrivalActivity : AppCompatActivity() {
    lateinit var binding : ActivityArrivalBinding
    lateinit var retrofit: Retrofit
    lateinit var service: SeoulOpenService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArrivalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val stId = intent.getStringExtra("stationId").toString() // 정류소 아이디
        val busRouteId = intent.getStringExtra("busRouteId").toString() // 노선 아이디
        val ord = intent.getStringExtra("ord").toString() // 순번

        //val busRouteId = "108900012"
        //val ord = "1"
        println("stId: $stId")
        println("busRouteId: $busRouteId")
        println("ord: $ord")

        binding.arrivalId.text = stId

        retrofit = Retrofit.Builder()
            .baseUrl(SeoulOpenApi.DOMAIN)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(SeoulOpenService::class.java)
        service.getArrive(stId, busRouteId, ord, "json").enqueue(object : Callback<Bus>{
            override fun onResponse(call: Call<Bus>, response: Response<Bus>) {
                if (response.isSuccessful) {
                    println(response.body().toString())
                    println(response.body()?.msgBody.toString())
                    if (response.body()?.msgBody?.itemList != null) {
                        binding.arrivalId.text = busRouteId
                        val vehId1 = response.body()!!.msgBody.itemList[0].vehId1
                        if (vehId1 == "0") { // 0 -> 도착정보 없는 경우
                            binding.vehId1.text = "도착정보 없음"
                        } else {
                            binding.vehId1.text = vehId1 // 첫번째로 도착하는 버스 ID
                        }
                        println(response.body()!!.msgBody.itemList[0].vehId1)
                    }
                }
            }

            override fun onFailure(call: Call<Bus>, t: Throwable) {
                Log.e("retrofit onFailure", "${t.printStackTrace()}")
            }
        })
    }
}