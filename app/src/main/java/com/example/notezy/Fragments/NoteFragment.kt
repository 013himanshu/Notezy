package com.example.notezy.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.notezy.R
import com.example.notezy.databinding.FragmentNoteBinding


class NoteFragment : Fragment() {

    private lateinit var binding: FragmentNoteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_note, container, false)

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_noteFragment_to_addFragment)
        }

        return binding.root
    }

}