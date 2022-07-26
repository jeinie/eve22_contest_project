package com.example.testapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.Adapter.MainCustomAdapter
import com.example.testapp.Adapter.StationCustomAdapter
import com.example.testapp.SeoulOpenApi
import com.example.testapp.SeoulOpenService
import com.example.testapp.data.Bus
import com.example.testapp.databinding.ActivityStationBinding
import com.example.testapp.myStationViewModel.MyStationViewModel
import com.example.testapp.myStationViewModel.StationData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.collections.ArrayList

class StationActivity : AppCompatActivity() {
    lateinit var retrofit: Retrofit
    lateinit var service: SeoulOpenService
    lateinit var binding: ActivityStationBinding
    private val viewModel by viewModels<MyStationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = StationCustomAdapter(viewModel)
        binding.recyclerView2.adapter = adapter
        binding.recyclerView2.layoutManager = LinearLayoutManager(this)
        binding.recyclerView2.setHasFixedSize(true)

        viewModel.itemsListData.observe(this) {
            adapter.notifyDataSetChanged()
        }

        retrofit = Retrofit.Builder()
            .baseUrl(SeoulOpenApi.DOMAIN)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(SeoulOpenService::class.java)
        val busRouteId = intent.getStringExtra("busRouteId").toString()
        val ord = intent.getIntExtra("ord", 0)

        println("StationActivity:: busrouteId=$busRouteId, ord=$ord") // 넘어오는 것 확인됨

        service.getStation(busRouteId, "json").enqueue(object : Callback<Bus>{
            override fun onResponse(call: Call<Bus>, response: Response<Bus>) {
                if (response.isSuccessful) {
                    if (response.body()?.msgBody?.itemList != null) {
                        viewModel.deleteItem()
                        val body = response.body()!!.msgBody
                        val stationList = ArrayList<String>()
                        val stationIdList = ArrayList<String>()

                        for (i in 0..body.itemList.size-1) {
                            stationList.add(body.itemList[i].stationNm) // 정류장 이름
                            stationIdList.add(body.itemList[i].station) // 정류장 ID
                        }

                        for (i in 0..body.itemList.size-1) {
                            val stationNm = stationList[i]
                            val stationId = stationIdList[i]
                            viewModel.addItem(StationData(stationNm, stationId))
                        }

                        val intent = Intent(applicationContext, ArrivalActivity::class.java)
                        Log.d("busRouteId 확인 : ",busRouteId)
                        Log.d("ord 확인 : ",ord.toString())
                        intent.putExtra("busRouteId", busRouteId) // 노선 ID 보내기
                        intent.putExtra("ord", ord) // 순번 보내기

                    }
                }
            }

            override fun onFailure(call: Call<Bus>, t: Throwable) {

            }
        })
    }
}