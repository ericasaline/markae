package br.com.app.markae.domain.model

data class Note(
	val id: String? = null,
	val title: String,
	val content: String,
	val pinned: Boolean = false,
	val createdAt: Long,
	val updatedAt: Long? = null
)