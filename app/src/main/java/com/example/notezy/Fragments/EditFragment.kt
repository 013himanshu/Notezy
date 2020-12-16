package com.example.notezy.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notezy.Model.Note
import com.example.notezy.R
import com.example.notezy.ViewModel.NoteViewModel
import com.example.notezy.databinding.FragmentEditBinding


class EditFragment : Fragment() {
    private lateinit var binding: FragmentEditBinding
    private val args by navArgs<EditFragmentArgs>()
    private lateinit var noteViewModel: NoteViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit, container, false)

        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        binding.editTitleText.setText(args.currentNote.title)
        binding.editNoteText.setText(args.currentNote.note)

        binding.editBtn.setOnClickListener {
            updateData()
        }

        return binding.root
    }

    private fun updateData() {
        val noteTitle: String = binding.editTitleText.text.toString().trim()
        val noteBody: String = binding.editNoteText.text.toString().trim()

        when {
            noteTitle.isEmpty() -> {
                Toast.makeText(this.activity, "Title missing.", Toast.LENGTH_SHORT).show()
                binding.editTitleText.requestFocus()
            }
            noteBody.isEmpty() -> {
                Toast.makeText(this.activity, "Note Body missing.", Toast.LENGTH_SHORT).show()
                binding.editNoteText.requestFocus()
            }
            else -> {
                noteViewModel.updateNote(Note(args.currentNote.id, noteTitle, noteBody))
                Toast.makeText(this.activity, "Note Updated.", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_editFragment_to_noteFragment)
            }
        }
    }

}