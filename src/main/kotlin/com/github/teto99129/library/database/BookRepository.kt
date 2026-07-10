package com.github.teto99129.library.database

import com.github.teto99129.library.jooq.tables.records.BooksRecord
import com.github.teto99129.library.jooq.tables.references.BOOKS
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.time.OffsetDateTime

@Repository
class BookRepository(private val dsl: DSLContext) {
	fun insertBook(title: String, value: Int, publicationStatus: Short): BooksRecord? {
		return dsl.insertInto(BOOKS)
		.columns(BOOKS.TITLE, BOOKS.VALUE, BOOKS.PUBLICATION_STATUS, BOOKS.CREATED_AT)
		.values(title, value, publicationStatus, OffsetDateTime.now())
                .returning(BOOKS)
                .fetchOne()
	}

}
