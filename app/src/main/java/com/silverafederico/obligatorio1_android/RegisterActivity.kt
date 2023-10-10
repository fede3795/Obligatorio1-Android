package com.silverafederico.obligatorio1_android

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.silverafederico.obligatorio1_android.databinding.RegisterBinding

class RegisterActivity : AppCompatActivity() {

    private val preferenceManager by lazy { PreferenceManager(this) }

    private lateinit var binding: RegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = RegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signBtn2.setOnClickListener { onCancelButtonClick() }
        binding.signBtn3.setOnClickListener { onSaveButtonClick() }

    }

    private fun onCancelButtonClick() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun onSaveButtonClick() {
        val name = binding.editTextName.text.toString()
        val lastname = binding.editTextLastName.text.toString()
        val email = binding.editTextEmail.text.toString()
        val password = binding.editTextPassword.text.toString()
        val repeatPassword = binding.editTextRepeatPassword.text.toString()

        if (isValidRegistrationData(password, repeatPassword)) {
            val sharedPreferences = getSharedPreferences("LoginPreferences", MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            editor.putString("name", name)
            editor.putString("lastname", lastname)
            editor.putString("email", email)
            editor.putString("password", password)

            editor.apply()
            showLoginScreen()
        } else {
            Toast.makeText(this, "Invalid registration data", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isValidRegistrationData(
        password: String,
        repeatPassword: String
    ): Boolean {
        return password == repeatPassword
    }

    private fun showLoginScreen() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }
}