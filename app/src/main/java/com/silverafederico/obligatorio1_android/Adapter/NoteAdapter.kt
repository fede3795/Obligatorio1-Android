package com.silverafederico.obligatorio1_android.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.silverafederico.obligatorio1_android.Entities.Note
import com.silverafederico.obligatorio1_android.R

interface OnItemClickListen{
    fun onItemClick(item:Note)
}

class NoteAdapter(private val list: List<Note>,private val onItemClickListen: OnItemClickListen): RecyclerView.Adapter<NoteAdapter.NoteViewHolder>(){
    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.titleID)
        val description: TextView = itemView.findViewById(R.id.descriptionID)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = list[position]
        holder.title.text = if (currentNote.title.isNotEmpty()) currentNote.title else "Default Title"
        holder.description.text = if (currentNote.description.isNotEmpty()) currentNote.description else "Default Description"
        holder.itemView.setOnClickListener {
            onItemClickListen.onItemClick(currentNote)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

