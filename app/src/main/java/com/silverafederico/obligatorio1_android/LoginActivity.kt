package com.silverafederico.obligatorio1_android

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.silverafederico.obligatorio1_android.databinding.LoginBinding

class LoginActivity : AppCompatActivity() {

    private val preferenceManager by lazy { PreferenceManager(this) }

    private lateinit var binding:  LoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = LoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val savedCredentials = preferenceManager.getSavedCredentials()

        binding.signBtn.setOnClickListener {
            val username = binding.editTextEmailInput.text.toString()
            val password = binding.editTextPasswordInput.text.toString()

            val userPreferences = getSharedPreferences("User_$username", MODE_PRIVATE)

            if (isValidCredentials(username, password, userPreferences)) {
                preferenceManager.saveCredentials(username, password, userPreferences)
                showHomeScreen(username)
            } else {
                Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
            }
        }

        binding.regBtn.setOnClickListener {
            showRegisterScreen()
        }
    }

    private fun showHomeScreen(username: String) {
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("username", username)
        startActivity(intent)
        finish()
    }


    private fun showRegisterScreen() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun isValidCredentials(username: String, password: String, userPreferences: SharedPreferences): Boolean {
        val savedUsername = userPreferences.getString("email", null)
        val savedPassword = userPreferences.getString("password", null)

        return (username == savedUsername && password == savedPassword)
    }
}