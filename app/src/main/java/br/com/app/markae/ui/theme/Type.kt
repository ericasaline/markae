package br.com.app.markae.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import br.com.app.markae.R

val fontRegular = FontFamily(Font(R.font.comic_neue_regular))
val fontLight = FontFamily(Font(R.font.comic_neue_light))
val fontBold = FontFamily(Font(R.font.comic_neue_bold))

val Typography = Typography(
	bodyLarge = TextStyle(
		fontFamily = fontRegular,
		fontWeight = FontWeight.Normal,
		fontSize = 22.sp,
		lineHeight = 22.sp,
		letterSpacing = 0.5.sp
	),
	bodyMedium = TextStyle(
		fontFamily = fontRegular,
		fontWeight = FontWeight.Normal,
		fontSize = 16.sp,
		lineHeight = 16.sp,
		letterSpacing = 0.5.sp
	),
	bodySmall = TextStyle(
		fontFamily = fontRegular,
		fontWeight = FontWeight.Normal,
		fontSize = 14.sp,
		lineHeight = 14.sp,
		letterSpacing = 0.5.sp
	),
	labelLarge = TextStyle(
		fontFamily = fontBold,
		fontWeight = FontWeight.Normal,
		fontSize = 22.sp,
		lineHeight = 22.sp,
		letterSpacing = 0.5.sp
	),
	labelMedium = TextStyle(
		fontFamily = fontBold,
		fontWeight = FontWeight.Normal,
		fontSize = 16.sp,
		lineHeight = 16.sp,
		letterSpacing = 0.5.sp
	),
	labelSmall = TextStyle(
		fontFamily = fontLight,
		fontWeight = FontWeight.Normal,
		fontSize = 16.sp,
		lineHeight = 16.sp,
		letterSpacing = 0.5.sp
	),
	titleLarge = TextStyle(
		fontFamily = fontBold,
		fontWeight = FontWeight.Normal,
		fontSize = 24.sp,
		lineHeight = 24.sp,
		letterSpacing = 0.5.sp
	),
	titleMedium = TextStyle(
		fontFamily = fontBold,
		fontWeight = FontWeight.Normal,
		fontSize = 20.sp,
		lineHeight = 20.sp,
		letterSpacing = 0.5.sp
	)
)