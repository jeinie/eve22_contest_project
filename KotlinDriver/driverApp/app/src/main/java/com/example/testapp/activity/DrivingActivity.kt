package com.example.testapp.activity

import android.graphics.Color
import android.os.*
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.testapp.Adapter.DrivingCustomAdapter
import com.example.testapp.Model.StationModel.StationModel
import com.example.testapp.Model.WaitingCntForEachStationModel.WaitingCntForEachStation
import com.example.testapp.controller.drivingController.DrivingController
import com.example.testapp.databinding.ActivityDrivingBinding
import com.example.testapp.databinding.ActivityIndexBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.concurrent.thread
import kotlin.coroutines.CoroutineContext
import kotlin.reflect.typeOf




class DrivingActivity : AppCompatActivity() , CoroutineScope{

    private lateinit var job : Job
    override val coroutineContext : CoroutineContext
        get() = Dispatchers.Main + job

    lateinit var binding : ActivityDrivingBinding
    lateinit var dc : DrivingController
    lateinit var dca : DrivingCustomAdapter
    //recyclerview 갱신시 화면 올라감 방지
    //lateinit var recyclerViewState : Parcelable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var busPos : Int = 0
        var bpForLoop : String
        job = Job()



        window.statusBarColor = Color.parseColor("#000000")
        binding = ActivityDrivingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dc = DrivingController()

        if (Build.VERSION.SDK_INT > 9) {
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }


        //

        val stationList : ArrayList<StationModel> = intent.getSerializableExtra("stationList") as ArrayList<StationModel>

        var routeNum : String? = intent.getStringExtra("routeNum")
        var vehId : String? = intent.getStringExtra("vehId")
        var routeId : String? = intent.getStringExtra("routeId")


        //recycler view 핸들러
        val handler = object : Handler(){
            override fun handleMessage(msg: Message) {


                dca = DrivingCustomAdapter(stationList,
                    object : DrivingCustomAdapter.OnRouteClickedListener {
                        override fun onRouteClicked(model: StationModel) {

                            //Toast.makeText(baseContext, "${model.routeId} , ",Toast.LENGTH_SHORT).show()
                            //현재 toast는 model의 routeId 즉 4312가 뜨는 상태이다.
                            //고로 routeId를 넘겨서 버스가 지나는 station들을 뽑아와야 한다
                            //sc.loadBus(model.routeId,applicationContext)
                            //Log.d("item확인","item별 model.ord : ${model.ord}")
                            //Log.d("item확인","item별 routeId : ${routeId}")
                            Log.d("item확인", "item별 model.stationId : ${model.stationId}")
                            Log.d("item확인", "item별 model.stationNm : ${model.stationNm}")
                            //dc.getArriveBus(model.stationId,routeId!!,model.ord,routeNum!!,model.stationNm,applicationContext)

                        }
                    })
                binding.stationsrecyclerView.adapter = dca
                //recyclerview 갱신시 화면 올라감 방지
                //recyclerViewState = binding.stationsrecyclerView.layoutManager!!.onSaveInstanceState()!!
                // 스크롤
                binding.stationsrecyclerView.scrollToPosition( msg.what )

                dca.notifyDataSetChanged()
            }
        }
        //recycler view 핸들러



        Log.d("DrivingActivity에서의 routeNum : ","${routeNum}")
        Log.d("DrivingActivity에서의 vehId : ","${vehId}")
        Log.d("DrivingActivity에서의 busRouteId : ","${routeId}")
        //intent.putExtra("vehId",this.vehId)
        //intent.putExtra("busRouteId",this.busRouteId)

        //WCFES = WaitingCntForEachStation

        launch {
            thread(start = true) {
                while (true) {

                    Log.d("갱신 : ", "갱신되었음")

                    var WCFES = dc.getWaitingCntForEachStation(vehId!!).StationAndWaiting!!

                    WCFES = WCFES.replace("{", "").replace("}", "").replace("\"", "")

                    var WCFESArr = WCFES.split(",")

                    WCFESArr.forEach { data ->

                        for (i: Int in 0 until stationList.size) {

                            if ((data.split(":")[0]).equals(stationList[i].stationNm)) {

                                stationList[i].waitingCnt = data.split(":")[1]

                            }

                        }

                    }

                    bpForLoop = dc.getBusPos( vehId )
                    //Log.d("API 제대로 호출되나","${dc.getBusPos( vehId )}")
                    for( i in 0 until stationList.size ) {

                        if ( stationList[ i ].stationId.equals(  bpForLoop  ) ) {

                            busPos = i

                            break

                        } else {

                            busPos = 0

                        }

                    }


                    handler.sendEmptyMessage( busPos )

                    Thread.sleep(5000L)

                }
                //while문 끝
            }
            //thread 끝


        }
        //binding.routeNumTextView.text = routeNum
        //binding.startAndEndStationTextView.text = intent.getStringExtra("startStationNm") + "<---> " +intent.getStringExtra("finishStationNm")


        //ArrayList 받아왔으니 목록 list 그대로 띄워주는 작업을 해줘야지
        /*
        dca = DrivingCustomAdapter(stationList,
            object : DrivingCustomAdapter.OnRouteClickedListener {
                override fun onRouteClicked(model: StationModel) {

                    //Toast.makeText(baseContext, "${model.routeId} , ",Toast.LENGTH_SHORT).show()
                    //현재 toast는 model의 routeId 즉 4312가 뜨는 상태이다.
                    //고로 routeId를 넘겨서 버스가 지나는 station들을 뽑아와야 한다
                    //sc.loadBus(model.routeId,applicationContext)
                    //Log.d("item확인","item별 model.ord : ${model.ord}")
                    //Log.d("item확인","item별 routeId : ${routeId}")
                    Log.d("item확인", "item별 model.stationId : ${model.stationId}")
                    Log.d("item확인", "item별 model.stationNm : ${model.stationNm}")
                    //dc.getArriveBus(model.stationId,routeId!!,model.ord,routeNum!!,model.stationNm,applicationContext)

                }
            })
        binding.stationsrecyclerView.adapter = dca
        dca.notifyDataSetChanged()
        */




    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()

    }
}