package br.com.app.markae.core.state

sealed class ViewState<out T> {
	object Loading : ViewState<Nothing>()
	object Empty : ViewState<Nothing>()
	data class Success<T>(val data: T) : ViewState<T>()
	data class Error(val throwable: Throwable? = null) : ViewState<Nothing>()
}