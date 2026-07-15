package com.github.teto99129.library.book.service

import com.github.teto99129.library.book.model.Book
import com.github.teto99129.library.book.model.BookAuthors
import com.github.teto99129.library.book.model.PatchBookRequest
import com.github.teto99129.library.book.model.PublicationStatus
import com.github.teto99129.library.book.repository.BookRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException

@Service
class BookService(
	private val repository: BookRepository
) {

	@Transactional
	fun registerBook(title: String, value: Int, authors: List<Int>, publicationStatus: PublicationStatus): Book {
		return this.repository.insertBook(
			title = title,
			value = value,
			publicationStatus = publicationStatus,
			authors = authors
		)
	}

	@Transactional
	fun updateBook(bookId: Int, request: PatchBookRequest): Book {
		val currentBook = this.repository.getBookById(bookId)
			?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found")

		if (request.publicationStatus != null) {
			if (currentBook.publicationStatus == PublicationStatus.PUBLISHED &&
				request.publicationStatus == PublicationStatus.UNPUBLISHED
			) {
				throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot revert a published book to unpublished")
			}
		}

		return this.repository.updateBook(
			bookId = bookId,
			title = request.title,
			value = request.value,
			publicationStatus = request.publicationStatus,
			authors = request.authors
		)
	}

	fun registerBookAuthors(bookId: Int, authors: List<Int>): BookAuthors {
		return this.repository.insertBookAuthors(
			bookId = bookId,
			authors = authors
		)
	}

	fun getBook(authId: Int?, authName: String?): List<Book> {
		return this.repository.getBook(
			authId = authId,
			authName = authName,
		)
	}

}
