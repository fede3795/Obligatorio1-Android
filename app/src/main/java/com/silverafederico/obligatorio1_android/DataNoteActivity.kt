package com.silverafederico.obligatorio1_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.silverafederico.obligatorio1_android.Entities.Note
import com.silverafederico.obligatorio1_android.databinding.ActivityDataNoteBinding
import com.silverafederico.obligatorio1_android.databinding.DataNoteBinding
import kotlinx.serialization.json.Json

class DataNoteActivity : AppCompatActivity() {
    private lateinit var binding:DataNoteBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding =DataNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val itemJson = intent.getStringExtra("item")
        val item: Note? = itemJson?.let { Json.decodeFromString(it) }
        binding.titleLayout.setText("${item?.title}")
        binding.descriptionLayout.setText("${item?.description}")
        binding.dateLayout.setText("${item?.date}")

    }
}