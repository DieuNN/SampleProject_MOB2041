package com.example.sampleproject_mob2041.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.sampleproject_mob2041.R
import com.example.sampleproject_mob2041.database.Database
import com.example.sampleproject_mob2041.database.LibrarianDB
import com.example.sampleproject_mob2041.databinding.ActivitySignInBinding
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var preferencesEditor: SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        sharedPreferences = getSharedPreferences("saved_info", MODE_PRIVATE)
        preferencesEditor = sharedPreferences.edit()
        setContentView(binding.root)

        if (sharedPreferences.getBoolean("remember", false)) {
            innerInfoFromSharedPreferences(sharedPreferences.getBoolean("remember", false))
        }

        binding.btnSignIn.setOnClickListener {
            if (isEmpty(binding.edtSignInUsername, binding.edtSignInPassword)) {
                Toast.makeText(this, "Bạn chưa nhập username hoặc password!", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            // check if admin, then put admin role to intent to set menu view, if not admin, then set the view to normal user
            if (!isAdmin(
                    binding.edtSignInUsername.text.toString(),
                    binding.edtSignInPassword.text.toString()
                )
            ) {
                if (checkUserExist(
                        binding.edtSignInUsername.text.toString(),
                        binding.edtSignInPassword.text.toString()
                    )
                ) {
                    putCurrentUsernameToIntent(binding.edtSignInUsername)
                } else {
                    Toast.makeText(
                        applicationContext,
                        applicationContext.getText(R.string.invalid_username_or_password),
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
            } else {
                putCurrentUsernameToIntent(binding.edtSignInUsername)
            }

            // if user checked remember button, commit data to SharedPreferences
            if (binding.chkSignInRememberPassword.isChecked) {
                putToSharePreferences()
            } else {
                preferencesEditor.clear().commit()
            }

            finish()
        }
    }

    // check if user exist
    private fun checkUserExist(userName: String, password: String): Boolean {
        val librarianDB = LibrarianDB(Database(applicationContext))
        return librarianDB.isLibrarianExist(userName, password)
    }

    // get and set text from SharedPreferences for edittext
    private fun innerInfoFromSharedPreferences(isChecked: Boolean) {
        binding.edtSignInUsername.setText(sharedPreferences.getString("username", ""))
        binding.edtSignInPassword.setText(sharedPreferences.getString("password", ""))
        binding.chkSignInRememberPassword.isChecked = isChecked
    }

    // back button pressed then finish activity
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    // check if admin
    private fun isAdmin(userName: String, password: String): Boolean =
        userName == "admin" && password == "admin"

    // commit to SharedPreferences
    private fun putToSharePreferences() {
        sharedPreferences = getSharedPreferences("saved_info", MODE_PRIVATE)
        preferencesEditor = sharedPreferences.edit()
        preferencesEditor.putBoolean("remember", binding.chkSignInRememberPassword.isChecked)
        preferencesEditor.putString("username", binding.edtSignInUsername.text.toString())
        preferencesEditor.putString("password", binding.edtSignInPassword.text.toString())
        preferencesEditor.commit()
    }

    // check if edittext is empty
    private fun isEmpty(userName: TextInputEditText, password: TextInputEditText): Boolean {
        return userName.text.isNullOrBlank() || password.text.toString().isEmpty()
    }

    // put username and user type to intent and start main activity
    private fun putCurrentUsernameToIntent(
        userName: TextInputEditText
    ) {
        val intent = Intent(this, MainActivity::class.java)


        if (userName.text.toString() == "admin") {
            intent.putExtra("user_type", "admin")
            intent.putExtra("username", "admin")

        } else {
            intent.putExtra("user_type", "normal")
            intent.putExtra("username", userName.text.toString())
            intent.putExtra("display_name", getDisplayNameFromLoginName(userName.text.toString()))
        }
        startActivity(intent)
    }

    private fun getDisplayNameFromLoginName(loginName:String) : String {
        val librarianDB = LibrarianDB(Database(applicationContext))

        val opt = librarianDB.getAllLibrarians().stream().filter { lib -> lib.loginName == loginName }.findFirst()
        return if (opt.isPresent) opt.get().displayName else ""
    }

    private fun initAdminAccount() {
        val librarianDB:LibrarianDB = LibrarianDB(Database(applicationContext))

    }
}