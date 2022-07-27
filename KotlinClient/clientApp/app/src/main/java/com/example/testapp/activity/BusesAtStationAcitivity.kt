package com.example.testapp.activity

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.testapp.Model.ArriveBusInfo.ArriveBusInfo
import com.example.testapp.Model.RouteModel.RouteModel
import com.example.testapp.Model.StationModel.StationModel
import com.example.testapp.databinding.ActivityBusListAtStationBinding

class BusesAtStationAcitivity : AppCompatActivity() {
    lateinit var binding : ActivityBusListAtStationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = Color.parseColor("#90008000")
        binding = ActivityBusListAtStationBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val stId = intent.getStringExtra("stId")
        val routeId = intent.getStringExtra("routeId")
        val ord = intent.getStringExtra("ord")
        val routeNum = intent.getStringExtra("routeNum")
        val stationNm = intent.getStringExtra("stationNm")
        val arriveBusList : ArrayList<ArriveBusInfo> = intent.getSerializableExtra("ArriveBusesInfoList") as
                ArrayList<ArriveBusInfo>

        Log.d("stId",stId!!)
        Log.d("routeId",routeId!!)
        Log.d("ord",ord!!)
        Log.d("routeNum",routeNum!!)
        Log.d("stationNm",stationNm!!)
        Log.d("1","arriveBusList[0].arrmsg : ${arriveBusList[0].arrmsg} vehid : ${arriveBusList[0].vehId}")
        Log.d("2","arriveBusList[0].arrmsg : ${arriveBusList[1].arrmsg} vehid : ${arriveBusList[1].vehId}")

    }
}