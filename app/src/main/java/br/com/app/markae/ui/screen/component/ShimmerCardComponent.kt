package br.com.app.markae.ui.screen.component

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.com.app.markae.R

@Composable
fun LoadingGrid() {
	SearchNoteComponent()
	LazyVerticalGrid(
		modifier = Modifier.padding(top = 16.dp),
		columns = GridCells.Fixed(2),
		contentPadding = PaddingValues(4.dp),
		horizontalArrangement = Arrangement.spacedBy(2.dp),
		verticalArrangement = Arrangement.spacedBy(2.dp),
		content = {
			items(4) {
				ShimmerCard()
			}
		}
	)
}

@Composable
private fun ShimmerCard() {
	val shimmerColors = if (isSystemInDarkTheme()) {
		listOf(
			Color.DarkGray.copy(alpha = 0.9f),
			Color.DarkGray.copy(alpha = 0.3f),
			Color.DarkGray.copy(alpha = 0.9f)
		)
	} else {
		listOf(
			Color.LightGray.copy(alpha = 0.9f),
			Color.LightGray.copy(alpha = 0.3f),
			Color.LightGray.copy(alpha = 0.9f)
		)
	}
	val label = stringResource(R.string.loading)
	val transition = rememberInfiniteTransition(label = label)
	val translateAnim = transition.animateFloat(
		initialValue = 0f,
		targetValue = 1000f,
		label = label,
		animationSpec = infiniteRepeatable(
			animation = tween(
				durationMillis = 1200,
				easing = LinearEasing
			)
		)
	)
	val brush = Brush.linearGradient(
		colors = shimmerColors,
		start = Offset(translateAnim.value - 1000f, 0f),
		end = Offset(translateAnim.value, 0f)
	)

	Card(
		modifier = Modifier
			.width(190.dp)
			.height(210.dp)
			.padding(12.dp),
		shape = RoundedCornerShape(12.dp),
		elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
		content = {
			Box(
				modifier = Modifier
					.fillMaxSize()
					.background(brush)
			)
		}
	)
}