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
            }
            if (binding.chkSignInRememberPassword.isChecked) {
                putToSharePreferences()
            } else {
                preferencesEditor.clear().commit()
            }
            putCurrentUsernameToIntent(binding.edtSignInUsername)
            finish()
        }
    }

    private fun checkUserExist(userName: String, password: String): Boolean {
        val librarianDB = LibrarianDB(Database(applicationContext))
        return librarianDB.isLibrarianExist(userName, password)
    }

    private fun innerInfoFromSharedPreferences(isChecked: Boolean) {
        binding.edtSignInUsername.setText(sharedPreferences.getString("username", ""))
        binding.edtSignInPassword.setText(sharedPreferences.getString("password", ""))
        binding.chkSignInRememberPassword.isChecked = isChecked
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun isAdmin(userName: String, password: String): Boolean =
        userName == "admin" && password == "admin"

    private fun putToSharePreferences() {
        sharedPreferences = getSharedPreferences("saved_info", MODE_PRIVATE)
        preferencesEditor = sharedPreferences.edit()
        preferencesEditor.putBoolean("remember", binding.chkSignInRememberPassword.isChecked)
        preferencesEditor.putString("username", binding.edtSignInUsername.text.toString())
        preferencesEditor.putString("password", binding.edtSignInPassword.text.toString())
        preferencesEditor.commit()
    }

    private fun isEmpty(userName: TextInputEditText, password: TextInputEditText): Boolean {
        return userName.text.isNullOrBlank() || password.text.toString().isEmpty()
    }

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
        }
        startActivity(intent)
    }
}