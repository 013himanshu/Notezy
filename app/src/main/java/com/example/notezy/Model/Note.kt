package com.example.notezy.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class Note(
    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "note")
    val note: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L
}