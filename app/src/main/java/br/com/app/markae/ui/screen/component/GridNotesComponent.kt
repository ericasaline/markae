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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import br.com.app.markae.R
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
			items(notes) { note ->
				CardNote(
					title = note.title,
					content = note.content,
					isNotePinned = note.pinned,
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
								modifier = Modifier.padding(end = 8.dp),
								text = title,
								style = MaterialTheme.typography.labelMedium,
								maxLines = 1,
								overflow = TextOverflow.Ellipsis
							)
							if (isNotePinned) {
								Spacer(Modifier.weight(1f))
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
						text = content,
						style = MaterialTheme.typography.bodySmall,
						overflow = TextOverflow.Ellipsis
					)
				}
			)
		}
	)
}