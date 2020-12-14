package com.example.notezy.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notezy.Adapter.NoteAdapter
import com.example.notezy.Database.Note
import com.example.notezy.R
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
        recyclerview.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerview.adapter = adapter


        //NoteViewModel...
        viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        viewModel.allNotes.observe(viewLifecycleOwner, Observer { list ->
//            list?.let {
//                adapter.setData(it)
//            }
            if (list.isEmpty()) {
                binding.recyclerview.visibility = View.INVISIBLE
                binding.rvEmptyLayout.visibility = View.VISIBLE
            }
            else {
                binding.recyclerview.visibility = View.VISIBLE
                binding.rvEmptyLayout.visibility = View.INVISIBLE
                adapter.setData(list)
            }
        })

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_noteFragment_to_addFragment)
        }

        return binding.root
    }

}