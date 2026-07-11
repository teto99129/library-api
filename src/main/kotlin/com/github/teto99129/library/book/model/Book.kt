package com.github.teto99129.library.book.model

import com.github.teto99129.library.author.model.Author
import java.time.OffsetDateTime

data class Book(
	val bookID: Int,
	val title: String,
	val value: Int,
	val publicationStatus: Short,
	val createdAt: OffsetDateTime,
	val authors: List<Author> = emptyList(),
)
