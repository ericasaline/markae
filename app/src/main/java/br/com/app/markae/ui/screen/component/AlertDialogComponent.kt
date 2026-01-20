package br.com.app.markae.ui.screen.component

import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import br.com.app.markae.R

@Composable
fun AlertDialogComponent(
	showDialog: MutableState<Boolean>,
	onDeleteAll: () -> Unit
) {
	AlertDialog(
		modifier = Modifier.border(
			width = 3.dp,
			color = Color.Red,
			shape = RoundedCornerShape(24.dp)
		),
		onDismissRequest = { showDialog.value = false },
		title = {
			Row(
				horizontalArrangement = Arrangement.Center,
				modifier = Modifier.fillMaxWidth(),
				content = {
					LottieAnimationComponent(
						size = 100.dp,
						raw = R.raw.thinking
					)
				}
			)
		},
		text = {
			Text(
				text = stringResource(R.string.delete_all_notes_question),
				style = MaterialTheme.typography.labelMedium,
				textAlign = TextAlign.Center
			)
		},
		confirmButton = {
			TextButton(
				onClick = {
					onDeleteAll.invoke()
					showDialog.value = false
				},
				content = {
					Text(
						color = Color.White,
						text = stringResource(R.string.delete),
						style = MaterialTheme.typography.titleSmall
					)
				},
				colors = ButtonDefaults.textButtonColors(
					containerColor = Color.Red
				),
				shape = RoundedCornerShape(8.dp)
			)
		},
		dismissButton = {
			TextButton(
				onClick = { showDialog.value = false },
				content = {
					Text(
						color = Color.White,
						text = stringResource(R.string.cancel),
						style = MaterialTheme.typography.titleSmall
					)
				},
				colors = ButtonDefaults.textButtonColors(
					containerColor = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primaryContainer
						else MaterialTheme.colorScheme.onPrimaryContainer
				),
				shape = RoundedCornerShape(8.dp)
			)
		}
	)
}