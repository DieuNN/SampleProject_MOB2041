package com.example.sampleproject_mob2041.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.example.sampleproject_mob2041.databinding.ActivityMainBinding
import android.view.View

import android.widget.TextView
import android.widget.Toast
import com.example.sampleproject_mob2041.R

import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mIntent: Intent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mIntent = intent

        setupDrawerLayout(binding.drawerLayoutToolbar, binding.drawerLayout, R.string.app_name)

    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }

    private fun setupDrawerLayout(toolbar: Toolbar, drawerLayout: DrawerLayout, toolbarTitle: Int) {
        val navigationView = findViewById<View>(R.id.drawerLayoutNavigationView) as NavigationView
        val headerView: View = navigationView.getHeaderView(0)
        val headerText = headerView.findViewById<TextView>(R.id.drawer_header_username)

        headerText.text = "Xin chào: ${mIntent.getStringExtra("username")}"

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = resources.getString(toolbarTitle)

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        Toast.makeText(this, mIntent.getStringExtra("user_type"), Toast.LENGTH_SHORT).show()

        if (mIntent.getStringExtra("user_type") == "admin") {
            setMenuDependOnUser(true)
        } else {
            setMenuDependOnUser(false)
        }


    }

    private fun setMenuDependOnUser(isAdmin:Boolean) {
        if(isAdmin) {
            binding.drawerLayoutNavigationView.menu.clear()
            binding.drawerLayoutNavigationView.inflateMenu(R.menu.admin_menu)
        } else{
            binding.drawerLayoutNavigationView.menu.clear()
            binding.drawerLayoutNavigationView.inflateMenu(R.menu.normal_user_menu)
        }
    }
}