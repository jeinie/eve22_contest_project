package com.example.testapp.activity

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.testapp.Adapter.MainCustomAdapter
import com.example.testapp.Model.BusModel.Bus
import com.example.testapp.Model.BusModel.ExampleModel
import com.example.testapp.controller.mainController.MainController
import com.example.testapp.databinding.ActivityMainBinding
// , MainCustomAdapter.OnRouteClickedListener
class MainActivity : AppCompatActivity(){
    lateinit var binding : ActivityMainBinding
    //mainContainer 객체화
    lateinit var mc : MainController
    //mainCustomeAdapter 객체화
    lateinit var mca : MainCustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //맨위 보라색 상태바 색상 변경
        window.statusBarColor = Color.parseColor("#FFFFFF")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mc = MainController()


        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(busNum: String?): Boolean {
                //제출을 함 (검색 버튼을 눌렀을때)
                //ㅂㅓ스를 검색하면 그 버스가 있으면
                Log.d("확인","입력한 버스넘버 : $busNum")

                //recyclerview 잘나오나 테스트
                var routeIdList = ArrayList<ExampleModel>()
                routeIdList.add( ExampleModel("4312") )
                routeIdList.add( ExampleModel("3333") )
                routeIdList.add( ExampleModel("777") )
                mca = MainCustomAdapter(routeIdList,object : MainCustomAdapter.OnRouteClickedListener{
                    override fun onRouteClicked(model: ExampleModel) {

                        //Toast.makeText(baseContext, "${model.routeId} , ",Toast.LENGTH_SHORT).show()
                        //현재 toast는 model의 routeId 즉 4312가 뜨는 상태이다.
                        //고로 routeId를 넘겨서 버스가 지나는 station들을 뽑아와야 한다
                        mc.loadBus(model.routeId,applicationContext)
                        Log.d("1","1")
                    }
                })
                binding.mainrecyclerView.adapter = mca
                mca.notifyDataSetChanged()
                //recyclerview 잘나오나 테스트
                return true
            }
            override fun onQueryTextChange(busNum: String?): Boolean {
                //입력중인 상태
                return true
            }




        })
    }
}