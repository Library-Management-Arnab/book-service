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

import com.lms.bs.rest.model.UploadCsvRequest;
import com.lms.bs.rest.model.json.BookJson;
import com.lms.bs.rest.service.BookService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/books")
@Api(value = "Book Service Rest Controller")
public class BookServiceRestController {
	private BookService bookService;

	public BookServiceRestController(BookService bookService) {
		this.bookService = bookService;
	}

	@ApiOperation(value = "Add new book to the library", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, response = ResponseEntity.class, httpMethod = "POST")
	@ApiResponses({ @ApiResponse(code = 200, message = "Successfully added book"),
			@ApiResponse(code = 400, message = "Invalid request"),
			@ApiResponse(code = 403, message = "User not authorized to perform this operation"),
			@ApiResponse(code = 409, message = "Duplicate book."),
			@ApiResponse(code = 500, message = "Generic error") })
	@PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Object> addBook(@RequestBody BookJson bookJson) {
		return new ResponseEntity<>(bookService.addBook(bookJson), HttpStatus.CREATED);
	}

	@ApiOperation(value = "Get all books", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, response = ResponseEntity.class, httpMethod = "GET")
	@ApiResponses({ @ApiResponse(code = 200, message = "Successfully retrieved all books"),
			@ApiResponse(code = 403, message = "User not authorized to perform this operation"),
			@ApiResponse(code = 500, message = "Generic error") })
	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Object> getAllBooks() {
		return new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.OK);
	}

	@ApiOperation(value = "Get book by BookID", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, response = ResponseEntity.class, httpMethod = "GET")
	@ApiResponses({ @ApiResponse(code = 200, message = "Successfully retrieved corresponding book"),
			@ApiResponse(code = 403, message = "User not authorized to perform this operation"),
			@ApiResponse(code = 404, message = "No book was found based on the selected criteria"),
			@ApiResponse(code = 500, message = "Generic error") })
	@GetMapping(value = "/{bookId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Object> getBookById(@PathVariable("bookId") String bookId) {
		return new ResponseEntity<>(bookService.getBookById(bookId), HttpStatus.OK);
	}

	@ApiOperation(value = "Update an existing book", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, response = ResponseEntity.class, httpMethod = "PUT")
	@ApiResponses({ @ApiResponse(code = 200, message = "Successfully updated book"),
			@ApiResponse(code = 403, message = "User not authorized to perform this operation"),
			@ApiResponse(code = 404, message = "No book was found based on the selected criteria"),
			@ApiResponse(code = 500, message = "Generic error") })
	@PutMapping(value = "/{bookId}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Object> updateBook(@PathVariable("bookId") String bookId, @RequestBody BookJson bookJson) {
		return new ResponseEntity<>(bookService.updateBook(bookId, bookJson), HttpStatus.OK);
	}

	@ApiOperation(value = "Delete an existing book", response = ResponseEntity.class, httpMethod = "DELETE")
	@ApiResponses({ @ApiResponse(code = 204, message = "Book deleted successfully"),
			@ApiResponse(code = 403, message = "User not authorized to perform this operation"),
			@ApiResponse(code = 404, message = "No book was found based on the selected criteria"),
			@ApiResponse(code = 500, message = "Generic error") })
	@DeleteMapping(value = "/{bookId}")
	public ResponseEntity<Object> deleteBook(@PathVariable("bookId") String bookId) {
		bookService.deleteBook(bookId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "Add books from CSV file", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, response = ResponseEntity.class, httpMethod = "POST")
	@ApiResponses({ @ApiResponse(code = 201, message = "Successfully added books"),
			@ApiResponse(code = 403, message = "User not authorized to perform this operation"),
			@ApiResponse(code = 500, message = "Generic error") })
	@PostMapping(value = "/upload", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Object> uploadBooksFromCSV(@RequestBody UploadCsvRequest request) {
		return new ResponseEntity<>(bookService.uploadBooksFromCSV(request), HttpStatus.CREATED);
	}

	@ApiOperation(value = "Increase stock of an existing book", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, response = ResponseEntity.class, httpMethod = "PUT")
	@ApiResponses({ @ApiResponse(code = 200, message = "Successfully updated stock"),
			@ApiResponse(code = 403, message = "User not authorized to perform this operation"),
			@ApiResponse(code = 404, message = "No book was found based on the selected criteria"),
			@ApiResponse(code = 500, message = "Generic error") })
	@PutMapping(value = "/{bookId}/inc/{count}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Object> increaseBookCount(@PathVariable("bookId") String bookId,
			@PathVariable("count") int count) {
		return new ResponseEntity<>(bookService.increaseCount(bookId, count), HttpStatus.OK);
	}

	@ApiOperation(value = "Decrease stock of an existing book", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, 
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE, response = ResponseEntity.class, httpMethod = "PUT")
	@ApiResponses({ @ApiResponse(code = 200, message = "Successfully updated stock"),
			@ApiResponse(code = 403, message = "User not authorized to perform this operation"),
			@ApiResponse(code = 404, message = "No book was found based on the selected criteria"),
			@ApiResponse(code = 500, message = "Generic error") })
	@PutMapping(value = "/{bookId}/dec/{count}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Object> decreaseBookCount(@PathVariable("bookId") String bookId,
			@PathVariable("count") int count) {
		return new ResponseEntity<>(bookService.decreaseCount(bookId, count), HttpStatus.OK);
	}
}
