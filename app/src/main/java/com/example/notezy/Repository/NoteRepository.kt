package com.example.notezy.Repository

import androidx.lifecycle.LiveData
import com.example.notezy.Database.NoteDao
import com.example.notezy.Model.Note

class NoteRepository(private val noteDao: NoteDao) {
    val allNotes: LiveData<List<Note>> = noteDao.getAllNotes()

    suspend fun insert(note: Note) {
        noteDao.insert(note)
    }

    suspend fun update(note: Note) {
        noteDao.updateNote(note)
    }

    suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note)
    }

    suspend fun deleteAll() {
        noteDao.deleteAll()
    }

    fun search(searchQuery: String): LiveData<List<Note>> {
        return noteDao.search(searchQuery)
    }
}