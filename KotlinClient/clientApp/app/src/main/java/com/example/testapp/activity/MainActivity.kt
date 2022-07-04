package com.example.testapp.activity

import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.example.testapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(busNum: String?): Boolean {
                //제출을 함 (검색 버튼을 눌렀을때)

                Log.d("확인","입력한 버스넘버 : $busNum")
                return true
            }

            override fun onQueryTextChange(busNum: String?): Boolean {
                //입력중인 상태

                return true
            }

        })

    }
}