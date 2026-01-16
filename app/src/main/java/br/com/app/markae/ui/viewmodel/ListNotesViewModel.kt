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

	private val _notes = MutableStateFlow<ViewState<List<Note>?>>(ViewState.Loading)
	val notes: StateFlow<ViewState<List<Note>?>> = _notes

	 fun listNotes() = viewModelScope.launch {
		listNotesUseCase()
			.onSuccess { list -> list?.let{ _notes.value = if (it.isEmpty()) ViewState.Empty else ViewState.Success(list) } }
			.onFailure { error -> _notes.value = ViewState.Error(error) }
	}
}