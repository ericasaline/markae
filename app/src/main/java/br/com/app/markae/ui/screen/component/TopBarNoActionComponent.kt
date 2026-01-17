package br.com.app.markae.ui.screen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BorderColor
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.com.app.markae.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarNoActionComponent() {
	TopAppBar(
		title = {
			Row(
				verticalAlignment = Alignment.CenterVertically,
				horizontalArrangement = Arrangement.Start,
				modifier = Modifier
					.fillMaxWidth()
					.padding(end = 16.dp)
			) {
				Text(
					text = stringResource(R.string.app_name),
					style = MaterialTheme.typography.titleLarge
				)
				Spacer(modifier = Modifier.weight(1f))
				Icon(
					modifier = Modifier.size(24.dp),
					imageVector = Icons.Default.BorderColor,
					contentDescription = stringResource(R.string.app_name)
				)
			}
		},
		colors = TopAppBarDefaults.topAppBarColors(
			containerColor = MaterialTheme.colorScheme.primaryContainer
		)
	)
}