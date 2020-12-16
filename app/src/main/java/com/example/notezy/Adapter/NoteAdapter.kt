package com.example.notezy.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.notezy.Fragments.NoteFragmentDirections
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

        holder.binding.cardNote.setOnClickListener {
//            Log.i("NoteAdapter", "${currentItem.title} clicked")
            val action = NoteFragmentDirections.actionNoteFragmentToEditFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
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