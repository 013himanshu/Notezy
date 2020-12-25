package com.example.notezy.Fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
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
import com.example.notezy.ViewModel.SharedViewModel
import com.example.notezy.databinding.FragmentNoteBinding


class NoteFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var binding: FragmentNoteBinding
    lateinit var viewModel: NoteViewModel
    lateinit var sharedViewModel: SharedViewModel

    lateinit var adapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_note, container, false)

        //RecyclerView...
        val recyclerview = binding.recyclerview
        adapter = NoteAdapter()
        recyclerview.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerview.adapter = adapter


        //NoteViewModel...
        viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        //sharedViewModel...
        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)

        viewModel.allNotes.observe(viewLifecycleOwner, Observer { list ->
            sharedViewModel.checkIfDatabaseEmpty(list)
            adapter.setData(list)
        })

        sharedViewModel.emptyData.observe(viewLifecycleOwner, {
            showEmptyView(it)
        })

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_noteFragment_to_addFragment)
        }

        setHasOptionsMenu(true)

        //Hide keyboard (bug)
        hideKeyboard(requireActivity())

        return binding.root
    }


    //function to display empty view if database is empty
    private fun showEmptyView(emptyDatabase: Boolean) {
        if (emptyDatabase) {
            binding.rvEmptyLayout.visibility = View.VISIBLE
        } else {
            binding.rvEmptyLayout.visibility = View.INVISIBLE
        }
    }

    //inflating menu options in action bar
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.note_menu, menu)

        val search = menu.findItem(R.id.note_search)
        val searchView = search.actionView as SearchView
        searchView?.isSubmitButtonEnabled = false
        searchView?.setOnQueryTextListener(this)
    }

    //fired when items are clicked in action bar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.note_delete) {
            deleteAllNote()
        }
        return super.onOptionsItemSelected(item)
    }

    //when search query is submitted
    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchThroughDatabase(query)
        }
        return true
    }

    //when there is a change in search query
    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            searchThroughDatabase(query)
        }
        return true
    }

    //function to search query from database
    private fun searchThroughDatabase(query: String) {
        var searchQuery: String = query
        searchQuery = "%$searchQuery%"
        viewModel.search(searchQuery).observe(this, Observer { list ->
            list?.let {
                adapter.setData(it)
            }
        })
    }

    //function to delete all notes
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