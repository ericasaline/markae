package br.com.app.markae.core.utils

suspend fun <T> safeCall(
	action: suspend () -> T
): Result<T> {
	return try { Result.success(action()) }
	catch (e: Exception) { Result.failure(e) }
}