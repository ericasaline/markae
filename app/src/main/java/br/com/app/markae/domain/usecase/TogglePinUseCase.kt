package br.com.app.markae.domain.usecase

import br.com.app.markae.domain.repository.NoteRepository

class TogglePinUseCase(private val repository: NoteRepository) {
	suspend operator fun invoke(id: String) = repository.togglePin(id)
}