package br.com.app.markae.ui.screen.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.SortByAlpha
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
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
import br.com.app.markae.ui.screen.model.SortOption

@Composable
fun FABMenuComponent(
	onCreateClick: () -> Unit,
	onDeleteAllClick: () -> Unit,
	onSortOptionClick: (SortOption) -> Unit
) {
	var expanded by remember { mutableStateOf(false) }
	var expandedSortOption by remember { mutableStateOf(false) }

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
								onClick = {
									expanded = false
									onDeleteAllClick.invoke()
								},
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
								onClick = { expandedSortOption = !expandedSortOption },
								icon = {
									Icon(
										imageVector = Icons.AutoMirrored.Filled.Sort,
										contentDescription = stringResource(R.string.order)
									)
								},
								text = {
									Text(
										text = if (expandedSortOption) stringResource(R.string.order_by)
										       else stringResource(R.string.order),
										style = MaterialTheme.typography.labelMedium
									)
								},
								containerColor = MaterialTheme.colorScheme.primaryContainer
							)
						}
					)
					AnimatedVisibility(
						visible = expandedSortOption,
						enter = fadeIn() + slideInVertically { it },
						exit = fadeOut() + slideOutVertically { it },
						content = {
							Column(
								horizontalAlignment = Alignment.End,
								content = {
									SmallFloatingActionButton(
										content = {
											Column(
												modifier = Modifier.padding(8.dp),
												content = {
													Row(
														verticalAlignment = Alignment.CenterVertically,
														content = {
															Icon(
																imageVector = Icons.Default.SortByAlpha,
																contentDescription = stringResource(R.string.title)
															)
															Spacer(Modifier.width(8.dp))
															Text(
																text = stringResource(R.string.title),
																style = MaterialTheme.typography.labelMedium
															)
														}
													)
												}
											)
										},
										onClick = {
											expandedSortOption = !expandedSortOption
											onSortOptionClick.invoke(SortOption.TITLE)
											expanded = !expanded
										}
									)
									Spacer(Modifier.height(8.dp))
									SmallFloatingActionButton(
										content = {
											Column(
												modifier = Modifier.padding(8.dp),
												content = {
													Row(
														verticalAlignment = Alignment.CenterVertically,
														content = {
															Icon(
																imageVector = Icons.Default.CalendarToday,
																contentDescription = stringResource(R.string.created_at)
															)
															Spacer(Modifier.width(8.dp))
															Text(
																text = stringResource(R.string.created_at),
																style = MaterialTheme.typography.labelMedium
															)
														}
													)
												}
											)
										},
										onClick = {
											expandedSortOption = !expandedSortOption
											onSortOptionClick.invoke(SortOption.CREATED_AT)
											expanded = !expanded
										}
									)
									Spacer(Modifier.height(8.dp))
									SmallFloatingActionButton(
										content = {
											Column(
												modifier = Modifier.padding(8.dp),
												content = {
													Row(
														verticalAlignment = Alignment.CenterVertically,
														content = {
															Icon(
																imageVector = Icons.Default.EditCalendar,
																contentDescription = stringResource(R.string.edited_at)
															)
															Spacer(Modifier.width(8.dp))
															Text(
																text = stringResource(R.string.edited_at),
																style = MaterialTheme.typography.labelMedium
															)
														}
													)
												}
											)
										},
										onClick = {
											expandedSortOption = !expandedSortOption
											onSortOptionClick.invoke(SortOption.UPDATED_AT)
											expanded = !expanded
										}
									)
								}
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