package com.example.sampleproject_mob2041.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.example.sampleproject_mob2041.databinding.ActivityMainBinding
import android.view.View

import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import com.example.sampleproject_mob2041.InitDataExample
import com.example.sampleproject_mob2041.R
import com.example.sampleproject_mob2041.fragment.*

import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mIntent: Intent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mIntent = intent

        setupDrawerLayout(binding.drawerLayoutToolbar, binding.drawerLayout, R.string.app_name)
        supportFragmentManager.beginTransaction()
            .replace(R.id.drawerLayoutFrameLayout, CallCardManagementFragment()).commit()
        binding.drawerLayoutNavigationView.setNavigationItemSelectedListener(this)

//        InitDataExample().initData(applicationContext)

    }


    override fun onBackPressed() {
        AlertDialog.Builder(this).apply {
            setTitle("Xác nhận thoát")
            setMessage("Bạn chắc chắn muốn thoát?")
            setPositiveButton("Thoát") { _, _ ->
                finish()
            }
            setNegativeButton("Hủy") { dialog, _ ->
                dialog.dismiss()
            }
        }.create().show()

    }

    private fun setupDrawerLayout(toolbar: Toolbar, drawerLayout: DrawerLayout, toolbarTitle: Int) {
        val navigationView = findViewById<View>(R.id.drawerLayoutNavigationView) as NavigationView
        val headerView: View = navigationView.getHeaderView(0)
        val headerText = headerView.findViewById<TextView>(R.id.drawer_header_username)



        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = resources.getString(toolbarTitle)

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()


        if (mIntent.getStringExtra("user_type") == "admin") {
            // true equals to admin
            setMenuDependOnUser(true)
            headerText.text = "Xin chào: ${mIntent.getStringExtra("user_type")}"
            Toast.makeText(
                this,
                "${applicationContext.getText(R.string.hello)}: ${mIntent.getStringExtra("user_type")}",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            setMenuDependOnUser(false)
            Toast.makeText(
                this,
                "${applicationContext.getText(R.string.hello)}: ${mIntent.getStringExtra("display_name")}",
                Toast.LENGTH_SHORT
            ).show()
            headerText.text = "Xin chào: ${mIntent.getStringExtra("display_name")}"
        }


    }

    private fun setMenuDependOnUser(isAdmin: Boolean) {
        if (isAdmin) {
            binding.drawerLayoutNavigationView.menu.clear()
            binding.drawerLayoutNavigationView.inflateMenu(R.menu.admin_menu)
        } else {
            binding.drawerLayoutNavigationView.menu.clear()
            binding.drawerLayoutNavigationView.inflateMenu(R.menu.normal_user_menu)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val transaction = supportFragmentManager.beginTransaction()
        when (item.itemId) {
            R.id.admin_menu_call_card, R.id.normal_user_menu_call_card_management -> transaction.replace(
                R.id.drawerLayoutFrameLayout,
                CallCardManagementFragment()
            ).commit()
            R.id.admin_menu_book_management, R.id.normal_user_menu_book_management -> transaction.replace(
                R.id.drawerLayoutFrameLayout,
                BookManagementFragment()
            ).commit()
            R.id.admin_menu_genre_management, R.id.normal_user_menu_genre_management -> transaction.replace(
                R.id.drawerLayoutFrameLayout,
                GenreManagementFragment()
            ).commit()
            R.id.admin_menu_customer_management, R.id.normal_user_menu_customer_management -> transaction.replace(
                R.id.drawerLayoutFrameLayout,
                CustomerManagementFragment()
            ).commit()
            R.id.admin_menu_top_10, R.id.normal_user_menu_top_10 -> transaction.replace(
                R.id.drawerLayoutFrameLayout,
                Top10Fragment()
            ).commit()
            R.id.admin_menu_income, R.id.normal_user_menu_income -> transaction.replace(
                R.id.drawerLayoutFrameLayout,
                IncomeAnalyticsFragment()
            ).commit()
            R.id.admin_menu_librarian_list -> transaction.replace(
                R.id.drawerLayoutFrameLayout,
                LibrarianManagementFragment()
            ).commit()
            R.id.normal_user_menu_edit_information -> transaction.replace(
                R.id.drawerLayoutFrameLayout,
                EditInformationFragment()
            ).commit()
            else ->
                AlertDialog.Builder(this).apply {
                    setTitle("Xác nhận đăng xuất")
                    setMessage("Bạn chắc chắn muốn đăng xuất?")
                    setPositiveButton("Đăng xuất") { _, _ ->
                        startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                        finish()
                    }
                    setNegativeButton("Hủy") { dialog, _ ->
                        dialog.dismiss()
                    }
                }.create().show()

        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)

        return true
    }
}