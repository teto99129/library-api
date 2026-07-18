package com.github.teto99129.library.author.model

import jakarta.validation.constraints.PastOrPresent
import java.time.LocalDate

data class PatchAuthorRequest(
	val name: String?,
	@field:PastOrPresent(message = "生年月日は過去または現在の日付を指定してください")
	val birthday: LocalDate?,
)
