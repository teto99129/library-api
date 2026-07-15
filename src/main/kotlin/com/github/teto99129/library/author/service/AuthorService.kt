package com.github.teto99129.library.author.service

import com.github.teto99129.library.author.model.Author
import com.github.teto99129.library.author.repository.AuthorRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class AuthorService(
	private val repository: AuthorRepository,
) {
	fun registerAuthor(
		name: String,
		birthday: LocalDate,
	): Author =
		this.repository.insertAuthor(
			name = name,
			birthday = birthday,
		)

	fun updateAuthor(
		authorId: Int,
		name: String?,
		birthday: LocalDate?,
	): Author =
		this.repository.updateAuthor(
			authorId = authorId,
			name = name,
			birthday = birthday,
		)
}
