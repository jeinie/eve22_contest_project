package com.example.testapp.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.Model.BusModel.ExistedStationModel
import com.example.testapp.R

//main의 버스 노선 id로 recyclerview를 채우기위한 어댑터
                                                        //변화 :
class MainCustomAdapter(var listData : ArrayList<ExistedStationModel>, var onRouteClickedListener:OnRouteClickedListener) : RecyclerView.Adapter<MainCustomAdapter.ViewHolder>() {
    interface OnRouteClickedListener{
        fun onRouteClicked(model : ExistedStationModel)
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var routeId : TextView = itemView.findViewById(R.id.routeId)
        var routeNm : TextView = itemView.findViewById(R.id.routeNm)
    }

    // ViewHolder 생성, ViewHolder는 View를 담는 상자
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        // 아이템 레이아웃을 객체화
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.activity_main_routeid_list,parent,false)
        return ViewHolder(view)
    }

    // ViewHolder에 데이터 쓰기
    override fun onBindViewHolder(holder : ViewHolder, position: Int) { // 각 아이템 뷰에 데이터를 셋팅
        //여기 다시체크해야한다 말이안되는데..
        var item = listData[position]
        //holder.routeId.text = item.msgBody.itemList[position].busRouteId
        //holder.routeId.text = item.routeId
        Log.d("")
        holder.routeNm.text = item.stationNm
        holder.routeId.text = item.stationId
        //변화 : setonClickListener추가해준부분
        holder.itemView.setOnClickListener {
                                                    //item은 arrayList의 position에 해당되는 item을 가져온것
            onRouteClickedListener.onRouteClicked(item)
        }
    }

    override fun getItemCount(): Int {
        Log.d("listData : ",listData.toString())
        Log.d("listData : ",listData.size.toString())
        return listData.size
    }
}