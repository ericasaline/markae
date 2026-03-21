package br.com.app.markae.ui.screen.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.com.app.markae.R

@Composable
fun OrderTextComponent(
	orderBy: String,
	clearSortOption: () -> Unit
) {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.padding(top = 16.dp),
		verticalAlignment = Alignment.CenterVertically,
		content = {
			Spacer(modifier = Modifier.weight(1f))
			Text(
				style = MaterialTheme.typography.bodySmall,
				text = stringResource(R.string.order_by_, orderBy)
			)
			IconButton(
				content = {
					Icon(
						imageVector = Icons.Default.Clear,
						contentDescription = stringResource(R.string.clear_order)
					)
				},
				onClick = { clearSortOption.invoke() }
			)
		}
	)
}