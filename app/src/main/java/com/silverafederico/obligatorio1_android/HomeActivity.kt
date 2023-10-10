package com.silverafederico.obligatorio1_android

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.silverafederico.obligatorio1_android.databinding.HomeBinding
import com.silverafederico.obligatorio1_android.databinding.LoginBinding

class HomeActivity : AppCompatActivity() {

    private val preferenceManager by lazy { PreferenceManager(this) }

    private lateinit var binding: HomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = HomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}