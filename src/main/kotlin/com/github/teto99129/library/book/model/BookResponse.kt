package com.github.teto99129.library.book.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.github.teto99129.library.author.model.Author
import java.time.OffsetDateTime

data class BookResponse(
	val bookID: Int,
	val title: String,
	val value: Int,
	@JsonProperty("publication_status")
	val publicationStatus: Short,
	@JsonProperty("publication_status_label")
	val publicationStatusLabel: String,
	@JsonProperty("created_at")
	val createdAt: OffsetDateTime,
	val authors: List<Author>,
) {
	companion object {
		fun from(book: Book): BookResponse =
			BookResponse(
				bookID = book.bookID,
				title = book.title,
				value = book.value,
				publicationStatus = book.publicationStatus.code,
				publicationStatusLabel = book.publicationStatus.label,
				createdAt = book.createdAt,
				authors = book.authors,
			)
	}
}
