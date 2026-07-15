package com.github.teto99129.library.author.model

import java.time.LocalDate

data class Author(
	val authorId: Int,
	val name: String,
	val birthday: LocalDate,
)
