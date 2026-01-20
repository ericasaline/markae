package br.com.app.markae.common.utils

import android.content.Context
import android.content.Intent

fun shareText(
	context: Context,
	text: String,
	title: String? = null
) {
	val sendIntent = Intent(Intent.ACTION_SEND).apply {
		type = "text/plain"
		putExtra(Intent.EXTRA_TEXT, text)
	}
	val shareIntent = Intent.createChooser(sendIntent, title)
	context.startActivity(shareIntent)
}