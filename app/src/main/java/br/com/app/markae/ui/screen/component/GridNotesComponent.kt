package br.com.app.markae.ui.screen.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import br.com.app.markae.R
import br.com.app.markae.common.utils.formatDate
import br.com.app.markae.domain.model.Note

@Composable
fun GridNotesComponent(
	notes: List<Note>,
	onClickNote: (String) -> Unit
) {
	LazyVerticalGrid(
		modifier = Modifier.padding(top = 16.dp),
		columns = GridCells.Fixed(2),
		contentPadding = PaddingValues(4.dp),
		horizontalArrangement = Arrangement.spacedBy(2.dp),
		verticalArrangement = Arrangement.spacedBy(2.dp),
		content = {
			items(items = notes, key = { note -> note.id.toString() }) { note ->
				CardNote(
					title = note.title,
					content = note.content,
					isNotePinned = note.pinned,
					createdAt = note.createdAt,
					updatedAt = note.updatedAt,
					onClickNote = {
						onClickNote.invoke(note.id.toString())
					}
				)
			}
		}
	)
}

@Composable
private fun CardNote(
	title: String,
	content: String,
	isNotePinned: Boolean,
	createdAt: Long,
	updatedAt: Long? = null,
	onClickNote: () -> Unit
) {
	Card(
		modifier = Modifier
			.width(180.dp)
			.height(210.dp)
			.padding(8.dp)
			.clickable { onClickNote() },
		elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
		content = {
			Column(
				modifier = Modifier.padding(16.dp),
				content = {
					Row(
						modifier = Modifier.fillMaxWidth(),
						horizontalArrangement = Arrangement.Start,
						verticalAlignment = Alignment.CenterVertically,
						content = {
							Text(
								modifier = Modifier
									.weight(1f)
									.padding(end = 8.dp),
								text = title,
								style = MaterialTheme.typography.labelMedium,
								maxLines = 1,
								overflow = TextOverflow.Ellipsis
							)
							if (isNotePinned) {
								Icon(
									modifier = Modifier.size(16.dp),
									imageVector = Icons.Filled.PushPin,
									contentDescription = stringResource(R.string.pin)
								)
							}
						}
					)
					Spacer(modifier = Modifier.height(8.dp))
					Text(
						modifier = Modifier.weight(1f),
						text = content,
						style = MaterialTheme.typography.bodySmall,
						overflow = TextOverflow.Ellipsis
					)
					Spacer(modifier = Modifier.height(16.dp))
					Text(
						modifier = Modifier.fillMaxWidth(),
						text = updatedAt?.let { stringResource(R.string.edited, formatDate(it)) }
							?: stringResource(R.string.created, formatDate(createdAt)),
						textAlign = TextAlign.End,
						style = MaterialTheme.typography.displaySmall,
						overflow = TextOverflow.Ellipsis,
						maxLines = 1
					)
				}
			)
		}
	)
}