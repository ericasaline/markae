package br.com.app.markae.data.repository

import br.com.app.markae.core.utils.safeCall
import br.com.app.markae.data.local.dao.NoteDao
import br.com.app.markae.domain.model.Note
import br.com.app.markae.domain.repository.NoteRepository
import br.com.app.markae.domain.repository.mapper.NoteMapper.toDomain
import br.com.app.markae.domain.repository.mapper.NoteMapper.toEntity

class NoteRepositoryImpl(
	private val dao: NoteDao
): NoteRepository {
	override suspend fun insert(note: Note) = safeCall {
		dao.insertNote(note.toEntity())
	}

	override suspend fun update(note: Note) = safeCall {
		dao.updateNote(note.toEntity())
	}

	override suspend fun deleteAll()= safeCall {
		dao.deleteAllNotes()
	}

	override suspend fun deleteById(id: String) = safeCall {
		dao.deleteNoteById(id)
	}

	override suspend fun getById(id: String) = safeCall {
		dao.getNoteById(id)?.toDomain()
	}

	override suspend fun getAll() = safeCall {
		dao.getNotes()?.map { note -> note.toDomain() }
	}

	override suspend fun togglePin(id: String) = safeCall {
		getById(id).getOrElse { throw it }?.let { note ->
			update(note.copy(pinned = !note.pinned)).getOrElse { throw it }
		} ?: throw IllegalStateException()
	}
}