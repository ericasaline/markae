package br.com.app.markae.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.app.markae.R

@Composable
fun CreateNote(
	onClickBack: () -> Unit
) {
	Scaffold(
		topBar = {
			TopBar(
				onClickBack = { onClickBack() },
				onClickPin = {},
				onClickSave = {},
				onClickDelete = {},
				isNotePinned = true
			)
		},
		content = { paddingValues ->
			Column(
				modifier = Modifier
					.padding(paddingValues)
					.fillMaxSize(),
				content = {
					Column(
						modifier = Modifier.padding(horizontal = 16.dp),
						content = {
							TextField(
								modifier = Modifier.fillMaxWidth(),
								state = rememberTextFieldState(),
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
								)
							)

							TextField(
								modifier = Modifier.fillMaxWidth(),
								state = rememberTextFieldState(),
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
								)
							)
						}
					)
				}
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
	isNotePinned: Boolean
) {
	TopAppBar(
		title = {},
		navigationIcon = {
			IconButton(
				content = {
					Icon(
						imageVector = Icons.AutoMirrored.Filled.ArrowBack,
						contentDescription = null
					)
				},
				onClick = {
					onClickBack()
				}
			)
		},
		actions = {
			IconButton(
				content = {
					Icon(
						imageVector = if (isNotePinned) Icons.Filled.PushPin else Icons.Outlined.PushPin,
						contentDescription = null
					)
				},
				onClick = {
					onClickPin()
				}
			)
			IconButton(
				content = {
					Icon(
						imageVector = Icons.Default.Done,
						contentDescription = null
					)
				},
				onClick = {
					onClickSave()
				}
			)
			IconButton(
				content = {
					Icon(
						imageVector = Icons.Default.Delete,
						contentDescription = null
					)
				},
				onClick = {
					onClickDelete()
				}
			)
		},
		colors = TopAppBarDefaults.topAppBarColors(
			containerColor = MaterialTheme.colorScheme.primaryContainer
		)
	)
}

@Preview
@Composable
private fun CreateNotePreview() {
	CreateNote(
		onClickBack = {}
	)
}