package com.silverafederico.obligatorio1_android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.silverafederico.obligatorio1_android.databinding.HomeBinding
import java.util.Date

data class Note(val title: String, val content: String, val date: Date)

class HomeActivity : AppCompatActivity() {

    private val preferenceManager by lazy { PreferenceManager(this) }

    private lateinit var binding: HomeBinding

    private val noteList = mutableListOf<Note>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = HomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}