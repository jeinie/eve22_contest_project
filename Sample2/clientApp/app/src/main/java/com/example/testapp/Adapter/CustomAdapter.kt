package com.example.testapp.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.databinding.RecyclerBusBinding
import com.example.testapp.myViewModel.MyViewModel

class CustomAdapter(private val viewModel: MyViewModel) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: RecyclerBusBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setContents(pos: Int) { // item 으로 데이터를 가져와 View에 셋팅.
            with(viewModel.items[pos]) {
                binding.routeNm.text = routeNm
                binding.routeId.text = routeId
            }
        }
    }

    // ViewHolder 생성, ViewHolder는 View를 담는 상자
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {

        // 아이템 레이아웃을 객체화
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val binding = RecyclerBusBinding.inflate(layoutInflater, viewGroup, false)
        return ViewHolder(binding)
    }

    // ViewHolder에 데이터 쓰기
    override fun onBindViewHolder(viewHolder: CustomAdapter.ViewHolder, position: Int) { // 각 아이템 뷰에 데이터를 셋팅
        viewHolder.setContents(position)

        // 리사이클러뷰 아이템 클릭시 정류소 목록 화면으로 넘어가기
        viewHolder.itemView.setOnClickListener {
            println(viewHolder.itemView.context)
            Toast.makeText(viewHolder.itemView.context, "Clicked", Toast.LENGTH_SHORT).show()
            //val intent = Intent(viewHolder.itemView?.context, ArrivalBus::class.java)
            //val routeId = intent.getStringExtra("routeId")
            //val ord = intent.getStringExtra("seq")

            //intent.putExtra("stId", viewModel.items[position].stationId)
            //intent.putExtra("busRouteId", routeId) // MainActivty에서 가져온 노선 ID 보내기
            //intent.putExtra("ord", ord) // MainActivity에서 가져온 순번 보내기

            //ContextCompat.startActivity(viewHolder.itemView.context, intent, null)
        }
    }

    override fun getItemCount() = viewModel.items.size // 데이터의 총 크기
}