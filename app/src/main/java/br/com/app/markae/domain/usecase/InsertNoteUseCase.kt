package br.com.app.markae.domain.usecase

import br.com.app.markae.domain.model.Note
import br.com.app.markae.domain.repository.NoteRepository

class InsertNoteUseCase(private val repository: NoteRepository) {
	suspend operator fun invoke(note: Note) = repository.insert(note)
}