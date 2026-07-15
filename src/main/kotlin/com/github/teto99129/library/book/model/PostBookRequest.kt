package com.github.teto99129.library.book.model

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotEmpty
import java.time.LocalDateTime

data class PostBookRequest(
	val title: String,
	@field:Min(1)
	val value: Int,
	@JsonProperty("publication_status")
	val publicationStatus: PublicationStatus,
	@field:NotEmpty
	val authors: List<Int>
)
