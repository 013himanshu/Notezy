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

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("DELETE from note_table")
    suspend fun deleteAll()

    @Query("SELECT * from note_table ORDER BY id DESC")
    fun getAllNotes(): LiveData<List<Note>>
}