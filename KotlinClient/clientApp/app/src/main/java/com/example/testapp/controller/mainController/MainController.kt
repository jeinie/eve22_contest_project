package com.example.testapp.controller.mainController

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import com.example.testapp.Model.BusModel.Bus
import com.example.testapp.Model.BusModel.ExistedStationModel
import com.example.testapp.Model.KeyModel.ApiKeyOne
import com.example.testapp.Model.RouteModel.RouteModel
import com.example.testapp.Model.StationModel.StationModel
import com.example.testapp.Service.getBus
import com.example.testapp.Service.getStation
import com.example.testapp.activity.StationsActivity

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class MainController{


    //retrofit으로 버스정보를 검색해서 그 list를 recyclerView에 뿌려주는 역할의 메서드
    //이곳에 장정윤 팀원이 작업한 MainActivity내의 loadBus() , getBusRouteId(), getStation()등을 옮겨야함

    //service들


    //service들
    //4312를 받아가서 장정윤 팀원이 하던대로 출력이 되게하는게 목표
    //Main에서 받아서 arrayList에 담고...그걸또 intent에 담아서.. Stations화면으로 넘기는게 좋을것같은데...

    //버스 번호를 넣어 -> 버스 ID를 API로 구해서 -> 정류장 목록을 불러오는것이니 getStations 로 명명한다.
    //그러고싶은데 장정윤 팀원이 작성해놓은게 있어서 그대로 가져와본다
    //장정윤 팀원의 파일중 MainActivity의 48번째에 loadBus()가 있으니 일단 그걸 따라간다
    fun loadBus( routeNum : String , applicontext : Context) {
        val retrofit = Retrofit.Builder().baseUrl(ApiKeyOne.DOMAIN).
        addConverterFactory(GsonConverterFactory.create()).build()
        val service = retrofit.create(getBus::class.java)
        //매개변수 routeNum은  4312같은 즉 노선번호
        Log.d("뭐뭐들어가지? 1","뭐뭐들어가지? 1")
        //일단 메서드 그대로 복사
        service.getBus(routeNum, "json").enqueue(object : Callback<Bus> {
            override fun onResponse(call: Call<Bus>, response: Response<Bus>) {
                if (response.isSuccessful){
                    println(response.body().toString())
                    println(response.body()?.msgBody.toString())
                    if(response.body()?.msgBody?.itemList != null) {
                        getBusRouteId(response.body()!!,applicontext)
                    }
                }
            }
            override fun onFailure(call: Call<Bus>, t: Throwable) {
                Log.e("retrofit onFailure", "${t.printStackTrace()}")
            }
        })
        //일단 메서드 그대로 복사
    }

    //존재하는 버스노선인지 체크
    fun isRouteExist( routeNum : String ) : ArrayList<RouteModel> {
        val retrofit = Retrofit.Builder().baseUrl(ApiKeyOne.DOMAIN)
            .addConverterFactory(GsonConverterFactory.create()).build()
        val service = retrofit.create(getBus::class.java)
        //동기가.. 있을텐데?

        var call: Call<Bus> = service.getBus(routeNum , "json")
        var body = call.execute().body()
        var routeArrayList = ArrayList<RouteModel>()

        try{
            for (i in body!!.msgBody.itemList) {
                routeArrayList.add( RouteModel(i.busRouteNm,i.busRouteId) )
            }
            return routeArrayList
        }catch (e : Exception){
            var ecp1 = ArrayList<RouteModel>()
            ecp1.add(RouteModel("",""))
            return ecp1
        }
        return routeArrayList
    }



    //getBusRouteId 복사
    // 노선 ID 가져오기               //일단 화면이동이 있는것같으니 applicontext를 추가
    fun getBusRouteId(body: Bus , applicontext : Context) {
        Log.d("뭐뭐들어가지? 2","뭐뭐들어가지? 2")
        val routeId = body.msgBody.itemList[0].busRouteId
        val routeNm = body.msgBody.itemList[0].busRouteNm
        println("노선 ID는 $routeId")
        println("노선 번호는 $routeNm")
        // 가져온 노선 ID로 버스 정류소 가져오기
        getStation(routeNm,routeId,applicontext)
    }
    //getBusRouteId 복사


    fun getStation(routeNum : String ,routeId:String, applicontext : Context) {
        val retrofit = Retrofit.Builder().baseUrl(ApiKeyOne.DOMAIN).
        addConverterFactory(GsonConverterFactory.create()).build()
        val service = retrofit.create(getStation::class.java)
        Log.d("뭐뭐들어가지? 3","뭐뭐들어가지? 3")
        service.getStation(routeId, "json").enqueue(object : Callback<Bus>{
            override fun onResponse(call: Call<Bus>, response: Response<Bus>) {
                if (response.isSuccessful){
                    if(response.body()?.msgBody?.itemList != null) {
                        //출발역, 종착역을 좀 뽑아내 보내야할것같은데..

                        var stationList : ArrayList<StationModel> = getStationList(response.body()!!) // 버스 정류장 목록 가져오기
                        Log.d("station정보는 뭐넘어오지","${response.body()!!}")
                        var intent = Intent(applicontext, StationsActivity::class.java)

                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        //intent에 모든 정류장 list가 들어있는 stationList를 넘긴다
                        intent.putExtra("startStationNm",stationList[0].stationNm)
                        intent.putExtra("finishStationNm",stationList[stationList.size-1].stationNm)
                        intent.putExtra("routeNum",routeNum)
                        intent.putExtra("routeId",routeId)
                        intent.putExtra("stationList",stationList)
                        applicontext.startActivity(intent)

                        //ArrayList에 담긴 station목록들을 intent에 담아서 stationsActivity에 넘겨야지
                        Log.d("뭐뭐들어가지? 5","뭐뭐들어가지? 5")
                    }
                }
            }

            override fun onFailure(call: Call<Bus>, t: Throwable) {
                Log.e("retrofit onFailure", "${t.printStackTrace()}")
            }
        })
    }

    fun getStationList(body: Bus) : ArrayList<StationModel> {
        val direction = body.msgBody.itemList[0].direction
        Log.d("뭐뭐들어가지? 4","뭐뭐들어가지? 4")
        var stationList : ArrayList<StationModel> = ArrayList()
        for (i in 0..body.msgBody.itemList.size) {
            val item = body.msgBody.itemList[i]
            if (item.direction != direction){ // 방향이 반대이면 중단 (같은 방향끼리만 출력)
                break
            }
            //stList += item.stationNm + '\n' // 정류소 이름 계속 붙여나가기
            //이곳에서 모든 정류장 list가 담기는듯한데..전부 ArrayList에 담고서.. return하자
            stationList.add(StationModel( item.stationNm , item.station ,(i+1).toString()))
            //viewModel.addItem(StationModel(item.stationNm, item.station)) // 정류소 이름, ID 목록으로 붙이기
            Log.d("Api", body.msgBody.itemList[i].stationNm)
        }

        // 정류소 이름 목록들 텍스트뷰에 붙이기
        //binding.textStation.text = stList
        //다 채운 station들을(ArrayList) return
        return stationList
    }


}