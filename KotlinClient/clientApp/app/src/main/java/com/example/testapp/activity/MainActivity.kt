package com.example.testapp.activity

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.testapp.Adapter.MainCustomAdapter
import com.example.testapp.Model.BusModel.Bus
import com.example.testapp.Model.BusModel.ExistedStationModel
import com.example.testapp.Model.KeyModel.ApiKeyOne
import com.example.testapp.Model.RouteModel.RouteModel
import com.example.testapp.Service.getBus
import com.example.testapp.controller.mainController.MainController
import com.example.testapp.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

// , MainCustomAdapter.OnRouteClickedListener
class MainActivity : AppCompatActivity(), CoroutineScope {
    //코루틴
    private lateinit var job : Job
    override val coroutineContext : CoroutineContext
        get() = Dispatchers.Main + job
    //코루틴

    lateinit var binding : ActivityMainBinding
    //mainContainer 객체화
    lateinit var mc : MainController
    //mainCustomeAdapter 객체화
    lateinit var mca : MainCustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()

        //맨위 보라색 상태바 색상 변경
        window.statusBarColor = Color.parseColor("#000000")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mc = MainController()

        if (Build.VERSION.SDK_INT > 9) {
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(busNum: String?): Boolean {
                var routeNum : String = busNum!!


                launch {
                    var routeIdList = mc.isRouteExist( routeNum )
                    Log.d("선생님..정말로?", routeIdList.toString())
                    if (routeIdList[0].routeNm == ""){
                        Toast.makeText(baseContext,"해당 노선정보가 없습니다",Toast.LENGTH_SHORT).show()
                        routeIdList.clear()
                    }
                    mca = MainCustomAdapter(routeIdList ,object : MainCustomAdapter.OnRouteClickedListener{
                        override fun onRouteClicked(model: RouteModel) {

                            mc.loadBus(model.routeNm , applicationContext)
                            Log.d("1","1")
                        }
                    })
                    binding.mainrecyclerView.adapter = mca
                    mca.notifyDataSetChanged()
                    //recyclerview 잘나오나 테스트
                }
                return true
            }
            override fun onQueryTextChange(busNum: String?): Boolean {
                //입력중인 상태
                return true
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}

