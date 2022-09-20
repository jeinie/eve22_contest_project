package com.example.testapp.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.Model.ArriveBusInfo.ArriveBusInfo
import com.example.testapp.R

class BusesAtStationCustomeAdapter (var listData : ArrayList<ArriveBusInfo>, var onRouteClickedListener:OnRouteClickedListener) : RecyclerView.Adapter<BusesAtStationCustomeAdapter.ViewHolder>(){
        interface OnRouteClickedListener{
            fun onRouteClicked(model : ArriveBusInfo)
        }

        inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
            var routeNumTextView : TextView = itemView.findViewById(R.id.routeNumTextView)
            var busArrivedTime : TextView = itemView.findViewById(R.id.busArrivedTime)
            var vehId : TextView = itemView.findViewById(R.id.vehId)
        }
        // ViewHolder 생성, ViewHolder는 View를 담는 상자
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            Log.d("여기 오긴하나1","오긴하나1")
            // 아이템 레이아웃을 객체화
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.activity_bus_list_at_station_bus_list,parent,false)
            return ViewHolder(view)
        }

        // ViewHolder에 데이터 쓰기
        override fun onBindViewHolder(holder : ViewHolder, position: Int) { // 각 아이템 뷰에 데이터를 셋팅
            //여기 다시체크해야한다 말이안되는데..
            Log.d("왜안떠??", listData.toString())
            Log.d("여기 오긴하나2","오긴하나2")
            var item = listData[position]

            holder.busArrivedTime.text = item.arrmsg
            holder.routeNumTextView.text = item.routeNum
            holder.vehId.text = item.vehId
            Log.d("오는지확인","${item.arrmsg}")
            Log.d("오는지확인2","${item.routeNum}")
            Log.d("오는지확인3","${item.vehId}")
            //변화 : setonClickListener추가해준부분
            holder.itemView.setOnClickListener {
                //item은 arrayList의 position에 해당되는 item을 가져온것
                onRouteClickedListener.onRouteClicked(item)
            }
        }

        override fun getItemCount(): Int {
            return listData.size
        }

}