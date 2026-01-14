package br.com.app.markae.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "NOTES")
data class NoteEntity(
	@PrimaryKey
	@ColumnInfo(name = "ID")
	val id: String,
	@ColumnInfo(name = "TITLE")
	val title: String,
	@ColumnInfo(name = "CONTENT")
	val content: String,
	@ColumnInfo(name = "PINNED")
	val pinned: Boolean = false
)