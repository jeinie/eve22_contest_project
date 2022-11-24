package com.example.testapp.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.location.Location
import android.os.*
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import com.example.buspassenger.Model.RegisterInfo
import com.example.buspassenger.Service.getReservation
import com.example.testapp.Model.BusCoordinateModel.BusCoordinateModel
import com.example.testapp.R
import com.example.testapp.Service.cancelReservation
import com.example.testapp.controller.ReservationAfterController.ReservationAfterController
import com.example.testapp.databinding.ActivityReservationAfterBinding
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import kotlin.coroutines.CoroutineContext
import kotlin.reflect.typeOf

@RequiresApi(Build.VERSION_CODES.S)
class ReservationAfterActivity : AppCompatActivity() , OnMapReadyCallback , CoroutineScope  {

    //코루틴
    private lateinit var job : Job
    override val coroutineContext : CoroutineContext
        get() = Dispatchers.Main + job
    //코루틴

    lateinit var binding : ActivityReservationAfterBinding
    lateinit var rac : ReservationAfterController
    //lateinit var basca : BusesAtStationCustomeAdapter
    private lateinit var mMap: GoogleMap

    private lateinit var fusedLocationClient: FusedLocationProviderClient // 위칫값 사용
    private lateinit var locationCallback: LocationCallback // 위칫값 요청에 대한 갱신 정보를 받아옴
    private lateinit var tmx : String
    private lateinit var tmy : String
    private lateinit var vehId : String
    private lateinit var stName : String
    private lateinit var routeNum : String
    private lateinit var busCoordinate : BusCoordinateModel
    private lateinit var arrmsg : String
    var alarmMsg : Long = 0

    /**
     * 바이브레이터 떄문에 추가한 코드
     */


    private lateinit var discriptor : BitmapDescriptor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.parseColor("#90008000")
        binding = ActivityReservationAfterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        rac = ReservationAfterController()
        job = Job()
        // 사용할 권한 array로 저장
        val permissions = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION)

        requirePermissions(permissions, 999) //권환 요쳥, 999는 임의의 숫자
        tmx = intent.getStringExtra("tmX")!!
        tmy = intent.getStringExtra("tmY")!!
        vehId = intent.getStringExtra("vehId")!!
        stName = intent.getStringExtra("stName")!!
        routeNum = intent.getStringExtra("routeNum")!!
        arrmsg = intent.getStringExtra("arrmsg")!!
        alarmMsg = arrmsg.split("[")[1][0].toString().toLong()


        //백그라운드에서 돌릴 service에 인텐트까지 넘기는 코드
        val serviceIntent = Intent(this@ReservationAfterActivity,ArriveAlarmService::class.java)
        serviceIntent.putExtra("alarmMsg", alarmMsg)

        Log.d("뭘 넘기기는하는건가?",alarmMsg.toString())
        Log.d("뭘 넘기기는하는건가? 타입은? ", alarmMsg.javaClass.name)

        serviceIntent.putExtra("stName",stName)
        serviceIntent.putExtra("routeNum",routeNum)
        startService(serviceIntent)

        //백그라운드에서 돌릴 service에 인텐트까지 넘기는 코드


        binding.stNameTextView.text = stName
        binding.routeNumTextView.text = routeNum

        if ( Build.VERSION.SDK_INT > 9 ){
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }



    }



    fun startProcess() {
        // SupportMapFragment를 가져와서 지도가 준비되면 알림을 받습니다.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapview) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /** 권한 요청*/
    fun requirePermissions(permissions: Array<String>, requestCode: Int) {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            permissionGranted(requestCode)
        } else {
            val isAllPermissionsGranted = permissions.all { checkSelfPermission(it) == PackageManager.PERMISSION_GRANTED }
            if (isAllPermissionsGranted) {
                permissionGranted(requestCode)
            } else {
                ActivityCompat.requestPermissions(this, permissions, requestCode)
            }
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
            permissionGranted(requestCode)
        } else {
            permissionDenied(requestCode)
        }
    }

    // 권한이 있는 경우 실행
    fun permissionGranted(requestCode: Int) {

        startProcess() // 권한이 있는 경우 구글 지도를준비하는 코드 실행

    }

    // 권한이 없는 경우 실행
    fun permissionDenied(requestCode: Int) {
        Toast.makeText(this
            , "권한 승인이 필요합니다."
            , Toast.LENGTH_LONG)
            .show()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        updateLocation()

        var bitmapDrawable: BitmapDrawable

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            bitmapDrawable = getDrawable(R.drawable.marker) as BitmapDrawable
        } else {
            bitmapDrawable = resources.getDrawable(R.drawable.marker) as BitmapDrawable
        }
        var scaledBitmap = Bitmap.createScaledBitmap(bitmapDrawable.bitmap, 55, 55, false)
        discriptor = BitmapDescriptorFactory.fromBitmap(scaledBitmap)
    }

    // 위치 정보를 받아오는 역할
    @SuppressLint("MissingPermission") //requestLocationUpdates는 권한 처리가 필요한데 현재 코드에서는 확인 할 수 없음. 따라서 해당 코드를 체크하지 않아도 됨.
    fun updateLocation() {

        val locationRequest = LocationRequest.create().apply{
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 5000
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {

                //locationResult?.let {
                launch {
                    for(location in locationResult!!.locations) {
                        Log.d("뭐 어떻게 되는건지","뭐 어떻게 되는건지")
                        busCoordinate = rac.getBusPositionRepeat(vehId = vehId)

                        tmy = busCoordinate.tmy.toString()
                        tmx = busCoordinate.tmx.toString()
                        location.latitude = tmy.toDouble()
                        location.longitude = tmx.toDouble()

                        Log.d("엥??","tmx : ${tmx.toDouble()} , tmy : ${tmy.toDouble()}")
                        setLastLocation(location)
                        delay(5000L)

                    }
                    //}
                }

            }
        }

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())

    }

    fun setLastLocation(lastLocation: Location) {
        val LATLNG = LatLng(lastLocation.latitude, lastLocation.longitude)

        var markerOptions = MarkerOptions()
            .position(LATLNG)
            .title("Marker in Seoul City Hall")
            .icon(discriptor)

        mMap.addMarker(markerOptions)

        val cameraPosition = CameraPosition.Builder()
            .target(LATLNG)
            .zoom(13.0f)
            .build()
        mMap.clear()
        mMap.addMarker(markerOptions)
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }


    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBackPressed() {
        //문제생길듯..
        /* 버스 서버 , 버스 기사 서버 관리가 더는 필요없어졌으니.. 주석처리함이 맞다 job.cancel()과 finish()는 밖으로 빼준다
        val retrofit = Retrofit.Builder().baseUrl("http://10.0.2.2:3000")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val service = retrofit.create(cancelReservation::class.java)

        service.cancelBusReservation(vehId, stName).enqueue(object :
            Callback<RegisterInfo> {
            override fun onResponse(call: Call<RegisterInfo>, response: Response<RegisterInfo>) {
                Log.d("버튼 눌림 3 ", "버튼 눌렸습니다 3")
                if (response.isSuccessful) {
                    //Log.d("res", response.body().toString())
                    job.cancel()
                    Toast.makeText(baseContext ,"예약이 취소되었습니다",Toast.LENGTH_SHORT).show()
                    finish()
                }
            }

            override fun onFailure(call: Call<RegisterInfo>, t: Throwable) {
                Log.d("버튼 눌림 4", "버튼 눌렸습니다 4")
                Log.d("res", "onResponse 실패")
            }
        })
        */
        //문제생길듯..
        job.cancel()
        Toast.makeText(baseContext ,"예약이 취소되었습니다",Toast.LENGTH_SHORT).show()
        finish()

    }


}

