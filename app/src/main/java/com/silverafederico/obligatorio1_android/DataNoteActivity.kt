package com.silverafederico.obligatorio1_android

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.silverafederico.obligatorio1_android.Adapter.ListStringAdapter
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
    private lateinit var listAdapter: ListStringAdapter
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = DataNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val itemJson = intent.getStringExtra("item")
        val item: Note? = itemJson?.let { Json.decodeFromString(it) }

        note = item ?: Note(
            UUID.randomUUID().toString(),
            "Title",
            "Description",
            SimpleDateFormat("dd/MM/yyyy").format(Date()),
            false,
            mutableListOf()
        )

        if (note.isList) {
            val listAdapter = ListStringAdapter(note.listItems) { updatedItem ->
                editElementInList(updatedItem)
            }
            binding.recyclerViewString.layoutManager = LinearLayoutManager(this)
            binding.recyclerViewString.adapter = listAdapter
            binding.addToList.visibility = View.VISIBLE
            binding.addToList.setOnClickListener {
                val newItem = "Nuevo Item"
                addElementToList(newItem)
            }
        } else {
            binding.descriptionLayout.setText("${note.description}")
            binding.addToList.visibility = View.GONE
        }

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

    private fun addElementToList(newItem: String) {
        note.listItems.add(newItem)
        listAdapter.notifyDataSetChanged()
    }

    private fun editElementInList(updatedItem: Any) {
        val position = note.listItems.indexOf(updatedItem)
        note.listItems[position] = updatedItem.toString()
        listAdapter.notifyDataSetChanged()
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
        note.title = binding.titleLayout.text.toString()
        note.description = binding.descriptionLayout.text.toString()
        note.date = SimpleDateFormat("dd/MM/yyyy").format(Date())

        val intent = Intent()
        intent.putExtra("updatedNote", Json.encodeToString(note))
        setResult(RESULT_OK, intent)

        finish()
    }

}