package br.com.app.markae.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.app.markae.data.local.dao.NoteDao
import br.com.app.markae.data.local.entity.NoteEntity

@Database(entities = [NoteEntity::class], version = 1)
abstract class NotesDatabase: RoomDatabase() {
	abstract fun notesDao(): NoteDao
}