package br.com.app.markae.ui.screen.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.com.app.markae.R

@Composable
fun FABMenuComponent(
	onCreateClick: () -> Unit,
	onDeleteAllClick: () -> Unit,
	onOrderClick: () -> Unit
) {
	var expanded by remember { mutableStateOf(false) }

	Box(
		modifier = Modifier.fillMaxSize(),
		contentAlignment = Alignment.BottomEnd,
		content = {
			Column(
				horizontalAlignment = Alignment.End,
				verticalArrangement = Arrangement.spacedBy(16.dp),
				modifier = Modifier.padding(bottom = 80.dp, end = 16.dp),
				content = {
					AnimatedVisibility(
						visible = expanded,
						content = {
							ExtendedFloatingActionButton(
								onClick = onDeleteAllClick,
								icon = {
									Icon(
										imageVector = Icons.Default.Delete,
										contentDescription = stringResource(R.string.delete_all),
										tint = Color.White
									)
								},
								text = {
									Text(
										text = stringResource(R.string.delete_all),
										style = MaterialTheme.typography.labelMedium,
										color = Color.White
									)
								},
								containerColor = Color.Red
							)
						}
					)

					AnimatedVisibility(
						visible = expanded,
						content = {
							ExtendedFloatingActionButton(
								onClick = onOrderClick,
								icon = {
									Icon(
										imageVector = Icons.AutoMirrored.Filled.Sort,
										contentDescription = stringResource(R.string.order)
									)
								},
								text = {
									Text(
										text = stringResource(R.string.order),
										style = MaterialTheme.typography.labelMedium
									)
								},
								containerColor = MaterialTheme.colorScheme.primaryContainer
							)
						}
					)

					AnimatedVisibility(
						visible = expanded,
						content = {
							ExtendedFloatingActionButton(
								onClick = { onCreateClick.invoke() },
								icon = {
									Icon(
										imageVector = Icons.Filled.Edit,
										contentDescription = stringResource(R.string.create_note)
									)
								},
								text = {
									Text(
										text = stringResource(R.string.create_note),
										style = MaterialTheme.typography.labelMedium
									)
								},
								containerColor = MaterialTheme.colorScheme.primaryContainer
							)
						}
					)
				}
			)
			FloatingActionButton(
				onClick = { expanded = !expanded },
				containerColor = MaterialTheme.colorScheme.primaryContainer,
				content = {
					Icon(
						imageVector = if (expanded) Icons.Default.Close else Icons.Default.Menu,
						contentDescription = stringResource(R.string.menu)
					)
				}
			)
		}
	)
}