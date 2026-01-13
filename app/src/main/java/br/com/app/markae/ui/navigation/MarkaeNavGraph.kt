package br.com.app.markae.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.app.markae.ui.navigation.MarkaeRoutes.CREATE_SCREEN
import br.com.app.markae.ui.navigation.MarkaeRoutes.NOTE_SCREEN
import br.com.app.markae.ui.screen.CreateNote
import br.com.app.markae.ui.screen.NoteScreen

@Composable
fun MarkaeNavGraph() {
	val navController = rememberNavController()

	NavHost(navController = navController, startDestination = NOTE_SCREEN) {
		composable(
			enterTransition = {
				slideInHorizontally(
					initialOffsetX = { it },
					animationSpec = tween(1500)
				)
			},
			route = NOTE_SCREEN) {
			NoteScreen(
				onClickNote = {},
				onClickCreateNote = {
					navController.navigate(route = CREATE_SCREEN)
				}
			)
		}

		composable(
			route = CREATE_SCREEN,
			enterTransition = {
				slideInHorizontally(
					initialOffsetX = { it },
					animationSpec = tween(1500)
				)
			},
			popExitTransition = {
				slideOutHorizontally(
					targetOffsetX = { it },
					animationSpec = tween(1500)
				)
			}
		) {
			CreateNote(
				onClickBack = {
					navController.popBackStack()
				}
			)
		}
	}
}