package com.silverafederico.obligatorio1_android

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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

        val username = intent.getStringExtra("username")
        if (username == null) {
            finish()
        }


        val itemDecoration = DividerItemDecoration(binding.recyclerView.context, LinearLayoutManager(this).orientation)
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false)

        noteAdapter = NoteAdapter(noteList, this)
        binding.recyclerView.adapter = noteAdapter
        binding.recyclerView.addItemDecoration(itemDecoration)



        binding.addButton.setOnClickListener {
            addNewNote()
        }

        binding.deleteAllBtn.setOnClickListener {
            showDeleteAllConfirmationDialog()
        }

        binding.logoutBtn.setOnClickListener {
            logout()
        }
    }

    override fun onResume() {
        super.onResume()
        loadNotesFromSharedPreferences()
    }

    private fun loadNotesFromSharedPreferences() {
        val username = intent.getStringExtra("username")
        if (username != null) {
            val userPreferences = getSharedPreferences("User_$username", MODE_PRIVATE)
            val notesJson = userPreferences.getString("notes", null)

            if (notesJson != null) {
                val notes: List<Note> = Json.decodeFromString(notesJson)

                noteList.clear()

                noteList.addAll(notes)

                noteAdapter.notifyDataSetChanged()
            }
        }
    }


    private fun addNewNote() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Seleccione el tipo de nota")
        val noteTypes = arrayOf("Texto simple", "Lista")
        dialog.setItems(noteTypes) { _, which ->
            when (which) {
                0 -> addNewSimpleNote()
                1 -> addNewListNote()
            }
        }
        dialog.show()
    }

    private fun addNewSimpleNote() {
        val newNote = Note(
            UUID.randomUUID().toString(),
            "Title",
            "Description",
            SimpleDateFormat("dd/MM/yyyy").format(Date()),
            false,
            mutableListOf()
        )
        noteList.add(newNote)

        noteAdapter.notifyDataSetChanged()

        saveNotesToSharedPreferences()
    }

    private fun addNewListNote() {
        val newNote = Note(
            UUID.randomUUID().toString(),
            "Title",
            "Description",
            SimpleDateFormat("dd/MM/yyyy").format(Date()),
            true,
            mutableListOf()
        )
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
        val username = intent.getStringExtra("username")
        if (username != null) {
            val userPreferences = getSharedPreferences("User_$username", MODE_PRIVATE)
            val notesJson = Json.encodeToString(noteList)
            val editor = userPreferences.edit()
            editor.putString("notes", notesJson)
            editor.apply()
        }
    }

    companion object {
        const val REQUEST_CODE = 1
    }

    private fun showDeleteAllConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_alert, null)
        builder.setView(dialogView)
        val dialog = builder.create()

        val confirmButton = dialogView.findViewById<Button>(R.id.confirmBtn)
        val cancelButton = dialogView.findViewById<Button>(R.id.CancelBtn)

        confirmButton.setOnClickListener {
            deleteAllNotes()
            dialog.dismiss()
        }

        cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()

    }

    private fun deleteAllNotes() {
        noteList.clear()
        noteAdapter.notifyDataSetChanged()

        saveNotesToSharedPreferences()
    }

    private fun logout() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

}