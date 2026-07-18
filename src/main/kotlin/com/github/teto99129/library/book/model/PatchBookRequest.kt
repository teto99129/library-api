package com.github.teto99129.library.book.model

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Size

data class PatchBookRequest(
	val title: String?,
	@field:Min(0)
	val value: Int?,
	@JsonProperty("publication_status")
	val publicationStatus: PublicationStatus?,
	@field:Size(min = 1)
	val authors: List<Int>?,
) {
	fun hasAnyUpdate(): Boolean = title != null || value != null || publicationStatus != null || authors != null
}
