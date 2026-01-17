package br.com.app.markae.ui.screen.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.com.app.markae.R
import br.com.app.markae.core.states.ViewState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateOrEditNoteComponent(
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
		modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
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
					unfocusedContainerColor = MaterialTheme.colorScheme.surface,
					unfocusedIndicatorColor = Color.Transparent
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
					unfocusedContainerColor = MaterialTheme.colorScheme.surface,
					unfocusedIndicatorColor = Color.Transparent
				),
				readOnly = readOnly.value
			)
		}
	)
}