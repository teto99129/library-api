package com.github.teto99129.library.author.controller

import com.github.teto99129.library.author.model.PostAuthorRequest
import com.github.teto99129.library.author.service.AuthorService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthorController(private val _service: AuthorService) {
        @PostMapping("/author")
        fun registerAuthor(@RequestBody body: PostAuthorRequest) {
		this._service.registerAuthor(body.name, body.birthday)
        }
}

