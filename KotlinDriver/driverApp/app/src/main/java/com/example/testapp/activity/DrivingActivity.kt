package com.example.testapp.activity

import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.testapp.databinding.ActivityDrivingBinding
import com.example.testapp.databinding.ActivityIndexBinding

class DrivingActivity : AppCompatActivity() {
    lateinit var binding : ActivityDrivingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = Color.parseColor("#000000")
        binding = ActivityDrivingBinding.inflate(layoutInflater)
        setContentView(binding.root)




    }

}