package br.com.app.markae.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import br.com.app.markae.common.state.ViewState
import br.com.app.markae.common.utils.shareText
import br.com.app.markae.domain.model.Note
import br.com.app.markae.ui.screen.component.AppSnackbarHost
import br.com.app.markae.ui.screen.component.CreateOrEditNoteComponent
import br.com.app.markae.ui.screen.component.ShowSnackBar
import br.com.app.markae.ui.screen.component.TopBarActionComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(
	onClickBack: () -> Unit,
	onClickSave: (String, String, Boolean) -> Unit,
	onClickDelete: () -> Unit,
	onClickPin: () -> Unit,
	actionAdd: ViewState<Unit>,
	actionDel: ViewState<Unit>,
	note: ViewState<Note?>
) {
	var title = remember { mutableStateOf("") }
	var content = remember { mutableStateOf("") }
	var id = remember { mutableStateOf("") }
	var isPinned = remember { mutableStateOf(false) }
	var readOnly = remember { mutableStateOf(false) }
	val snackbarHostState = remember { SnackbarHostState() }
	val sheetState = rememberModalBottomSheetState()
	var showSheet = remember { mutableStateOf(false) }
	val keyboardController = LocalSoftwareKeyboardController.current
	val focusManager = LocalFocusManager.current
	val context = LocalContext.current

	ShowSnackBar(
		state = actionAdd,
		snackbarHostState = snackbarHostState,
		onBack = { onClickBack() }
	)
	Scaffold(
		topBar = {
			TopBarActionComponent(
				onClickBack = { onClickBack() },
				onClickShare = {
					shareText(
						context = context,
						text = "${title.value} \n ${content.value}"
					)
				},
				onClickPin = {
					isPinned.value = !isPinned.value
					if (title.value.isNotEmpty() || content.value.isNotEmpty()) onClickPin()
				},
				onClickSave = {
					keyboardController?.hide()
					focusManager.clearFocus()
					if (title.value.isNotEmpty() || content.value.isNotEmpty()) {
						readOnly.value = true
						onClickSave(title.value, content.value, isPinned.value)
					}
				},
				onClickDelete = { showSheet.value = true },
				isNotePinned = isPinned,
				readOnly = readOnly
			)
		},
		snackbarHost = { AppSnackbarHost(snackbarHostState) },
		content = { paddingValues ->
			Column(
				modifier = Modifier
					.padding(paddingValues)
					.verticalScroll(rememberScrollState())
					.fillMaxSize(),
				content = {
					val noteData = (note as? ViewState.Success)?.data

					val initialTitle = noteData?.title.orEmpty()
					val initialContent = noteData?.content.orEmpty()

					when (note) {
						is ViewState.Loading -> {}

						is ViewState.Success,
						ViewState.Empty -> {

							LaunchedEffect(noteData) {
								noteData?.let { n ->
									id.value = n.id.orEmpty()
									isPinned.value = n.pinned
								}
							}

							CreateOrEditNoteComponent(
								showSheet = showSheet,
								sheetState = sheetState,
								actionDel = actionDel,
								onClickDelete = { onClickDelete() },
								onBack = { onClickBack() },
								title = title,
								content = content,
								initialTitle = initialTitle,
								initialContent = initialContent,
								readOnly = readOnly
							)
						}

						is ViewState.Error -> {}
					}
				}
			)
		}
	)
}

@Preview
@Composable
private fun CreateNotePreview() {
	NoteScreen(
		onClickBack = {},
		onClickDelete = {},
		onClickPin = {},
		onClickSave = { _, _, _-> },
		actionAdd = ViewState.Error(),
		actionDel = ViewState.Error(),
		note = ViewState.Error()
	)
}