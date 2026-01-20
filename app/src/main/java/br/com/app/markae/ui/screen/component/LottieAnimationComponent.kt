package br.com.app.markae.ui.screen.component

import androidx.annotation.RawRes
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun LottieAnimationComponent(
	size: Dp,
	@RawRes raw: Int
) {
	val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(raw))
	val progress by animateLottieCompositionAsState(
		composition = composition,
		iterations = LottieConstants.IterateForever
	)

	LottieAnimation(
		composition = composition,
		progress = { progress },
		modifier = Modifier.size(size)
	)
}