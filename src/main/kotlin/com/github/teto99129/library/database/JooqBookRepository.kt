package com.github.teto99129.library.database

import com.github.teto99129.library.book.model.Book
import com.github.teto99129.library.book.model.BookAuthors
import com.github.teto99129.library.book.repository.BookRepository
import com.github.teto99129.library.jooq.tables.records.BooksRecord
import com.github.teto99129.library.jooq.tables.references.BOOKS
import com.github.teto99129.library.jooq.tables.references.BOOK_AUTHORS
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import java.time.OffsetDateTime

@Repository
class JooqBookRepository(private val dsl: DSLContext): BookRepository {
	override fun insertBook(title: String, value: Int, publicationStatus: Short): Book {
		val record: BooksRecord =  dsl.insertInto(BOOKS)
			.columns(BOOKS.TITLE, BOOKS.VALUE, BOOKS.PUBLICATION_STATUS, BOOKS.CREATED_AT)
			.values(title, value, publicationStatus, OffsetDateTime.now())
			.returning()
			.fetchOne() ?: throw IllegalStateException("Failed to insert book")

		return Book(
			bookID = record.bookId!!,
			title = record.title!!,
			value = record.value!!,
			publicationStatus = record.publicationStatus!!,
			createdAt = record.createdAt!!
		)
	}

	override fun insertBookAuthors(bookId: Int, authors: List<Int>): BookAuthors {
		if (authors.isEmpty()) {
			return BookAuthors(bookId, emptyList())
		}

		val queries = authors.map { authorId ->
			dsl.insertInto(BOOK_AUTHORS)
				.columns(BOOK_AUTHORS.BOOK_ID, BOOK_AUTHORS.AUTHOR_ID)
				.values(bookId, authorId)
		}
		dsl.batch(queries).execute()

		return BookAuthors(bookId, authors)
	}
}
