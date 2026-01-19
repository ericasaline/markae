package br.com.app.markae.ui.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.com.app.markae.R
import br.com.app.markae.core.state.ViewState

@Composable
fun ShowSnackBar(
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

@Composable
fun AppSnackbarHost(
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
					.background(MaterialTheme.colorScheme.primaryContainer),
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
											color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primaryContainer
												else MaterialTheme.colorScheme.onPrimaryContainer,
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