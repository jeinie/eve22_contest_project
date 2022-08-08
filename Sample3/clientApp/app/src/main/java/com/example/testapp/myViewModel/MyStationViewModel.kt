package com.example.testapp.myStationViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// 버스 번호(노선 번호) + 버스 노선 ID
data class StationData (val stationNm:String, val station:String) // 정류소 이름, 정류소 아이디

class MyStationViewModel : ViewModel() {
    val itemsListData = MutableLiveData<ArrayList<StationData>>()
    val items = ArrayList<StationData>()

    fun addItem(item: StationData) {
        items.add(item)
        itemsListData.value = items // 라이브데이터 바뀐 것 옵저버에게 알림
    }

    fun deleteItem() {
        items.removeAll(items.toSet())
    }
}
