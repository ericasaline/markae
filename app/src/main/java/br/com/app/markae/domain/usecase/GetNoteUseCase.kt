package br.com.app.markae.domain.usecase

import br.com.app.markae.domain.repository.NoteRepository

class GetNoteUseCase(private val repository: NoteRepository) {
	suspend operator fun invoke(id: String) = repository.getById(id)
}