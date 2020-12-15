package com.example.notezy.Repository

import androidx.lifecycle.LiveData
import com.example.notezy.Database.NoteDao
import com.example.notezy.Model.Note

class NoteRepository(private val noteDao: NoteDao) {
    val allNotes: LiveData<List<Note>> = noteDao.getAllNotes()

    suspend fun insert(note: Note) {
        noteDao.insert(note)
    }
}