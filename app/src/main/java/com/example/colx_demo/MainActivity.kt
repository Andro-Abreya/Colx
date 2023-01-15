package com.example.colx_demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.colx_demo.add_product.choose_category.CreateAdd
import com.example.colx_demo.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: com.example.colx_demo.databinding.ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()

        binding.bottomNavigation.menu.getItem(2).isEnabled =false

        binding.fab.setOnClickListener {

            val intent = Intent(this, CreateAdd ::class.java)
            startActivity(intent)
        }



        val navView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment, R.id.chatFragment,R.id.placeholderFragment,R.id.myAddFragment,R.id.accountFragment
        )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


    }
}