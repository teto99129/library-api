package com.github.teto99129.library.book.service

import com.github.teto99129.library.book.model.PostBookResponse
import com.github.teto99129.library.database.BookRepository
import com.github.teto99129.library.jooq.tables.records.BooksRecord
import org.springframework.stereotype.Service

@Service
class BookService(val repository: BookRepository) {

	fun registerBook(title: String, value: Int, publicationStatus: Short) {
		val record: BooksRecord = this.repository.insertBook(title, value, publicationStatus)!!
		val response = PostBookResponse(
			bookID = record.bookID!!,
			title = record.title!!,
			value = record.value!!,
			publicationStatus = record.publicationStatus!!,
			createdAt = record.createdAt!!
		)
		println(dto)
	}

}
