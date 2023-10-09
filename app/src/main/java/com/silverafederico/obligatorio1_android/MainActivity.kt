package com.silverafederico.obligatorio1_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.silverafederico.obligatorio1_android.databinding.LoginBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: LoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}