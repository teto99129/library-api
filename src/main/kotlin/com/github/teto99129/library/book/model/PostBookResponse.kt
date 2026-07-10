package com.github.teto99129.library.book.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime
import java.time.OffsetDateTime

data class PostBookResponse(
	val bookID: Int,
	val title: String,
	val value: Int,
	@JsonProperty("publication_status")
	val publicationStatus: Short,
	@JsonProperty("created_at")
	val createdAt: OffsetDateTime
)
