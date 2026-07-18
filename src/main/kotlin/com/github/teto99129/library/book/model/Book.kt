package com.github.teto99129.library.book.model

import com.github.teto99129.library.author.model.Author
import jakarta.validation.constraints.Min
import java.time.OffsetDateTime

data class Book(
	val bookId: Int,
	val title: String,
	@field:Min(0)
	val value: Int,
	val publicationStatus: PublicationStatus,
	val createdAt: OffsetDateTime,
	val authors: List<Author> = emptyList(),
)
