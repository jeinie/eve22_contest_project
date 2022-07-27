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
    val plainNo1:String, // 첫번째 도착예정 차량번호
    //val sectOrd1:String // 첫번째 도착예정버스 현재구간 순번
    //도착예정 정보때문에 아래추가
    val sectOrd1 : String,//sectOrd1 : 도착예정버스의 현재구간 순번
    val traTime1 : String, //도착예정버스의 여행시간..?
    val exps1 : String, //무슨..? 첫번째 도착예정버스의 지수평활 도착예정시간 (초)
    val exps2 : String, //허허..? 두번째 도착예정버스의 지수평활 도착예정시간 (초)
    val kals1 : String, //첫번째 도착에정 버스의 기타1 도착예정시간 (초)
    val kals2 : String, //두번째 도착예정 버스의 기타1 도착예정시간 (초)
    val arrmsg1 : String, //이게 첫번째 버스의 도착예정 시간이라는데..
    val arrmsg2 : String, //이건그럼 2번쨰인가?
    val vehId1: String, // 첫번째 도착예정버스 ID 인데 2번째 도착예정버스는 없나? 그러면..이걸
    val vehId2: String // 밑은 무시. 찾았다 두번쨰 도착예정 버스의 ID
    //말이되게 할라면... "예약하기 버튼을 하나만 마지막페이지에 만들어놔서" 첫번째 도착예정버스에만 예약을 할수있게 해야하나
    //그 이유는 "돌아다니는 모든 버스에 마구잡이로 예약을 넣는 승객을 막으려면 그래야함"
    //사실상 버스 한대를 예약해놓으면 다른버스를 예약해놓지 못하게 하는 방법도 있는데,
    //그렇게되면 "이미 예약한 버스를 까먹고" 다른버스를 예약할때 아 왜 안되지..? 뭐야 할수도 있을것같아서
    //제일 처음 도착한 버스에만 예약을 할수있게 해놨다고 하는게 좋을것같다.
    //그럼 마지막 view는 recyclerview 일 필요가 없는데?
)