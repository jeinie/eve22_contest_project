package com.example.testapp.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.Model.RouteModel.RouteModel
import com.example.testapp.Model.StationModel.StationModel
import com.example.testapp.R

class DrivingCustomAdapter (var listData : ArrayList<StationModel>, var onRouteClickedListener:OnRouteClickedListener) : RecyclerView.Adapter<DrivingCustomAdapter.ViewHolder>() {
    interface OnRouteClickedListener {
        fun onRouteClicked(model: StationModel)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var stName: TextView = itemView.findViewById(R.id.stName)
        var stId: TextView = itemView.findViewById(R.id.stId)
        var ord: TextView = itemView.findViewById(R.id.ord)
    }

    // ViewHolder 생성, ViewHolder는 View를 담는 상자
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        // 아이템 레이아웃을 객체화
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.activity_driving_station_list, parent, false)
        return ViewHolder(view)
    }

    // ViewHolder에 데이터 쓰기
    override fun onBindViewHolder(holder: ViewHolder, position: Int) { // 각 아이템 뷰에 데이터를 셋팅
        //여기 다시체크해야한다 말이안되는데..
        var item = listData[position]
        //holder.routeId.text = item.msgBody.itemList[position].busRouteId
        holder.ord.text = (position + 1).toString()
        holder.stName.text = item.stationNm
        holder.stId.text = item.stationId

        //변화 : setonClickListener추가해준부분
        holder.itemView.setOnClickListener {
            //item은 arrayList의 position에 해당되는 item을 가져온것
            //onRouteClickedListener.onRouteClicked(item)
            onRouteClickedListener.onRouteClicked(item)
        }
    }

    override fun getItemCount(): Int {
        return listData.size
    }
}