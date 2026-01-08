package br.com.app.markae.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun HomeScreen() {
	Scaffold(
		topBar = {

		},
		floatingActionButton = {
			FloatingActionButton(
				onClick = {},
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

				}
			)
		}
	)
}

@Preview
@Composable
private fun HomeScreenPreview() {
	HomeScreen()
}