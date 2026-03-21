package br.com.app.markae.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.app.markae.common.state.ViewState
import br.com.app.markae.domain.model.Note
import br.com.app.markae.domain.usecase.DeleteAllNotesUseCase
import br.com.app.markae.domain.usecase.GetAllNotesUseCase
import br.com.app.markae.ui.screen.model.SortOption
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ListNotesViewModel(
	private val listNotesUseCase: GetAllNotesUseCase,
	private val deleteAllNotesUseCase: DeleteAllNotesUseCase
): ViewModel() {

	private val _notes = MutableStateFlow<ViewState<List<Note>?>>(ViewState.Loading)
	val notes: StateFlow<ViewState<List<Note>?>> = _notes

	private var orderNotes: List<Note> = emptyList()

	 fun listNotes() = viewModelScope.launch {
		listNotesUseCase()
			.onSuccess { list ->
				list?.let { notes ->
					_notes.value = if (notes.isEmpty()) ViewState.Empty else ViewState.Success(list)
					orderNotes = notes
				}
			}
			.onFailure { error -> _notes.value = ViewState.Error(error) }
	}

	fun deleteAllNotes() = viewModelScope.launch {
		deleteAllNotesUseCase()
			.onSuccess { Log.i("INFO", "Sucesso em: deleteAllNotes()") }
			.onFailure { e -> Log.i("INFO", "Erro $e em: deleteAllNotes()") }
		listNotes()
	}

	fun orderNotesBy(sortOption: SortOption) = viewModelScope.launch {
		val sortedList = when (sortOption) {
			SortOption.NONE -> orderNotes
			SortOption.TITLE -> orderNotes.sortedBy { it.title }
			SortOption.CREATED_AT -> orderNotes.sortedBy { it.createdAt }
			SortOption.UPDATED_AT -> orderNotes.sortedBy { it.updatedAt }
		}
		_notes.value = ViewState.Success(sortedList)
	}
}