package br.com.app.markae.ui.screen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import br.com.app.markae.R
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun NoNoteComponent(
	text: String
) {
	val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.playing_cat))
	val progress by animateLottieCompositionAsState(
		composition = composition,
		iterations = LottieConstants.IterateForever
	)

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
			LottieAnimation(
				composition = composition,
				progress = { progress },
				modifier = Modifier.size(500.dp)
			)
		}
	)
}