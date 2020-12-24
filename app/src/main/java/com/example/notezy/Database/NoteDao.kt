package com.example.notezy.Database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.notezy.Model.Note

@Dao
interface NoteDao {
    //inserts new note
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    //updates a single note
    @Update
    suspend fun updateNote(note: Note)

    //deletes a single note
    @Delete
    suspend fun deleteNote(note: Note)

    //deletes all notes
    @Query("DELETE from note_table")
    suspend fun deleteAll()

    //query to get all notes and display them in recyclerView
    @Query("SELECT * from note_table ORDER BY id DESC")
    fun getAllNotes(): LiveData<List<Note>>

    //search note from database
    @Query("SELECT * from note_table WHERE title LIKE :searchQuery OR note LIKE :searchQuery")
    fun search(searchQuery: String): LiveData<List<Note>>
}