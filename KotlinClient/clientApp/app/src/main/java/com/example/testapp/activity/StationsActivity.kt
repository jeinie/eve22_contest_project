package com.example.testapp.activity

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

import com.example.testapp.Adapter.StationsCustomAdapter
import com.example.testapp.Model.BusModel.ExampleModel
import com.example.testapp.Model.StationModel.StationModel

import com.example.testapp.controller.stationsController.StationsController
import com.example.testapp.databinding.ActivityStationsBinding

class StationsActivity : AppCompatActivity(){
    lateinit var binding : ActivityStationsBinding
    lateinit var sca : StationsCustomAdapter
    lateinit var sc : StationsController
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        //맨위 보라색 상태바 색상 변경
        window.statusBarColor = Color.parseColor("#90008000")
        binding = ActivityStationsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //여기까지 기본화면 구성완료

        val stationList : ArrayList<StationModel> = intent.getSerializableExtra("stationList") as
        ArrayList<StationModel>
        var routeNum : String? = intent.getStringExtra("routeNum")
        binding.routeNumTextView.text = routeNum
        binding.startAndEndStationTextView.text = intent.getStringExtra("startStationNm") + "<---> " +intent.getStringExtra("finishStationNm")


        //ArrayList 받아왔으니 목록 list 그대로 띄워주는 작업을 해줘야지

        sca = StationsCustomAdapter(stationList,object : StationsCustomAdapter.OnRouteClickedListener{
            override fun onRouteClicked(model: ExampleModel) {
                //Toast.makeText(baseContext, "${model.routeId} , ",Toast.LENGTH_SHORT).show()
                //현재 toast는 model의 routeId 즉 4312가 뜨는 상태이다.
                //고로 routeId를 넘겨서 버스가 지나는 station들을 뽑아와야 한다
                //sc.loadBus(model.routeId,applicationContext)
                Log.d("1","1")
            }
        })
        binding.stationsrecyclerView.adapter = sca
        sca.notifyDataSetChanged()
        //recyclerv


    }
}