package com.example.testapp.activity

import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.testapp.Adapter.DrivingCustomAdapter
import com.example.testapp.Model.StationModel.StationModel
import com.example.testapp.controller.drivingController.DrivingController
import com.example.testapp.databinding.ActivityDrivingBinding
import com.example.testapp.databinding.ActivityIndexBinding

class DrivingActivity : AppCompatActivity() {

    lateinit var binding : ActivityDrivingBinding
    lateinit var dc : DrivingController
    lateinit var dca : DrivingCustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = Color.parseColor("#000000")
        binding = ActivityDrivingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dc = DrivingController()





        //

        val stationList : ArrayList<StationModel> = intent.getSerializableExtra("stationList") as ArrayList<StationModel>
        var route : String? = intent.getStringExtra("routeNum")

        var vehId : String? = intent.getStringExtra("vehId")
        var busRouteId : String? = intent.getStringExtra("busRouteId")

        //intent.putExtra("vehId",this.vehId)
        //intent.putExtra("busRouteId",this.busRouteId)


        //binding.routeNumTextView.text = routeNum
        //binding.startAndEndStationTextView.text = intent.getStringExtra("startStationNm") + "<---> " +intent.getStringExtra("finishStationNm")


        //ArrayList 받아왔으니 목록 list 그대로 띄워주는 작업을 해줘야지

        dca = DrivingCustomAdapter(stationList,object : DrivingCustomAdapter.OnRouteClickedListener{
            override fun onRouteClicked(model: StationModel) {
                //Toast.makeText(baseContext, "${model.routeId} , ",Toast.LENGTH_SHORT).show()
                //현재 toast는 model의 routeId 즉 4312가 뜨는 상태이다.
                //고로 routeId를 넘겨서 버스가 지나는 station들을 뽑아와야 한다
                //sc.loadBus(model.routeId,applicationContext)
                //Log.d("item확인","item별 model.ord : ${model.ord}")
                //Log.d("item확인","item별 routeId : ${routeId}")
                Log.d("item확인","item별 model.stationId : ${model.stationId}")
                Log.d("item확인","item별 model.stationNm : ${model.stationNm}")
                //dc.getArriveBus(model.stationId,routeId!!,model.ord,routeNum!!,model.stationNm,applicationContext)

            }
        })
        binding.stationsrecyclerView.adapter = dca
        dca.notifyDataSetChanged()

        //



    }

}