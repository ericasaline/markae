package br.com.app.markae.common.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatDate(timestamp: Long): String {
	val locale = Locale("pt", "BR")
	val sdf = SimpleDateFormat("dd/MM/yyyy 'às' HH:mm", locale)
	return sdf.format(Date(timestamp))
}