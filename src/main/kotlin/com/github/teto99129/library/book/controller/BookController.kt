package com.github.teto99129.library.book.controller

import com.github.teto99129.library.book.model.PostBookRequest
import com.github.teto99129.library.book.model.PostBookResponse
import com.github.teto99129.library.book.service.BookService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class BookController(private val _service: BookService) {

	@PostMapping("/book")
	fun registerBook(@RequestBody body: PostBookRequest) {
		this._service.registerBook(body.title, body.value, body.publicationStatus)

	}

}
