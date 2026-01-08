package br.com.app.markae.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.app.markae.ui.navigation.MarkaeRoutes.HOME_SCREEN
import br.com.app.markae.ui.screen.HomeScreen

@Composable
fun MarkaeNavGraph() {
	val navController = rememberNavController()

	NavHost(navController = navController, startDestination = HOME_SCREEN) {
		composable(route = HOME_SCREEN) {
			HomeScreen()
		}
	}
}