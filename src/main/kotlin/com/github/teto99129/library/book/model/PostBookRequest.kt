package com.github.teto99129.library.book.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class PostBookRequest(
	val title: String,
	val value: Int,
	@JsonProperty("publication_status")
	val publicationStatus: Short,
        val authors: List<Int>
)
