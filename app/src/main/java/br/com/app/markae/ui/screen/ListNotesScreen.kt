package br.com.app.markae.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults.InputField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.app.markae.R
import br.com.app.markae.core.states.ViewState
import br.com.app.markae.domain.model.Note
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun ListNotesScreen(
	onClickNote: (String) -> Unit,
	onClickCreateNote: () -> Unit,
	notes: ViewState<List<Note>?>,
) {
	var mustShowTopBar = false
	var mustShowSearchBar = true

	Scaffold(
		topBar = {
			TopBar()
		},
		floatingActionButton = {
			FloatingActionButton(
				onClick = {
					onClickCreateNote()
				},
				content = {
					Icon(
						imageVector = Icons.Filled.Edit,
						contentDescription = null
					)
				}
			)
		},
		content = { paddingValues ->
			Column(
				modifier = Modifier
					.padding(paddingValues)
					.fillMaxSize(),
				content = {
					when (notes) {
						is ViewState.Error -> NoNote(text = stringResource(R.string.error_action_list_notes))
						ViewState.Loading -> {

							// tela de loanding

						}
						is ViewState.Success -> {
							if (notes.data.isNullOrEmpty()) NoNote(text = stringResource(R.string.no_notes))
							 else {
								GridNotes(
									notes = notes.data,
									onClickNote = { id ->
										onClickNote(id)
									}
								)
							}
						}

						ViewState.Empty -> {}
					}
				}
			)
		}
	)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar() {
	var expanded by rememberSaveable { mutableStateOf(false) }
	var query by remember { mutableStateOf("") }
	val helpList = listOf(
		"item 1",
		"item 2",
		"item 3",
	).filter { it.contains(query, ignoreCase = true) }

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
						onQueryChange = { searchText ->
							query = searchText
						},
						onSearch = {
							expanded = false
						},
						expanded = expanded,
						onExpandedChange = { expanded = it },
						leadingIcon = {
							Icon(
								imageVector = Icons.Default.Search,
								contentDescription = null
							)
						},
						placeholder = {
							Text(
								text = stringResource(R.string.search_note),
								fontSize = 18.sp,
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
									headlineContent = { Text(text = resultText) },
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
										.clickable {
											expanded = false
										}
										.fillMaxWidth()
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar() {
	TopAppBar(
		title = {
			Row(
				verticalAlignment = Alignment.CenterVertically,
				horizontalArrangement = Arrangement.Start,
				modifier = Modifier
					.fillMaxWidth()
					.padding(end = 16.dp)
			) {
				Text(
					text = stringResource(R.string.app_name),
					style = MaterialTheme.typography.titleLarge
				)
				Spacer(modifier = Modifier.weight(1f))
				Icon(
					modifier = Modifier.size(32.dp),
					imageVector =  Icons.Filled.EditNote,
					contentDescription = null
				)
			}
		},
		colors = TopAppBarDefaults.topAppBarColors(
			containerColor = MaterialTheme.colorScheme.primaryContainer
		)
	)
}

@Composable
private fun GridNotes(
	notes: List<Note>,
	onClickNote: (String) -> Unit
) {
	SearchBar()
	LazyVerticalGrid(
		modifier = Modifier.padding(top = 16.dp),
		columns = GridCells.Fixed(2),
		contentPadding = PaddingValues(4.dp),
		horizontalArrangement = Arrangement.spacedBy(2.dp),
		verticalArrangement = Arrangement.spacedBy(2.dp)
	) {
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
			.clickable {
				onClickNote()
			},
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
									contentDescription = null
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

@Composable
private fun NoNote(
	text: String
) {
	Column(
		modifier = Modifier.fillMaxSize(),
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally,
		content = {
			Text(
				modifier = Modifier
					.padding(horizontal = 16.dp)
					.fillMaxWidth(),
				text = text,
				textAlign = TextAlign.Center,
				style = MaterialTheme.typography.labelSmall
			)
			Animation()
		}
	)
}

@Composable
private fun Animation() {
	val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.playing_cat))
	val progress by animateLottieCompositionAsState(
		composition = composition,
		iterations = LottieConstants.IterateForever
	)

	LottieAnimation(
		composition = composition,
		progress = { progress },
		modifier = Modifier.size(500.dp)
	)
}

@Preview
@Composable
private fun HomeScreenPreview() {
	ListNotesScreen(
		notes = ViewState.Error(),
		onClickNote = {},
		onClickCreateNote = {}
	)
}