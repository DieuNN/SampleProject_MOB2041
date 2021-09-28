package com.example.sampleproject_mob2041.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.example.sampleproject_mob2041.R
import com.example.sampleproject_mob2041.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupDrawerLayout(binding.drawerLayoutToolbar, binding.drawerLayout, R.string.app_name)

    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }

    private fun setupDrawerLayout(toolbar: Toolbar, drawerLayout: DrawerLayout, toolbarTitle: Int) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = resources.getString(toolbarTitle)

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }
}