package com.example.sample

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// 정류소 이미지 + 정류소 이름 + 정류소 ID
data class StationData (val stationNm:String, val stationId:String)

class MyViewModel : ViewModel() {
    val itemsListData = MutableLiveData<ArrayList<StationData>>()
    val items = ArrayList<StationData>()

    fun addItem(item: StationData) {
        items.add(item)
        itemsListData.value = items // 라이브데이터 바뀐 것 옵저버에게 알림
    }
}