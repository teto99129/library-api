package com.github.teto99129.library.author.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

data class AuthorResponse(
	@JsonProperty("author_id")
	val authorId: Int,
	val name: String,
	val birthday: LocalDate,
)
