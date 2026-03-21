package br.com.app.markae.domain.repository

import br.com.app.markae.domain.model.Note

interface NoteRepository {
	suspend fun insert(note: Note): Result<Unit>
	suspend fun update(note: Note): Result<Unit>
	suspend fun deleteAll(): Result<Unit>
	suspend fun deleteById(id: String): Result<Unit>
	suspend fun getAll(): Result<List<Note>?>
	suspend fun getById(id: String): Result<Note?>
	suspend fun togglePin(id: String): Result<Unit>
}