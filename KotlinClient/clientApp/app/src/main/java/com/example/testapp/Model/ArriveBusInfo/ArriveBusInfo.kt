package com.example.testapp.Model.ArriveBusInfo

import java.io.Serializable

//arrmsg는 도착예정 정보 보통 ([막차/3번째/6분27초후[3번째 전]]) 이런식
//vehid는 도착예정 버스의 "버스고유 ID" 그니까 노선id랑 별개란 말이다
data class ArriveBusInfo(val arrmsg : String , val vehId : String, val routeNum : String) : Serializable
