package br.com.app.markae.domain.usecase

import br.com.app.markae.domain.repository.NoteRepository

class DeleteAllNotesUseCase(private val repository: NoteRepository) {
	suspend operator fun invoke() = repository.deleteAll()
}