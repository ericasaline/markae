package br.com.app.markae.domain.usecase

import br.com.app.markae.domain.repository.NoteRepository

class GetAllNotesUseCase(private val repository: NoteRepository) {
	suspend operator fun invoke() = repository.getAll()
}