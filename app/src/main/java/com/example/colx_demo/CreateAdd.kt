package com.example.colx_demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.colx_demo.databinding.ActivityCreateAddBinding
import com.example.colx_demo.databinding.ActivityMainBinding
import com.example.colx_demo.databinding.ActivitySplashScreenBinding

class CreateAdd : AppCompatActivity() {

    private lateinit var binding : ActivityCreateAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateAddBinding.inflate(layoutInflater)
        setContentView(binding.root)





    }
}