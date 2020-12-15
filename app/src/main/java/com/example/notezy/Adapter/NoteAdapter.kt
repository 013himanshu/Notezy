package com.example.notezy.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notezy.Model.Note
import com.example.notezy.databinding.ItemNoteBinding

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.MyViewHolder>() {
    var allNotes = ArrayList<Note>()

    inner class MyViewHolder(val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {
        val rvTitle = binding.rvTitle
        val rvBody = binding.rvBody
        val rvDelete = binding.rvDelete
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemNoteBinding.inflate(layoutInflater, parent, false)

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = allNotes[position]
        holder.binding.rvTitle.text = currentItem.title
        holder.binding.rvBody.text = currentItem.note
    }

    override fun getItemCount(): Int {
        return allNotes.size
    }

    fun setData(newList: List<Note>) {
        allNotes.clear()
        this.allNotes.addAll(newList)
        notifyDataSetChanged()
    }
}