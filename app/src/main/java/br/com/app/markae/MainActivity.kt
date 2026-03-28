package br.com.app.markae

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import br.com.app.markae.ui.navigation.MarkaeNavGraph
import br.com.app.markae.ui.theme.MarkaeTheme

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
			installSplashScreen().setOnExitAnimationListener { splash ->
				splash.view.animate()
					.alpha(0f)
					.setDuration(1000)
					.withEndAction { splash.remove() }
					.start()
			}
		}
		setContent {
			MarkaeTheme {
				MarkaeNavGraph()
			}
		}
	}
}