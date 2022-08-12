package com.example.testapp.activity

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

import androidx.appcompat.app.AppCompatActivity
import com.example.buspassenger.Model.RegisterInfo
import com.example.buspassenger.Service.BusPassengersInfo
import com.example.testapp.Adapter.BusesAtStationCustomeAdapter

import com.example.testapp.Model.ArriveBusInfo.ArriveBusInfo
import com.example.testapp.controller.BusesAtStationController.BusesAtStationController
import com.example.testapp.databinding.ActivityBusListAtStationBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.CoroutineContext

class BusesAtStationAcitivity : AppCompatActivity() , CoroutineScope {
    //코루틴
    private lateinit var job : Job
    override val coroutineContext : CoroutineContext
        get() = Dispatchers.Main + job
    //코루틴
    lateinit var binding : ActivityBusListAtStationBinding
    lateinit var basc : BusesAtStationController
    lateinit var basca : BusesAtStationCustomeAdapter

    //
    lateinit var DialogBuilder : AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        job = Job()

        window.statusBarColor = Color.parseColor("#90008000")
        binding = ActivityBusListAtStationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        basc = BusesAtStationController()

        if (Build.VERSION.SDK_INT > 9) {
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }

        val DialogBuilder = AlertDialog.Builder(this)


        val stId = intent.getStringExtra("stId")
        val routeId = intent.getStringExtra("routeId")
        val ord = intent.getStringExtra("ord")
        val routeNum = intent.getStringExtra("routeNum")
        val stationNm = intent.getStringExtra("stationNm")
        var arriveBusList : ArrayList<ArriveBusInfo> = intent.getSerializableExtra("ArriveBusesInfoList") as
                ArrayList<ArriveBusInfo>

        binding.stationNameTextView.text = stationNm
        binding.routeNumTextView.text = routeNum
        /*
        Log.d("stId",stId!!)  //122000326
        Log.d("routeId",routeId!!) // 100100500
        Log.d("ord",ord!!) // 2
        Log.d("routeNum",routeNum!!) // 4312 얘랑
        Log.d("stationNm",stationNm!!) // 개포우성6차아파
        //vehid랑 arrmsg 가 recyclerview로 넘어갈것이다
        Log.d("1","arriveBusList[0].arrmsg : ${arriveBusList[0].arrmsg} vehid : ${arriveBusList[0].vehId}")
        Log.d("2","arriveBusList[0].arrmsg : ${arriveBusList[1].arrmsg} vehid : ${arriveBusList[1].vehId}")
        //다 구해왔는데
        */

        basca = BusesAtStationCustomeAdapter(arriveBusList,object : BusesAtStationCustomeAdapter.OnRouteClickedListener{
            override fun onRouteClicked(model: ArriveBusInfo) {

                //Toast.makeText(baseContext, "${model.routeId} , ",Toast.LENGTH_SHORT).show()
                //현재 toast는 model의 routeId 즉 4312가 뜨는 상태이다.
                //고로 routeId를 넘겨서 버스가 지나는 station들을 뽑아와야 한다
                //sc.loadBus(model.routeId,applicationContext)

                //basc.getArriveBus(model.stationId,routeId!!,model.ord,routeNum!!,model.stationNm,applicationContext)
                //여기서 클릭하면 나오면 좋겠는데
                //여기부터 다이얼로그
                //model.vehId
                DialogBuilder
                    .setTitle("${stationNm} ( ${model.routeNum} )")
                    .setMessage("${model.arrmsg}")
                //메세지만 띄워준상태고
                var DialogBuilderListener = object : DialogInterface.OnClickListener {
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        when (p1) {
                            DialogInterface.BUTTON_POSITIVE ->
                            {




                                //문제생길듯..
                                var registerNum = 0

                                val retrofit = Retrofit.Builder().baseUrl("http://10.0.2.2:3000")
                                    .addConverterFactory(GsonConverterFactory.create()).build()
                                val service = retrofit.create(BusPassengersInfo::class.java)


                                service.getBusStatus(model.vehId, stationNm!!).enqueue(object :
                                    Callback<RegisterInfo> {
                                    override fun onResponse(call: Call<RegisterInfo>, response: Response<RegisterInfo>) {
                                        Log.d("버튼 눌림 3 ", "버튼 눌렸습니다 3")
                                        if (response.isSuccessful) {
                                            Log.d("res", response.body().toString())
                                            Log.d("registerNum", "현재 예약 인원 ${registerNum}")
                                            Toast.makeText(baseContext,"예약되었습니다.",Toast.LENGTH_SHORT).show()
                                            Log.d("클릭한 버스의 아이디 : ","${model.vehId}")
                                            basc.getBusPosition(model.vehId,stationNm!!,model.routeNum,applicationContext)

                                        }
                                    }

                                    override fun onFailure(call: Call<RegisterInfo>, t: Throwable) {
                                        Log.d("버튼 눌림 4", "버튼 눌렸습니다 4")
                                        Log.d("res", "onResponse 실패")

                                    }
                                })

                                //문제생길듯..


                            }
                            DialogInterface.BUTTON_NEGATIVE ->
                                Log.d("그냥취소","그냥취소")
                            DialogInterface.BUTTON_NEUTRAL ->
                                Log.d("예약취소","예약취소")
                        }
                    }
                }
                DialogBuilder.setPositiveButton("예약하기",DialogBuilderListener)
                DialogBuilder.setNegativeButton("취소",DialogBuilderListener)
                DialogBuilder.setNeutralButton("예약취소",DialogBuilderListener)
                DialogBuilder.show()
                //여기까지 다이얼로그
            }
        })
        Log.d("여기 오긴하나","오긴하나")
        binding.busListRecyclerView.adapter = basca
        basca.notifyDataSetChanged()
        //recyclerv

    }
}