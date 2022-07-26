package com.example.testapp.myViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// 버스 번호(노선 번호) + 버스 노선 ID
data class BusData (val routeNm:String, val routeId:String)

class MyViewModel : ViewModel() {
    val itemsListData = MutableLiveData<ArrayList<BusData>>()
    val items = ArrayList<BusData>()

    fun addItem(item: BusData) {
        items.add(item)
        itemsListData.value = items // 라이브데이터 바뀐 것 옵저버에게 알림
    }

    fun deleteItem() {
        items.removeAll(items.toSet())
    }
}