package com.silverafederico.obligatorio1_android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.silverafederico.obligatorio1_android.Entities.Note
import com.silverafederico.obligatorio1_android.databinding.DataNoteBinding
import kotlinx.serialization.json.Json

class DataNoteActivity : AppCompatActivity() {
    private lateinit var binding:DataNoteBinding
    private var note: Note? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding =DataNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val itemJson = intent.getStringExtra("item")
        val item: Note? = itemJson?.let { Json.decodeFromString(it) }
        binding.titleLayout.setText("${item?.title}")
        binding.descriptionLayout.setText("${item?.description}")
        binding.dateLayout.setText("${item?.date}")
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

    }

    private fun saveNote() {
        note?.title = binding.titleLayout.text.toString()
        note?.description = binding.descriptionLayout.text.toString()
        note?.date = binding.dateLayout.text.toString()
    }

}