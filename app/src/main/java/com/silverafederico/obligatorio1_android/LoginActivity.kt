package com.silverafederico.obligatorio1_android

import android.content.Intent
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

        /*if (savedCredentials != null) {
            showLoginScreen(savedCredentials)
        } else {
            showRegisterScreen()
        }*/

        binding.signBtn.setOnClickListener {
            val username = binding.editTextEmailInput.text.toString()
            val password = binding.editTextPasswordInput.text.toString()

            if (isValidCredentials(username, password)) {
                preferenceManager.saveCredentials(username, password)
                showLoginScreen(Pair(username, password))
            } else {
                Toast.makeText(this, "Wrong credentials", Toast.LENGTH_SHORT).show()
            }
        }

        binding.regBtn.setOnClickListener {
            showRegisterScreen()
        }
    }

    private fun showLoginScreen(credentials: Pair<String, String>) {
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("credentials", credentials)
        startActivity(intent)
    }

    private fun showRegisterScreen() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun isValidCredentials(username: String, password: String): Boolean {
        return (username == "usuario" && password == "contraseña")
    }
}