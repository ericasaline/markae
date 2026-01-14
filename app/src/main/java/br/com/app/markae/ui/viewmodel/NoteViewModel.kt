package br.com.app.markae.ui.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.app.markae.core.states.ViewState
import br.com.app.markae.domain.model.Note
import br.com.app.markae.domain.usecase.DeleteNoteUseCase
import br.com.app.markae.domain.usecase.GetNoteUseCase
import br.com.app.markae.domain.usecase.InsertNoteUseCase
import br.com.app.markae.domain.usecase.TogglePinUseCase
import br.com.app.markae.domain.usecase.UpdateNoteUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NoteViewModel(
	private val insertNoteUseCase: InsertNoteUseCase,
	private val updateNoteUseCase: UpdateNoteUseCase,
	private val deleteNoteUseCase: DeleteNoteUseCase,
	private val togglePinUseCase: TogglePinUseCase,
	private val getNoteUseCase: GetNoteUseCase,
	savedStateHandle: SavedStateHandle
): ViewModel() {

	private val _actionAddEvent = MutableSharedFlow<ViewState<Unit>>()
	val actionAddEvent: SharedFlow<ViewState<Unit>> = _actionAddEvent

	private val _actionDelEvent = MutableSharedFlow<ViewState<Unit>>()
	val actionDelEvent: SharedFlow<ViewState<Unit>> = _actionDelEvent

	private val _note = MutableStateFlow<ViewState<Note?>>(ViewState.Loading)
	val note: StateFlow<ViewState<Note?>> = _note

	private val id: String? = savedStateHandle["id"]

	init {
		if (!id.isNullOrEmpty()) loadNote(id)
		else _note.value = ViewState.Empty
	}

	private fun loadNote(id: String) = viewModelScope.launch {
		_note.emit(ViewState.Loading)
		getNoteUseCase(id)
			.onSuccess { note -> if (note != null) _note.value = ViewState.Success(note) }
			.onFailure { error -> _note.value = ViewState.Error(error) }
	}

	fun addNote(
		title: String,
		content: String,
		isPinned: Boolean
	) = viewModelScope.launch {
		val note = Note(id = id, title = title, content = content, pinned = isPinned)

		_actionAddEvent.emit(ViewState.Loading)

		val operation: suspend (Note) -> Result<Unit> = { n ->
			if (id.isNullOrEmpty()) insertNoteUseCase(n) else updateNoteUseCase(n)
		}

		operation(note)
			.onSuccess { _actionAddEvent.emit(ViewState.Success(Unit)) }
			.onFailure { _actionAddEvent.emit(ViewState.Error(throwable = it)) }
	}

	fun delNote() = viewModelScope.launch {
		_actionDelEvent.emit(ViewState.Loading)
		deleteNoteUseCase(id ?: "")
			.onSuccess { _actionDelEvent.emit(ViewState.Success(Unit)) }
			.onFailure { _actionDelEvent.emit(ViewState.Error(throwable = it)) }
	}

	fun pinNote() = viewModelScope.launch {
		togglePinUseCase(id ?: "")
			.onSuccess { Log.d("INFO", "Sucesso em: pinNote()") }
			.onFailure { e -> Log.d("INFO", "Erro $e em: pinNote()") }
	}
}