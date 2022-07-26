package com.example.testapp.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.activity.ArrivalActivity
import com.example.testapp.databinding.RecyclerStationBinding
import com.example.testapp.myStationViewModel.MyStationViewModel

class StationCustomAdapter(private val viewModel: MyStationViewModel) : RecyclerView.Adapter<StationCustomAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: RecyclerStationBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setContents(pos: Int) { // item 으로 데이터를 가져와 View에 셋팅.
            with(viewModel.items[pos]) {
                binding.stationNm.text = stationNm // 정류소 이름
                binding.station.text = station // 정류소 아이디
            }
        }
    }

    // ViewHolder 생성, ViewHolder는 View를 담는 상자
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {

        // 아이템 레이아웃을 객체화
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val binding = RecyclerStationBinding.inflate(layoutInflater, viewGroup, false)
        return ViewHolder(binding)
    }

    // ViewHolder에 데이터 쓰기
    override fun onBindViewHolder(viewHolder: StationCustomAdapter.ViewHolder, position: Int) { // 각 아이템 뷰에 데이터를 셋팅
        viewHolder.setContents(position)

        // 리사이클러뷰 아이템 클릭시 버스도착 화면으로 넘어가기
        viewHolder.itemView.setOnClickListener {
            //println(viewHolder.itemView.context)
            //Toast.makeText(viewHolder.itemView.context, "Clicked", Toast.LENGTH_SHORT).show()
            val intent = Intent(viewHolder.itemView?.context, ArrivalActivity::class.java)
                .apply {
                    putExtra("stationId", viewModel.items[position].station) // 정류장 아이디
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    println("${viewModel.items[position].station}")
                }
            viewHolder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = viewModel.items.size // 데이터의 총 크기
}