package com.github.teto99129.library.book.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue

enum class PublicationStatus(
	@get:JsonValue
	val code: Short,
	val label: String
) {
	UNPUBLISHED(0, "未出版"),
	PUBLISHED(1, "出版済み");

	companion object {
		@JvmStatic
		@JsonCreator
		fun fromCode(code: Short): PublicationStatus {
			return entries.find { it.code == code }
				?: throw IllegalArgumentException("Unknown publication status code: $code")
		}
	}
}
