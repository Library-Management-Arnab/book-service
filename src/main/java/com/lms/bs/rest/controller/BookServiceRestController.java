package com.lms.bs.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lms.bs.rest.model.Book;
import com.lms.bs.rest.model.UploadCsvRequest;
import com.lms.bs.rest.model.json.BookJson;
import com.lms.bs.rest.service.BookService;

@RestController
@RequestMapping(value = "/books")
public class BookServiceRestController {
	private BookService bookService;

	public BookServiceRestController(BookService bookService) {
		this.bookService = bookService;
	}

	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Object> getAllBooks() {
		return new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.OK);
	}

	@PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Object> addBook(@RequestBody BookJson bookJson) {
		return new ResponseEntity<>(bookService.addBook(bookJson), HttpStatus.CREATED);
	}

	@GetMapping(value = "/{bookId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Object> getBookById(@PathVariable("bookId") String bookId) {
		return new ResponseEntity<>(bookService.getBookById(bookId), HttpStatus.OK);
	}

	@DeleteMapping(value = "/{bookId}")
	public ResponseEntity<Object> deleteBook(@PathVariable("bookId") String bookId) {
		bookService.deleteBook(bookId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PutMapping(value = "/{bookId}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Object> updateBook(@PathVariable("bookId") String bookId, @RequestBody Book book) {
		return new ResponseEntity<>(bookService.updateBook(bookId, book), HttpStatus.OK);
	}

	@PostMapping(value = "/upload", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Object> uploadBooksFromCSV(@RequestBody UploadCsvRequest request) {
		return new ResponseEntity<>(bookService.uploadBooksFromCSV(request), HttpStatus.CREATED);
	}
	
	@PostMapping(value = "/{bookId}/inc/{count}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Object> increaseBookCount(@PathVariable("bookId") String bookId, @PathVariable("count") int count) {
		return new ResponseEntity<>(bookService.increaseCount(bookId, count), HttpStatus.CREATED);
	}
	
	@PostMapping(value = "/{bookId}/dec/{count}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Object> decreaseBookCount(@PathVariable("bookId") String bookId, @PathVariable("count") int count) {
		return new ResponseEntity<>(bookService.decreaseCount(bookId, count), HttpStatus.CREATED);
	}
}
