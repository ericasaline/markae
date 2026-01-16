package br.com.app.markae.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.app.markae.R
import br.com.app.markae.core.states.ViewState
import br.com.app.markae.domain.model.Note
import kotlinx.coroutines.launch

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

	ShowSnackbar(
		state = actionAdd,
		snackbarHostState = snackbarHostState,
		onBack = { onClickBack() }
	)
	Scaffold(
		topBar = {
			TopBar(
				onClickBack = { onClickBack() },
				onClickPin = {
					isPinned.value = !isPinned.value
					onClickPin()
				},
				onClickSave = {
					keyboardController?.hide()
					focusManager.clearFocus()
					readOnly.value = true
					if (title.value.isNotEmpty() || content.value.isNotEmpty())
						onClickSave(title.value, content.value, isPinned.value)
				},
				onClickDelete = { showSheet.value = true },
				isNotePinned = isPinned
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

							CreateOrEditNote(
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CreateOrEditNote(
	showSheet: MutableState<Boolean>,
	sheetState: SheetState,
	actionDel: ViewState<Unit>,
	onClickDelete: () -> Unit,
	onBack: () -> Unit,
	title: MutableState<String>,
	content: MutableState<String>,
	initialTitle: String,
	initialContent: String,
	readOnly: MutableState<Boolean>,
) {
	val titleState = rememberTextFieldState(initialText = initialTitle)
	val contentState = rememberTextFieldState(initialText = initialContent)

	LaunchedEffect(titleState) {
		snapshotFlow { titleState.text }.collect { title.value = it.toString() }
	}
	LaunchedEffect(contentState) {
		snapshotFlow { contentState.text }.collect { content.value = it.toString() }
	}

	if (showSheet.value) {
		ShowBottomSheet(
			sheetState = sheetState,
			showSheet = showSheet,
			state = actionDel,
			onBack = { onBack() },
			onClickDelete = { onClickDelete() }
		)
	}
	Column(
		modifier = Modifier.padding(horizontal = 16.dp),
		content = {
			TextField(
				modifier = Modifier.fillMaxWidth(),
				state = titleState,
				placeholder = {
					Text(
						text = stringResource(R.string.title),
						color = Color.Gray,
						style = MaterialTheme.typography.titleLarge
					)
				},
				lineLimits = TextFieldLineLimits.SingleLine,
				textStyle = MaterialTheme.typography.titleLarge,
				colors = TextFieldDefaults.colors(
					focusedContainerColor = MaterialTheme.colorScheme.surface,
					unfocusedContainerColor = MaterialTheme.colorScheme.surface
				),
				readOnly = readOnly.value
			)
			TextField(
				modifier = Modifier.fillMaxWidth(),
				state = contentState,
				placeholder = {
					Text(
						text = stringResource(R.string.content),
						color = Color.Gray,
						style = MaterialTheme.typography.titleMedium
					)
				},
				textStyle = MaterialTheme.typography.titleMedium,
				colors = TextFieldDefaults.colors(
					focusedContainerColor = MaterialTheme.colorScheme.surface,
					unfocusedContainerColor = MaterialTheme.colorScheme.surface
				),
				readOnly = readOnly.value
			)
		}
	)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
	onClickBack: () -> Unit,
	onClickPin: () -> Unit,
	onClickSave: () -> Unit,
	onClickDelete: () -> Unit,
	isNotePinned: MutableState<Boolean>
) {
	TopAppBar(
		title = {},
		navigationIcon = {
			IconButton(
				content = {
					Icon(
						imageVector = Icons.AutoMirrored.Filled.ArrowBack,
						contentDescription = stringResource(R.string.back)
					)
				},
				onClick = { onClickBack.invoke() }
			)
		},
		actions = {
			IconButton(
				content = {
					Icon(
						imageVector = if (isNotePinned.value) Icons.Filled.PushPin else Icons.Outlined.PushPin,
						contentDescription = if (isNotePinned.value) stringResource(R.string.unpin) else stringResource(R.string.pin)
					)
				},
				onClick = { onClickPin.invoke() }
			)
			IconButton(
				content = {
					Icon(
						imageVector = Icons.Default.Done,
						contentDescription = stringResource(R.string.save)
					)
				},
				onClick = { onClickSave.invoke() }
			)
			IconButton(
				content = {
					Icon(
						imageVector = Icons.Default.Delete,
						contentDescription = stringResource(R.string.delete)
					)
				},
				onClick = { onClickDelete.invoke() }
			)
		},
		colors = TopAppBarDefaults.topAppBarColors(
			containerColor = MaterialTheme.colorScheme.primaryContainer
		)
	)
}

@Composable
private fun AppSnackbarHost(
	snackbarHostState: SnackbarHostState
) {
	SnackbarHost(
		hostState = snackbarHostState,
		snackbar = { snackbarData ->
			Column(
				modifier = Modifier
					.fillMaxWidth()
					.padding(16.dp)
					.clip(RoundedCornerShape(12.dp))
					.background(MaterialTheme.colorScheme.primaryContainer)
					.padding(16.dp),
				content = {
					Row(
						verticalAlignment = Alignment.CenterVertically,
						content = {
							Text(
								text = snackbarData.visuals.message,
								color = MaterialTheme.colorScheme.onPrimaryContainer,
								style = MaterialTheme.typography.titleSmall
							)
							Spacer(Modifier.weight(1f))
							snackbarData.visuals.actionLabel?.let { label ->
								TextButton(
									content = {
										Text(
											text = label,
											color = MaterialTheme.colorScheme.primaryContainer,
											style = MaterialTheme.typography.titleSmall
										)
									},
									colors = ButtonDefaults.textButtonColors(
										containerColor = Color.White
									),
									shape = RoundedCornerShape(8.dp),
									onClick = { snackbarData.performAction() }
								)
							}
						}
					)
				}
			)
		}
	)
}

@Composable
private fun ShowSnackbar(
	state: ViewState<Unit>,
	snackbarHostState: SnackbarHostState,
	onBack: () -> Unit
) {
	val successText = stringResource(R.string.success_action)
	val errorText = stringResource(R.string.error_action)
	val closeButtonText = stringResource(R.string.close)

	LaunchedEffect(state) {
		val message = when (state) {
			is ViewState.Success -> successText
			is ViewState.Error -> errorText
			else -> return@LaunchedEffect
		}

		val result = snackbarHostState.showSnackbar(
			message = message,
			actionLabel = closeButtonText
		)

		if (result == SnackbarResult.ActionPerformed) {
			snackbarHostState.currentSnackbarData?.dismiss()
			onBack.invoke()
		}
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BottomSheet(
	showSheet: MutableState<Boolean>,
	sheetState: SheetState,
	onClick1stButton: () -> Unit,
	onClick2ndButton: () -> Unit = {},
	message: String,
	button1stText: String,
	button2ndText: String? = null,
	icon: ImageVector
) {
	ModalBottomSheet(
		onDismissRequest = { showSheet.value = false },
		sheetState = sheetState,
		content = {
			Column(
				modifier = Modifier
					.fillMaxWidth()
					.padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 28.dp),
				horizontalAlignment = Alignment.CenterHorizontally,
				content = {
					Row(
						verticalAlignment = Alignment.CenterVertically,
						horizontalArrangement = Arrangement.Center,
						content = {
							Icon(
								modifier = Modifier.size(42.dp),
								imageVector = icon,
								contentDescription = stringResource(R.string.atention)
							)
							Spacer(modifier = Modifier.height(8.dp))
							Text(
								text = message,
								style = MaterialTheme.typography.titleSmall,
								textAlign = TextAlign.Center
							)
						}
					)
					Spacer(modifier = Modifier.height(24.dp))
					Row(
						content = {
							Button(
								onClick = {
									showSheet.value = false
									onClick1stButton.invoke()
								},
								modifier = Modifier.weight(1f),
								content = {
									Text(
										text = button1stText,
										style = MaterialTheme.typography.titleSmall
									)
								}
							)
							if (!button2ndText.isNullOrEmpty()) {
								Spacer(modifier = Modifier.width(8.dp))
								Button(
									onClick = {
										onClick2ndButton.invoke()
									},
									modifier = Modifier.weight(1f),
									colors = ButtonDefaults.buttonColors(
										containerColor = MaterialTheme.colorScheme.error
									),
									content = {
										Text(
											text = button2ndText,
											color = Color.White,
											style = MaterialTheme.typography.titleSmall
										)
									}
								)
							}
						}
					)
				}
			)
		}
	)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ShowBottomSheet(
	state: ViewState<Unit>,
	showSheet: MutableState<Boolean>,
	sheetState: SheetState,
	onClickDelete: () -> Unit,
	onBack: () -> Unit,
) {
	val coroutineScope = rememberCoroutineScope()

	when (state) {
		is ViewState.Error -> {
			BottomSheet(
				message = stringResource(R.string.error_action),
				button1stText = stringResource(R.string.close),
				showSheet = showSheet,
				sheetState = sheetState,
				onClick1stButton = { coroutineScope.launch { showSheet.value = false } },
				icon = Icons.Filled.Error
			)
		}

		is ViewState.Success<*> -> {
			BottomSheet(
				message = stringResource(R.string.success_action),
				button1stText = stringResource(R.string.close),
				showSheet = showSheet,
				sheetState = sheetState,
				onClick1stButton = {
					coroutineScope.launch { showSheet.value = false }
					onBack()
				},
				icon = Icons.Rounded.AutoAwesome
			)
		}

		ViewState.Loading -> {
			BottomSheet(
				message = stringResource(R.string.delete_note_question),
				button1stText = stringResource(R.string.cancel),
				button2ndText = stringResource(R.string.delete),
				showSheet = showSheet,
				sheetState = sheetState,
				onClick1stButton = { coroutineScope.launch { showSheet.value = false } },
				onClick2ndButton = { onClickDelete() },
				icon = Icons.Filled.Info
			)
		}

		ViewState.Empty -> {}
	}
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