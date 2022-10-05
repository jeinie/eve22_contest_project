package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.PayInfo.PayInfo
import com.example.myapplication.Service.tossPay
import com.example.myapplication.databinding.ActivityMainBinding
import okhttp3.OkHttpClient
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val amount = 1250 // 버스 요금
        val passengerId = "aaaa1234" // 승객 아이디 또는 이름??

        binding.pay.setOnClickListener {
            val cardNumber = binding.cardNumber.text.toString()
            val cardExpirationYear = binding.cardExpirationYear.text.toString()
            val cardExpirationMonth = binding.cardExpirationMonth.text.toString()
            val cardPassword = binding.cardPassword.text.toString()

            val retrofit = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3030")
                .addConverterFactory(GsonConverterFactory.create()).build()
            val service = retrofit.create(tossPay::class.java)

            service.payBus(amount, passengerId, cardNumber, cardExpirationYear, cardExpirationMonth, cardPassword).enqueue(object : Callback<PayInfo> {
                override fun onResponse(call: Call<PayInfo>, response: Response<PayInfo>) {
                    if (response.isSuccessful) {
                        Log.d("결제 버튼 눌림", "결제 버튼 눌림")
                        Log.d("응답 내용 : ", response.code().toString())
                    }
                }

                override fun onFailure(call: Call<PayInfo>, t: Throwable) {
                    Log.d("error", t.toString())
                    Log.d("res", "onResponse 실패")
                }
            })
            /*val client = OkHttpClient()

            val mediaType = MediaType.parse("application/json")
            val body = RequestBody.create(mediaType, "{\"cardNumber\":\"4330123412341234\",\"cardExpirationYear\":\"24\",\"cardExpirationMonth\":\"07\",\"cardPassword\":\"12\",\"customerIdentityNumber\":\"881212\",\"customerKey\":\"X6vlPYuR_MCe0TqykV7yc\"}")
            val request = Request.Builder()
                .url("https://api.tosspayments.com/v1/billing/rG5uMLcqr0kuDcqMkeUh0-tVlJq9QMnRlmYtRest2ug=")
                .post(body)
                .addHeader("Authorization", "Basic dGVzdF9za19PeUwwcVo0RzFWT0xvYkI2S3d2cm9XYjJNUVlnOg==")
                .addHeader("Content-Type", "application/json")
                .build()

            Log.d("요청 내용: ", request.toString())
            //val response = client.newCall(request).execute()
            //Log.d("응답 결과: ", response.toString())*/
        }
    }
}