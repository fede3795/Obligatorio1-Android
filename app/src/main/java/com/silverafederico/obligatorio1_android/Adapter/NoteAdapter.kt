package com.silverafederico.obligatorio1_android.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.silverafederico.obligatorio1_android.Entities.Note
import com.silverafederico.obligatorio1_android.databinding.NoteItemBinding

interface OnItemClickListen{
    fun onItemClick(item:Note)
}

class NoteAdapter(private val list: List<Note>,private val onItemClickListen: OnItemClickListen): RecyclerView.Adapter<NoteAdapter.NoteViewHolder>(){
    inner class NoteViewHolder(val binding: NoteItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent , false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val item= list[position]
        with(holder.binding){
            titleID.text= item.title
            descriptionID.text= item.description
            dateID.text= item.date.toString()

        }
        holder.binding.root.setOnClickListener{
            onItemClickListen.onItemClick(item)
        }
    }
    override fun getItemCount(): Int {
        return list.size
    }
}

