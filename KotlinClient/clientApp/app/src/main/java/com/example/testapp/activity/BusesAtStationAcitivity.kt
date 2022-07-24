package com.example.testapp.activity

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.testapp.Model.RouteModel.RouteModel
import com.example.testapp.databinding.ActivityBusListAtStationBinding

class BusesAtStationAcitivity : AppCompatActivity() {
    lateinit var binding : ActivityBusListAtStationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = Color.parseColor("#90008000")
        binding = ActivityBusListAtStationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var routeList = ArrayList<RouteModel>()


    }
}