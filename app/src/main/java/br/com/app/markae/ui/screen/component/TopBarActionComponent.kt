package br.com.app.markae.ui.screen.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import br.com.app.markae.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarActionComponent(
	onClickBack: () -> Unit,
	onClickPin: () -> Unit,
	onClickShare: () -> Unit,
	onClickSave: () -> Unit,
	onClickDelete: () -> Unit,
	isNotePinned: MutableState<Boolean>,
	readOnly: MutableState<Boolean>
) {
	TopAppBar(
		title = {},
		navigationIcon = {
			IconButton(
				content = {
					Icon(
						imageVector = Icons.Default.ArrowBackIosNew,
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
				onClick = { onClickPin.invoke() },
				enabled = !readOnly.value
			)
			IconButton(
				content = {
					Icon(
						imageVector = Icons.Outlined.Share,
						contentDescription = stringResource(R.string.share)
					)
				},
				onClick = { onClickShare.invoke() },
				enabled = !readOnly.value
			)
			IconButton(
				content = {
					Icon(
						imageVector = Icons.Default.Done,
						contentDescription = stringResource(R.string.save)
					)
				},
				onClick = { onClickSave.invoke() },
				enabled = !readOnly.value
			)
			IconButton(
				content = {
					Icon(
						imageVector = Icons.Default.Delete,
						contentDescription = stringResource(R.string.delete)
					)
				},
				onClick = { onClickDelete.invoke() },
				enabled = !readOnly.value
			)
		},
		colors = TopAppBarDefaults.topAppBarColors(
			containerColor = MaterialTheme.colorScheme.primaryContainer
		)
	)
}