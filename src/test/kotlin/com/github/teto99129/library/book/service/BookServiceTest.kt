package com.github.teto99129.library.book.service

import com.github.teto99129.library.book.model.Book
import com.github.teto99129.library.book.model.PatchBookRequest
import com.github.teto99129.library.book.model.PublicationStatus
import com.github.teto99129.library.book.repository.BookRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import java.time.OffsetDateTime

class BookServiceTest : DescribeSpec() {
	private val repository = mock(BookRepository::class.java)
	private val service = BookService(repository)

	init {
		describe("updateBook") {
			it("正常 - 非公開(UNPUBLISHED)から公開(PUBLISHED)へのステータス変更ができること") {
				val bookId = 1
				val currentBook =
					Book(
						bookID = bookId,
						title = "テスト本",
						value = 1000,
						publicationStatus = PublicationStatus.UNPUBLISHED,
						createdAt = OffsetDateTime.now(),
						authors = emptyList(),
					)
				val request =
					PatchBookRequest(
						title = null,
						value = null,
						publicationStatus = PublicationStatus.PUBLISHED,
						authors = null,
					)
				val expectedBook =
					Book(
						bookID = bookId,
						title = "テスト本",
						value = 1000,
						publicationStatus = PublicationStatus.PUBLISHED,
						createdAt = currentBook.createdAt,
						authors = emptyList(),
					)

				`when`(repository.getBookById(bookId)).thenReturn(currentBook)
				`when`(repository.updateBook(bookId, null, null, PublicationStatus.PUBLISHED, null)).thenReturn(expectedBook)

				val result = service.updateBook(bookId, request)

				result.publicationStatus shouldBe PublicationStatus.PUBLISHED
			}

			it("正常 - 公開済みの本についてステータスを変更せずに他のフィールド（タイトル）を更新できること") {
				val bookId = 1
				val currentBook =
					Book(
						bookID = bookId,
						title = "旧タイトル",
						value = 1000,
						publicationStatus = PublicationStatus.PUBLISHED,
						createdAt = OffsetDateTime.now(),
						authors = emptyList(),
					)
				val request =
					PatchBookRequest(
						title = "新タイトル",
						value = null,
						publicationStatus = null,
						authors = null,
					)
				val expectedBook =
					Book(
						bookID = bookId,
						title = "新タイトル",
						value = 1000,
						publicationStatus = PublicationStatus.PUBLISHED,
						createdAt = currentBook.createdAt,
						authors = emptyList(),
					)

				`when`(repository.getBookById(bookId)).thenReturn(currentBook)
				`when`(repository.updateBook(bookId, "新タイトル", null, null, null)).thenReturn(expectedBook)

				val result = service.updateBook(bookId, request)

				result.title shouldBe "新タイトル"
				result.publicationStatus shouldBe PublicationStatus.PUBLISHED
			}

			it("異常 - すでに公開済み(PUBLISHED)の本を非公開(UNPUBLISHED)に戻そうとすると400エラー(ResponseStatusException)が発生すること") {
				val bookId = 1
				val currentBook =
					Book(
						bookID = bookId,
						title = "公開済みの本",
						value = 1500,
						publicationStatus = PublicationStatus.PUBLISHED,
						createdAt = OffsetDateTime.now(),
						authors = emptyList(),
					)
				// PUBLISHED -> UNPUBLISHED への変更リクエスト
				val request =
					PatchBookRequest(
						title = null,
						value = null,
						publicationStatus = PublicationStatus.UNPUBLISHED,
						authors = null,
					)

				`when`(repository.getBookById(bookId)).thenReturn(currentBook)

				val exception =
					shouldThrow<ResponseStatusException> {
						service.updateBook(bookId, request)
					}

				exception.statusCode shouldBe HttpStatus.BAD_REQUEST
				exception.reason shouldBe "Cannot revert a published book to unpublished"
			}

			it("異常 - 存在しないIDを指定して更新しようとした場合は404エラー(ResponseStatusException)が発生すること") {
				val bookId = 999
				val request =
					PatchBookRequest(
						title = "新タイトル",
						value = null,
						publicationStatus = null,
						authors = null,
					)

				`when`(repository.getBookById(bookId)).thenReturn(null)

				val exception =
					shouldThrow<ResponseStatusException> {
						service.updateBook(bookId, request)
					}

				exception.statusCode shouldBe HttpStatus.NOT_FOUND
				exception.reason shouldBe "Book not found"
			}
		}
	}
}
