package com.github.teto99129.library

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LibraryApplication

@Suppress("SpreadOperator")
fun main(args: Array<String>) {
	runApplication<LibraryApplication>(*args)
}
