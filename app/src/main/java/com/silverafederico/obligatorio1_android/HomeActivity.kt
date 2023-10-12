package com.silverafederico.obligatorio1_android

import android.content.Intent
import android.os.Bundle
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

class HomeActivity : AppCompatActivity(), OnItemClickListen {

    private val preferenceManager by lazy { PreferenceManager(this) }

    private lateinit var binding: HomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = HomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val noteList = mutableListOf<Note>(
            Note("Prueba1", "descripcion1", SimpleDateFormat("dd/MM/yyyy").format(Date())),
            Note("Prueba2", "descripcion2", SimpleDateFormat("dd/MM/yyyy").format(Date())),
        )


        val itemDecoration = DividerItemDecoration(binding.recyclerView.context, LinearLayoutManager(this).orientation)

        binding.recyclerView.layoutManager = GridLayoutManager(this,2, LinearLayoutManager.VERTICAL,false)
        binding.recyclerView.adapter = NoteAdapter(noteList,this)
        binding.recyclerView.addItemDecoration(itemDecoration)

    }

    override fun onItemClick(item: Note) {
        val itemJson = Json.encodeToString(item)
        val intent = Intent(this,DataNoteActivity::class.java)
        intent.putExtra("item", itemJson)
        startActivity(intent)
    }
}