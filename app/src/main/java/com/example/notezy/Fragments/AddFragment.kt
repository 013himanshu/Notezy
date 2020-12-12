package com.example.notezy.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notezy.Database.Note
import com.example.notezy.Database.NoteDatabase
import com.example.notezy.R
import com.example.notezy.ViewModel.NoteViewModel
import com.example.notezy.databinding.FragmentAddBinding
import org.w3c.dom.Text


class AddFragment : Fragment() {
    private lateinit var binding: FragmentAddBinding
    private lateinit var noteViewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add, container, false)


        //throwing error
        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        binding.addBtn.setOnClickListener {
            val noteTitle: String = binding.addTitleText.text.toString().trim()
            val noteBody: String = binding.addNoteText.text.toString().trim()

            when {
                noteTitle.isEmpty() -> {
                    Toast.makeText(this.activity, "Title missing.", Toast.LENGTH_SHORT).show()
                    binding.addTitleText.requestFocus()
                    return@setOnClickListener
                }
                noteBody.isEmpty() -> {
                    Toast.makeText(this.activity, "Note Body missing.", Toast.LENGTH_SHORT).show()
                    binding.addNoteText.requestFocus()
                    return@setOnClickListener
                }
                else -> {
                    noteViewModel.insertNote(Note(noteTitle, noteBody))
                    Toast.makeText(this.activity, "New Note Added.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return binding.root
    }


//    private fun insertDataToDatabase() {
//
//    }

}