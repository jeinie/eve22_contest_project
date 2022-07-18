package com.example.sample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sample.data.Bus
import com.example.sample.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private val viewModel by viewModels<MyViewModel>()
    lateinit var retrofit: Retrofit
    lateinit var service: SeoulOpenService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = CustomAdapter(viewModel)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)

        viewModel.itemsListData.observe(this) { // 데이터에 변화가 있을 때 어댑터에게 변경을 알림
            adapter.notifyDataSetChanged() // 어댑터가 리사이클러뷰에게 알려 내용을 갱신함
        }


        retrofit = Retrofit.Builder()
            .baseUrl(SeoulOpenApi.DOMAIN)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(SeoulOpenService::class.java)

        // 검색 버튼을 클릭하면
        binding.button.setOnClickListener {
            loadBus()
        }
    }

    private fun loadBus() {
        val busNum = binding.edit.text.toString()

        service.getBus(busNum, "json").enqueue(object : Callback<Bus>{
            override fun onResponse(call: Call<Bus>, response: Response<Bus>) {
                if (response.isSuccessful){
                    println(response.body().toString())
                    println(response.body()?.msgBody.toString())
                    if(response.body()?.msgBody?.itemList != null) {
                        getBusRouteId(response.body()!!)
                    }
                }
            }

            override fun onFailure(call: Call<Bus>, t: Throwable) {
                Log.e("retrofit onFailure", "${t.printStackTrace()}")
            }
        })
    }

    // 노선 ID 가져오기
    fun getBusRouteId(body: Bus) {
        val routeId = body.msgBody.itemList[0].busRouteId
        val routeNm = body.msgBody.itemList[0].busRouteNm
        binding.textView.text = routeId + "\n" + routeNm // 택스트뷰에 노선 ID 출력
        println("노선 ID는 $routeId")
        println("노선 번호는 $routeNm")

        // 노선 ID와 순번(seq) CustomAdapter 로 보내기
        val intent = Intent(this, CustomAdapter::class.java)
        //intent.putExtra("routeId", routeId)
        //intent.putExtra("seq", body.msgBody.itemList[0].seq)

        // 가져온 노선 ID로 버스 정류소 가져오기
        getStation(routeId)
    }

    fun getStation(routeId:String) {
        service.getStation(routeId, "json").enqueue(object : Callback<Bus>{
            override fun onResponse(call: Call<Bus>, response: Response<Bus>) {
                if (response.isSuccessful){
                    if(response.body()?.msgBody?.itemList != null) {
                        getStationList(response.body()!!) // 버스 정류장 목록 가져오기
                    }
                }
            }

            override fun onFailure(call: Call<Bus>, t: Throwable) {
                Log.e("retrofit onFailure", "${t.printStackTrace()}")
            }
        })
    }

    fun getStationList(body: Bus) {
        val direction = body.msgBody.itemList[0].direction

        for (i in 0..body.msgBody.itemList.size) {
            val item = body.msgBody.itemList[i]
            if (item.direction != direction){ // 방향이 반대이면 중단 (같은 방향끼리만 출력)
                break
            }
            //stList += item.stationNm + '\n' // 정류소 이름 계속 붙여나가기

            viewModel.addItem(StationData(item.stationNm, item.station)) // 정류소 이름, ID 목록으로 붙이기
            Log.d("Api", body.msgBody.itemList[i].stationNm)
        }

        // 정류소 이름 목록들 텍스트뷰에 붙이기
        //binding.textStation.text = stList
    }
}
