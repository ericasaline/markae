package br.com.app.markae.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import br.com.app.markae.data.local.entity.NoteEntity

@Dao
interface NoteDao {
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertNote(note: NoteEntity)

	@Update
	suspend fun updateNote(note: NoteEntity)

	@Query("DELETE FROM NOTES WHERE ID = :id")
	suspend fun deleteNote(id: Long)

	@Query("SELECT * FROM NOTES WHERE ID = :id")
	suspend fun showNote(id: Long): NoteEntity?

	@Query("SELECT * FROM NOTES")
	suspend fun showAllNotes(): List<NoteEntity>
}