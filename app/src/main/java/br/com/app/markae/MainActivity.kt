package br.com.app.markae

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import br.com.app.markae.ui.navigation.MarkaeNavGraph
import br.com.app.markae.ui.theme.MarkaeTheme

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContent {
			MarkaeTheme {
				MarkaeNavGraph()
			}
		}
	}
}