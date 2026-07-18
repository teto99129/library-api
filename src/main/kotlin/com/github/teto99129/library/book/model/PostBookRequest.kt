package com.github.teto99129.library.book.model

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotEmpty

data class PostBookRequest(
	val title: String,
	@field:Min(0)
	val value: Int,
	@JsonProperty("publication_status")
	val publicationStatus: PublicationStatus,
	@field:NotEmpty
	val authors: List<Int>,
)
