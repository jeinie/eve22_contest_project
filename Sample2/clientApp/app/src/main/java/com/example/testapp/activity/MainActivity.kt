package com.example.testapp.activity

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.data.Bus
import com.example.testapp.Adapter.CustomAdapter
import com.example.testapp.SeoulOpenApi
import com.example.testapp.SeoulOpenService
import com.example.testapp.databinding.ActivityMainBinding
import com.example.testapp.myViewModel.MyViewModel
import com.example.testapp.myViewModel.StationData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    lateinit var retrofit: Retrofit
    lateinit var service: SeoulOpenService
    lateinit var binding : ActivityMainBinding
    private val viewModel by viewModels<MyViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //맨위 보라색 상태바 색상 변경
        window.statusBarColor = Color.parseColor("#FFFFFF")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = CustomAdapter(viewModel)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)

        viewModel.itemsListData.observe(this) { // 데이터에 변화가 있을 때 어댑터에게 변경을 알림
            adapter.notifyDataSetChanged() // 어댑터가 리사이클러뷰에게 알려 내용을 갱신함
        }

        retrofit = Retrofit.Builder()
            .baseUrl(SeoulOpenApi.DOMAIN)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(SeoulOpenService::class.java)


        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(busNum: String?): Boolean {
                //제출을 함 (검색 버튼을 눌렀을때)

                Log.d("확인", "입력한 버스넘버 : $busNum")

                //ex ) 10000로 검색을하면 안떠야 하잖아요?
                //그니까 api를 불러서 버스 고유 id가 return되어오면 있는 버스겠죠
                //if (있는 버스면 recyclerview에 출력이 되게) ----------------- 여기까지 완료

                if (busNum != null) {
                    service.getBus(busNum, "json").enqueue(object : Callback<Bus> {
                        override fun onResponse(call: Call<Bus>, response: Response<Bus>) {
                            if (response.isSuccessful) {
                                println(response.body().toString())
                                println(response.body()?.msgBody.toString())
                                if (response.body()?.msgBody?.itemList != null) {
                                    val routeId = response.body()!!.msgBody.itemList[0].busRouteId
                                    val routeNm = response.body()!!.msgBody.itemList[0].busRouteNm
                                    viewModel.addItem(StationData(routeNm, routeId))
                                }
                            }
                        }


                        //api 로 4312 번호 보내서 4312번 버스의 busid 받아와야함.
                        //아래 쫙 출력될 리스트에 (4312, 버스 고유 아이디 ex(10101011)) 이 출력되어야하고

                        //list 목록중 하나를 클릭하면 4312 텍스트를 가지고 api를 호출하면 됨
                        //지금현재는 4312를 타이핑해서 검색버튼 누르는 상황일텐데
                        //목록중 4312 버스를 클릭해서 다른 화면으로 넘어가서 정류소 목록 나오게 해야함.


                        override fun onFailure(call: Call<Bus>, t: Throwable) {
                            Log.e("retrofit onFailure", "${t.printStackTrace()}")
                        }
                    })
                }

                return true
            }

            override fun onQueryTextChange(busNum: String?): Boolean {
                //입력중인 상태

                return true
            }
        })
    }
}