package br.com.app.markae.ui.screen.component

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import br.com.app.markae.R
import br.com.app.markae.common.state.ViewState
import kotlinx.coroutines.launch

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
										containerColor = Color.Red
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
fun ShowBottomSheet(
	state: ViewState<Unit>,
	showSheet: MutableState<Boolean>,
	readOnly: MutableState<Boolean>,
	sheetState: SheetState,
	onClickDelete: () -> Unit,
	onBack: () -> Unit,
) {
	val coroutineScope = rememberCoroutineScope()

	when (state) {
		is ViewState.Error -> {
			Toast.makeText(
				LocalContext.current,
				stringResource(R.string.error_action),
				Toast.LENGTH_SHORT)
			.show()
			onBack()
		}

		is ViewState.Success<*> -> {
			Toast.makeText(
				LocalContext.current,
				stringResource(R.string.success_action),
				Toast.LENGTH_SHORT)
			.show()
			onBack()
		}

		ViewState.Loading -> {
			BottomSheet(
				message = stringResource(R.string.delete_note_question),
				button1stText = stringResource(R.string.cancel),
				button2ndText = stringResource(R.string.delete),
				showSheet = showSheet,
				sheetState = sheetState,
				onClick1stButton = {
					readOnly.value = false
					coroutineScope.launch { showSheet.value = false }
				},
				onClick2ndButton = {
					readOnly.value = true
					onClickDelete()
				},
				icon = Icons.Filled.Info
			)
		}

		ViewState.Empty -> {}
	}
}