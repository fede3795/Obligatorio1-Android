package com.silverafederico.obligatorio1_android

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.silverafederico.obligatorio1_android.Adapter.NoteAdapter
import com.silverafederico.obligatorio1_android.Adapter.OnItemClickListen
import com.silverafederico.obligatorio1_android.Entities.Note
import com.silverafederico.obligatorio1_android.databinding.HomeBinding
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.text.SimpleDateFormat
import java.util.Date
import java.util.UUID


class HomeActivity : AppCompatActivity(), OnItemClickListen {

    private lateinit var binding: HomeBinding
    private val noteList = mutableListOf<Note>()
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = HomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val itemDecoration = DividerItemDecoration(binding.recyclerView.context, LinearLayoutManager(this).orientation)
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false)

        noteAdapter = NoteAdapter(noteList, this)
        binding.recyclerView.adapter = noteAdapter
        binding.recyclerView.addItemDecoration(itemDecoration)

        binding.addButton.setOnClickListener {
            addNewNote()
        }
    }

    override fun onResume() {
        super.onResume()
        loadNotesFromSharedPreferences()
    }

    private fun loadNotesFromSharedPreferences() {
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val notesJson = sharedPreferences.getString("notes", null)

        if (notesJson != null) {
            val notes: List<Note> = Json.decodeFromString(notesJson)

            noteList.clear()

            noteList.addAll(notes)

            noteAdapter.notifyDataSetChanged()
        }
    }


    private fun addNewNote() {
        val newNote = Note(UUID.randomUUID().toString(), "Title", "Description", SimpleDateFormat("dd/MM/yyyy").format(Date()))
        noteList.add(newNote)

        noteAdapter.notifyDataSetChanged()

        saveNotesToSharedPreferences()
    }


    override fun onItemClick(item: Note) {
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val savedNoteJson = sharedPreferences.getString("note", null)
        val notesJson = Json.encodeToString(noteList)

        if (savedNoteJson != null) {
            val savedNote = Json.decodeFromString<Note>(savedNoteJson)
            if (savedNote.id == item.id) {
                val intent = Intent(this, DataNoteActivity::class.java)
                val itemJson = Json.encodeToString(savedNote)
                intent.putExtra("notes", notesJson)
                intent.putExtra("item", itemJson)
                startActivityForResult(intent, REQUEST_CODE)
            } else {
                val itemJson = Json.encodeToString(item)
                val intent = Intent(this, DataNoteActivity::class.java)
                intent.putExtra("notes", notesJson)
                intent.putExtra("item", itemJson)
                startActivityForResult(intent, REQUEST_CODE)
            }
        } else {
            val itemJson = Json.encodeToString(item)
            val intent = Intent(this, DataNoteActivity::class.java)
            intent.putExtra("notes", notesJson)
            intent.putExtra("item", itemJson)
            startActivityForResult(intent, REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            val updatedNoteJson = data?.getStringExtra("updatedNote")
            if (updatedNoteJson != null) {
                val updatedNote = Json.decodeFromString<Note>(updatedNoteJson)

                for (note in noteList) {
                    if (note.id == updatedNote.id) {
                        note.title = updatedNote.title
                        note.description = updatedNote.description
                        note.date = updatedNote.date
                        noteAdapter.notifyDataSetChanged()
                        Log.d("HomeActivity", "Nota actualizada: ${note.id}")
                        break
                    }
                }
            }

            val deletedNoteId = data?.getStringExtra("deletedNoteId")
            if (deletedNoteId != null) {
                val noteToDelete = noteList.find { it.id == deletedNoteId }
                if (noteToDelete != null) {
                    noteList.remove(noteToDelete)
                    noteAdapter.notifyDataSetChanged()
                    Log.d("HomeActivity", "Nota eliminada: $noteToDelete")
                }
                saveNotesToSharedPreferences()
            }
        }
    }

    private fun saveNotesToSharedPreferences() {
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val notesJson = Json.encodeToString(noteList)
        val editor = sharedPreferences.edit()
        editor.putString("notes", notesJson)
        editor.apply()
    }

    companion object {
        const val REQUEST_CODE = 1
    }

}