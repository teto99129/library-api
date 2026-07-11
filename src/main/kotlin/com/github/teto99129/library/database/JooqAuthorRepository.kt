package com.github.teto99129.library.database

import com.github.teto99129.library.author.model.Author
import com.github.teto99129.library.author.repository.AuthorRepository
import com.github.teto99129.library.jooq.tables.records.AuthorsRecord
import com.github.teto99129.library.jooq.tables.references.AUTHORS
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class JooqAuthorRepository(private val dsl: DSLContext): AuthorRepository {
	override fun insertAuthor(name: String, birthday: LocalDate): Author {
		val record: AuthorsRecord = dsl.insertInto(AUTHORS)
			.columns(AUTHORS.NAME, AUTHORS.BIRTHDAY)
			.values(name, birthday)
			.returning()
			.fetchOne() ?: throw IllegalStateException("Failed to insert author")

		return Author(
			authorId = record.authorId!!,
			name = record.name!!,
			birthday = record.birthday!!
		)
	}

	override fun findAuthorsByIds(ids: List<Int>): List<Author> {
		if (ids.isEmpty()) {
			return emptyList()
		}
		return dsl.selectFrom(AUTHORS)
			.where(AUTHORS.AUTHOR_ID.`in`(ids))
			.fetch { record ->
				Author(
					authorId = record.authorId!!,
					name = record.name!!,
					birthday = record.birthday!!
				)
			}
	}
}
