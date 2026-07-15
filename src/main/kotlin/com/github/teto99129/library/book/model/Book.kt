package com.github.teto99129.library.book.model

import com.github.teto99129.library.author.model.Author
import java.time.OffsetDateTime
import jakarta.validation.constraints.Min

data class Book(
	val bookID: Int,
	val title: String,
	@field:Min(1)
	val value: Int,
	val publicationStatus: PublicationStatus,
	val createdAt: OffsetDateTime,
	val authors: List<Author> = emptyList(),
)
