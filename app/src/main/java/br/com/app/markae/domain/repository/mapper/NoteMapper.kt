package br.com.app.markae.domain.repository.mapper

import br.com.app.markae.data.local.entity.NoteEntity
import br.com.app.markae.domain.model.Note
import java.util.UUID

object NoteMapper {
	fun Note.toEntity() = NoteEntity(
		id = this.id ?: UUID.randomUUID().toString(),
		title = this.title,
		content = this.content,
		pinned = this.pinned
	)

	fun NoteEntity?.toDomain() = Note(
		id = this?.id,
		title = this?.title ?: "",
		content = this?.content ?: "",
		pinned = this?.pinned == true
	)
}