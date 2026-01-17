package br.com.app.markae.ui.screen.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults.InputField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import br.com.app.markae.R
import br.com.app.markae.domain.model.Note

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchNoteComponent(
	items: List<Note> = emptyList(),
	onItemClick: (Note) -> Unit = {},
	onClearQuery: () -> Unit = {}
) {
	var expanded by rememberSaveable { mutableStateOf(false) }
	var query by remember { mutableStateOf("") }
	val helpList = items.filter { note ->
		note.title.contains(query, ignoreCase = true) ||
			note.content.contains(query, ignoreCase = true) }

	Box(
		modifier = Modifier.semantics { isTraversalGroup = true },
		content = {
			SearchBar(
				modifier = Modifier
					.fillMaxWidth()
					.align(Alignment.TopCenter)
					.semantics { traversalIndex = 0f },
				tonalElevation = 0.dp ,
				inputField = {
					InputField(
						query = query,
						onQueryChange = { searchText -> query = searchText },
						onSearch = { expanded = false },
						expanded = expanded,
						onExpandedChange = { expanded = it },
						leadingIcon = {
							Icon(
								imageVector = Icons.Default.Search,
								contentDescription = stringResource(R.string.search)
							)
						},
						trailingIcon = {
							if (query.isNotEmpty()) {
								IconButton(
									onClick = {
										expanded = false
										query = ""
										onClearQuery()
									},
									content = {
										Icon(
											imageVector = Icons.Rounded.Cancel,
											contentDescription = stringResource(R.string.clear)
										)
									}
								)
							}
						},
						placeholder = {
							Text(
								text = stringResource(R.string.search_note),
								style = MaterialTheme.typography.titleSmall,
								color = Color.Gray
							)
						}
					)
				},
				expanded = expanded,
				onExpandedChange = { expanded = it },
				content = {
					LazyColumn(
						content = {
							items(count = helpList.size) { index ->
								val resultText = helpList[index]

								ListItem(
									headlineContent = {
										Row(
											modifier = Modifier.fillMaxWidth(),
											content = {
												Text(
													text = resultText.title,
													style = MaterialTheme.typography.titleSmall,
													maxLines = 1,
													overflow = TextOverflow.Ellipsis
												)
												Text(
													text = " ",
													style = MaterialTheme.typography.titleSmall,
													maxLines = 1
												)
												Text(
													text = resultText.content,
													style = MaterialTheme.typography.titleSmall,
													maxLines = 1,
													overflow = TextOverflow.Ellipsis
												)
											}
										)
									},
									supportingContent = {
										if (query.isNotEmpty()) {
											Text(
												text = stringResource(id = R.string.search_results, query),
												style = MaterialTheme.typography.labelSmall
											)
										}
									},
									colors = ListItemDefaults.colors(containerColor = Color.Transparent),
									modifier = Modifier
										.fillMaxWidth()
										.clickable {
											onItemClick.invoke(resultText)
											expanded = false
											query = resultText.title
										}
										.padding(
											horizontal = 16.dp,
											vertical = 4.dp
										)
								)
							}
						}
					)
				}
			)
		}
	)
}