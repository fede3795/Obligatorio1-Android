package com.silverafederico.obligatorio1_android.Adapter

import android.text.Editable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.silverafederico.obligatorio1_android.Entities.Note
import com.silverafederico.obligatorio1_android.databinding.StringItemBinding
import java.text.FieldPosition

interface OnItemStringClickListen{
    fun onItemClick(item: String)
}
class ListStringAdapter(private val list: List<String>,private val onItemStringClickListen:OnItemStringClickListen): RecyclerView.Adapter<ListStringAdapter.StringViewHolder>(){
    inner class StringViewHolder(val binding: StringItemBinding): RecyclerView.ViewHolder(binding.root)

    interface OnItemStringClickListen {
        fun onItemClick(item: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int):StringViewHolder{
        val binding = StringItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StringViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: StringViewHolder, position: Int) {
        val item = list[position]
        with(holder.binding) {
            val editableItem: Editable = Editable.Factory.getInstance().newEditable(item)
            editText.text = editableItem
            root.setOnClickListener {
                onItemStringClickListen.onItemClick(item)
            }
        }
    }
}