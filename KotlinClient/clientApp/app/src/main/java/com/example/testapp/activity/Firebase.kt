package com.example.testapp.activity

import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.firebase.database.ktx.database
import androidx.appcompat.app.AppCompatActivity
import com.example.testapp.databinding.ActivityBusListAtStationBinding
import com.example.testapp.databinding.ActivityFirebaseBinding
import com.google.firebase.ktx.Firebase

class Firebase : AppCompatActivity() {
    lateinit var binding : ActivityFirebaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirebaseBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val database = Firebase.database
        val myRef = database.getReference("message")

        //val button = findViewById<Button>(R.id.fbBtn)
        val btn = binding.fbBtn
        btn.setOnClickListener {
            myRef.push().setValue("Hello, World!")
            Log.d("클릭됨 ","버튼 클릭됨")
        }
    }
}