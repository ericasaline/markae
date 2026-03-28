package br.com.app.markae.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.app.markae.R
import br.com.app.markae.common.state.ViewState
import br.com.app.markae.domain.model.Note
import br.com.app.markae.ui.screen.component.AlertDialogComponent
import br.com.app.markae.ui.screen.component.FABMenuComponent
import br.com.app.markae.ui.screen.component.GridNotesComponent
import br.com.app.markae.ui.screen.component.LoadingGrid
import br.com.app.markae.ui.screen.component.NoNoteComponent
import br.com.app.markae.ui.screen.component.OrderTextComponent
import br.com.app.markae.ui.screen.component.SearchNoteComponent
import br.com.app.markae.ui.screen.component.TopBarNoActionComponent
import br.com.app.markae.ui.screen.model.SortOption

@Composable
fun ListNotesScreen(
	onClickNote: (String) -> Unit,
	onClickCreateNote: () -> Unit,
	onClickDeleteAll: () -> Unit,
	onClickSortOption: (SortOption) -> Unit,
	notes: ViewState<List<Note>?>
) {
	val showDialog = remember { mutableStateOf(false) }
	val showOrderTextComponent = remember { mutableStateOf(false) }
	val sortOption = remember { mutableIntStateOf(R.string.none) }

	Scaffold(
		topBar = { TopBarNoActionComponent() },
		floatingActionButton = {
			FABMenuComponent(
				onCreateClick = { onClickCreateNote() },
				onDeleteAllClick = { showDialog.value = true },
				onSortOptionClick = { option ->
					onClickSortOption(option)
					showOrderTextComponent.value = true
					sortOption.intValue = option.label
				}
			)
		},
		content = { paddingValues ->
			Column(
				modifier = Modifier
					.padding(paddingValues)
					.fillMaxSize(),
				content = {
					if (showDialog.value) AlertDialogComponent(
						onDeleteAll = { onClickDeleteAll() },
						showDialog = showDialog
					)

					when (notes) {
						is ViewState.Error -> NoNoteComponent(text = stringResource(R.string.error_action_list_notes))

						is ViewState.Loading -> LoadingGrid()

						is ViewState.Success -> {
							notes.data?.let { list ->
								var query by rememberSaveable { mutableStateOf("") }
								val sortedNotes = remember(list) { list.sortedByDescending { it.pinned } }
								val filteredNotes by remember(sortedNotes, query) {
									derivedStateOf { if (query.isBlank()) sortedNotes else sortedNotes.filter { it.title.contains(query, true) ||
											it.content.contains(query, true) } }
								}

								Column(
									content = {
										SearchNoteComponent(
											items = sortedNotes,
											onItemClick = { n -> query = n.title },
											onClearQuery = { query = "" }
										)
										if (showOrderTextComponent.value) {
											OrderTextComponent(
												clearSortOption = {
													showOrderTextComponent.value = false
													onClickSortOption(SortOption.NONE)
												},
												orderBy = stringResource(sortOption.intValue)
											)
										}
										GridNotesComponent(
											notes = filteredNotes,
											onClickNote = { id -> onClickNote(id) }
										)
									}
								)
							}
						}

						is ViewState.Empty -> NoNoteComponent(text = stringResource(R.string.no_notes))
					}
				}
			)
		}
	)
}

@Preview
@Composable
private fun HomeScreenPreview() {
	ListNotesScreen(
		notes = ViewState.Error(),
		onClickNote = {},
		onClickCreateNote = {},
		onClickDeleteAll = {},
		onClickSortOption = {}
	)
}