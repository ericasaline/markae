package br.com.app.markae.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.app.markae.core.states.ViewState
import br.com.app.markae.domain.model.Note
import br.com.app.markae.domain.usecase.GetAllNotesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ListNotesViewModel(
	private val listNotesUseCase: GetAllNotesUseCase
): ViewModel() {

	init { listNotes() }

	private val _notes = MutableStateFlow<ViewState<List<Note>?>>(ViewState.Loading)
	val notes: StateFlow<ViewState<List<Note>?>> = _notes

	private fun listNotes() = viewModelScope.launch {
		listNotesUseCase()
			.onSuccess { notes -> _notes.value = ViewState.Success(notes) }
			.onFailure { error -> _notes.value = ViewState.Error(error) }
	}
}