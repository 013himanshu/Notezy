package com.example.notezy.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.notezy.Model.Note

class SharedViewModel(application: Application) : AndroidViewModel(application) {

    val emptyData: MutableLiveData<Boolean> = MutableLiveData(false)

    fun checkIfDatabaseEmpty(note: List<Note>) {
        emptyData.value = note.isEmpty()
    }

}