package com.silverafederico.obligatorio1_android

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.silverafederico.obligatorio1_android.Entities.Note
import com.silverafederico.obligatorio1_android.databinding.DataNoteBinding
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.text.SimpleDateFormat
import java.util.Date
import java.util.UUID

class DataNoteActivity : AppCompatActivity() {
    private lateinit var binding:DataNoteBinding
    private lateinit var note: Note
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = DataNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val itemJson = intent.getStringExtra("item")
        val item: Note? = itemJson?.let { Json.decodeFromString(it) }

        note = item ?: Note(UUID.randomUUID().toString(), "Title", "Description", SimpleDateFormat("dd/MM/yyyy").format(
            Date()
        ))

        binding.titleLayout.setText("${item?.title}")
        binding.descriptionLayout.setText("${item?.description}")
        binding.dateLayout.setText("${item?.date}")

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnSave.setOnClickListener {
            saveNoteAndReturnToHome()
        }

        binding.deleteBtn.setOnClickListener {
            deleteNote()
        }


    }

    private fun deleteNote() {
        val noteId = note.id

        deleteNoteFromSharedPreferences(noteId)

        val intent = Intent()
        intent.putExtra("deletedNoteId", noteId)
        setResult(RESULT_OK, intent)

        finish()
    }

    private fun deleteNoteFromSharedPreferences(noteId: String) {
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove("note_$noteId")

        editor.apply()
    }

    private fun saveNoteAndReturnToHome() {
        note?.title = binding.titleLayout.text.toString()
        note?.description = binding.descriptionLayout.text.toString()

        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val noteJson = Json.encodeToString(note)
        editor.putString("note", noteJson)

        editor.apply()

        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

}