package com.example.notezy.Fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notezy.Adapter.NoteAdapter
import com.example.notezy.R
import com.example.notezy.Utils.hideKeyboard
import com.example.notezy.ViewModel.NoteViewModel
import com.example.notezy.databinding.FragmentNoteBinding


class NoteFragment : Fragment() {

    private lateinit var binding: FragmentNoteBinding
    lateinit var viewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_note, container, false)

        //RecyclerView...
        val recyclerview = binding.recyclerview
        val adapter = NoteAdapter()
        recyclerview.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerview.adapter = adapter


        //NoteViewModel...
        viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        viewModel.allNotes.observe(viewLifecycleOwner, Observer { list ->
            if (list.isEmpty()) {
                binding.recyclerview.visibility = View.INVISIBLE
                binding.rvEmptyLayout.visibility = View.VISIBLE
            } else {
                binding.recyclerview.visibility = View.VISIBLE
                binding.rvEmptyLayout.visibility = View.INVISIBLE
                adapter.setData(list)
            }
        })

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_noteFragment_to_addFragment)
        }

        setHasOptionsMenu(true)

        //Hide keyboard (bug)
        hideKeyboard(requireActivity())

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.note_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.note_delete) {
            deleteAllNote()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllNote() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            viewModel.deleteAll()
            Toast.makeText(requireContext(), "All Notes Deleted.", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete Everything?")
        builder.setMessage("Are you sure you want to delete all notes?")
        builder.create().show()
    }
}