package com.example.notezy.Fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
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

        //Adds delete menu to toolbar...
        setHasOptionsMenu(true)

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            deleteNote()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun deleteNote() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            noteViewModel.deleteNote(args.currentNote)
            Toast.makeText(requireContext(), "Note Deleted.", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_editFragment_to_noteFragment)
        }
        builder.setNegativeButton("No") { _,_ -> }
        builder.setTitle("Delete Note?")
        builder.setMessage("Are you sure you want to delete this note?")
        builder.create().show()
    }
}