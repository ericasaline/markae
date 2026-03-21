package br.com.app.markae.ui.screen.model

import androidx.annotation.StringRes
import br.com.app.markae.R

enum class SortOption(@field:StringRes val label: Int) {
	NONE(label = R.string.none),
	TITLE(label = R.string.title),
	CREATED_AT(label = R.string.created_at),
	UPDATED_AT(label = R.string.edited_at)
}