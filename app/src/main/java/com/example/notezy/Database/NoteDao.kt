package com.example.notezy.Database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.notezy.Model.Note

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Query("SELECT * from note_table ORDER BY id DESC")
    fun getAllNotes():LiveData<List<Note>>
}