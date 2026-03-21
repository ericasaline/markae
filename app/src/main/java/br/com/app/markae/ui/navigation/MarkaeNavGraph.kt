package br.com.app.markae.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.app.markae.common.state.ViewState
import br.com.app.markae.ui.navigation.route.MarkaeRoutes.LIST_NOTES_SCREEN
import br.com.app.markae.ui.navigation.route.MarkaeRoutes.NOTE_SCREEN
import br.com.app.markae.ui.screen.ListNotesScreen
import br.com.app.markae.ui.screen.NoteScreen
import br.com.app.markae.ui.viewmodel.ListNotesViewModel
import br.com.app.markae.ui.viewmodel.NoteViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MarkaeNavGraph() {
	val navController = rememberNavController()

	NavHost(navController = navController, startDestination = LIST_NOTES_SCREEN) {
		composable(
			enterTransition = {
				slideInHorizontally(
					initialOffsetX = { it },
					animationSpec = tween(1000)
				)
			},
			route = LIST_NOTES_SCREEN
		) {
			val listNotesViewModel = koinViewModel<ListNotesViewModel>()

			LaunchedEffect(Unit) { listNotesViewModel.listNotes() }

			ListNotesScreen(
				notes = listNotesViewModel.notes.collectAsStateWithLifecycle().value,
				onClickNote = { id -> navController.navigate(route = NOTE_SCREEN.replace("{id}", id)) },
				onClickCreateNote = { navController.navigate(route = NOTE_SCREEN.replace("{id}", "")) },
				onClickDeleteAll = { listNotesViewModel.deleteAllNotes() },
				onClickSortOption = { option -> listNotesViewModel.orderNotesBy(sortOption = option) }
			)
		}

		composable(
			route = NOTE_SCREEN,
			arguments = listOf(
				navArgument("id") {
					type = NavType.StringType
					nullable = true
					defaultValue = null
				}
			),
			enterTransition = {
				slideInHorizontally(
					initialOffsetX = { it },
					animationSpec = tween(1000)
				)
			},
			popExitTransition = {
				slideOutHorizontally(
					targetOffsetX = { it },
					animationSpec = tween(1000)
				)
			}
		) {
			val noteViewModel = koinViewModel<NoteViewModel>()

			NoteScreen(
				onClickBack = { navController.popBackStack() },
				onClickPin = { noteViewModel.pinNote() },
				onClickSave = { title, content, isPinned -> noteViewModel.addNote(title = title, content = content, isPinned = isPinned) },
				onClickDelete = { noteViewModel.delNote() },
				note = noteViewModel.note.collectAsStateWithLifecycle().value,
				actionAdd = noteViewModel.addAction.collectAsStateWithLifecycle(initialValue = ViewState.Loading).value,
				actionDel = noteViewModel.delAction.collectAsStateWithLifecycle(initialValue = ViewState.Loading).value
			)
		}
	}
}