package com.example.testapp.Model.BusModel

data class Item (
    val busRouteAbrv: String,
    val busRouteId: String,
    val busRouteNm: String,
    val direction:String,
    val corpNm: String,
    val edStationNm: String,
    val firstBusTm: String,
    val firstLowTm: String,
    val lastBusTm: String,
    val lastBusYn: String,
    val lastLowTm: String,
    val length: String,
    val routeType: String,
    val stStationNm: String,
    val term: String,
    val seq: String, // 정류소 순번
    val section: String,
    val station: String, // 정류소 아이디
    val arsId: String,
    val stationNm: String, // 정류소 이름
    val gpsX: String,
    val gpsY: String,
    val posX: String,
    val posY: String,
    val vehId1: String, // 첫번째 도착예정버스 ID
    val plainNo1:String, // 첫번째 도착예정 차량번호
    //val sectOrd1:String // 첫번째 도착예정버스 현재구간 순번
    //도착예정 정보때문에 아래추가
    val sectOrd1 : String,//sectOrd1 : 도착예정버스의 현재구간 순번
    val traTime1 : String, //도착예정버스의 여행시간..?
    val exps1 : String, //무슨..? 첫번째 도착예정버스의 지수평활 도착예정시간 (초)
    val exps2 : String, //허허..? 두번째 도착예정버스의 지수평활 도착예정시간 (초)
    val kals1 : String, //첫번째 도착에정 버스의 기타1 도착예정시간 (초)
    val kals2 : String //두번째 도착예정 버스의 기타1 도착예정시간 (초)



)