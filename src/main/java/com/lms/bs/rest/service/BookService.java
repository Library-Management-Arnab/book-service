package com.lms.bs.rest.service;

import com.lms.bs.rest.exception.DuplicateBookException;
import com.lms.bs.rest.exception.NoSuchBookException;
import com.lms.bs.rest.model.UploadCsvRequest;
import com.lms.bs.rest.model.entity.Author;
import com.lms.bs.rest.model.entity.Book;
import com.lms.bs.rest.model.entity.BookStatus;
import com.lms.bs.rest.model.json.BookJson;
import com.lms.bs.rest.repository.AuthorRepository;
import com.lms.bs.rest.repository.BookRepository;
import com.lms.bs.rest.repository.BookStatusRepository;
import com.lms.bs.rest.service.util.CSVUtil;
import com.lms.bs.rest.transformer.BookTransformer;
import com.lms.svc.common.constants.ApplicationCommonConstants;
import com.lms.svc.common.exception.InvalidFieldValueException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookService {
	private BookRepository bookRepository;
	private AuthorRepository authorRepository;
	private BookStatusRepository bookStatusRepository;

	public List<BookJson> getAllBooks() {
		List<Book> allBooks = bookRepository.findAll();
		return BookTransformer.transformBookListToBookJsonList(allBooks);
	}

	public BookJson getBookById(String bookId) {
		return BookTransformer.transformBookToBookJson(searchBookById(bookId));
	}

	public BookJson addBook(BookJson json) {
		json.setAvailability("Available");
		Book addedBook = doAddBook(BookTransformer.transformBookJsonToBook(json));
		return BookTransformer.transformBookToBookJson(addedBook);
	}

	public BookJson updateBook(String bookId, BookJson bookJson) {
	    Book requestedBook = BookTransformer.transformBookJsonToBook(bookJson);

		Book existing = searchBookById(bookId);
        requestedBook.setStockAvailable(existing.getStockAvailable());
		BookStatus status = requestedBook.getStatus();
		Optional<BookStatus> currentStatus = bookStatusRepository.findByStatus(status.getStatus());

		if (currentStatus.isPresent()) {
            requestedBook.setStatus(currentStatus.get());
		} else {
			throwExceptionForInvalidStatus(requestedBook.getStatus());
		}
        Book updatedBook = bookRepository.save(requestedBook);
		return BookTransformer.transformBookToBookJson(updatedBook);
	}

	public void deleteBook(String bookId) {
		Book existing = searchBookById(bookId);
		setBookStatus(existing, ApplicationCommonConstants.BOOK_STATUS_CODE_DELETED);

		bookRepository.save(existing);
	}

	public BookJson increaseCount(String bookId, int count) {
		return updateCount(bookId, count);
	}

	public BookJson decreaseCount(String bookId, int count) {
		return updateCount(bookId, -count);
	}

	public List<BookJson> searchBooksByName(String bookName) {
        List<Book> foundBooks = bookRepository.findBookByBookName(bookName);
		return BookTransformer.transformBookListToBookJsonList(foundBooks);
	}

	public List<BookJson> uploadBooksFromCSV(UploadCsvRequest request) {
		try {
			List<Book> booksFromCsv = CSVUtil.readCsv(request.getCsvPath());
			return addMultipleBooks(booksFromCsv);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}
	
	private Book searchBookById(String bookId) {
		Optional<Book> searchResult = bookRepository.findById(bookId);
		if (searchResult.isPresent()) {
			return searchResult.get();
		}
		throw new NoSuchBookException();
	}
	
	private Book doAddBook(Book book) {
		prepareBookForAdd(book);
		return bookRepository.save(book);
	}

	private List<BookJson> addMultipleBooks(List<Book> booksFromCsv) {
		List<Book> toBeAdded = new ArrayList<>();
		booksFromCsv.forEach(book -> {
			try {
				prepareBookForAdd(book);
				toBeAdded.add(book);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		List<Book> savedBooks = bookRepository.saveAll(toBeAdded);
		return BookTransformer.transformBookListToBookJsonList(savedBooks);
	}
	
	private void prepareBookForAdd(Book book) {
		validateDuplicateAndUpdateAuthor(book);
		book.setStockDate(ApplicationCommonConstants.getCurrentDateAsString());
		int quantityToAdd = book.getStockAvailable() < 1 ? 1 : book.getStockAvailable();
		book.setStockAvailable(quantityToAdd);
		setBookStatus(book, ApplicationCommonConstants.BOOK_STATUS_CODE_AVAILABLE);
	}

	private void validateDuplicateAndUpdateAuthor(Book book) {
		Author foundAuthor = verifyExistingAuthor(book.getAuthor());
		if (foundAuthor != null) {
			book.setAuthor(foundAuthor);
			Optional<Book> searchResult = bookRepository.findBookByBookNameAndAuthor(book.getBookName(), foundAuthor);

			if (searchResult.isPresent())
                throw new DuplicateBookException(book.getBookName(), book.getAuthor().getAuthorName());
		}
	}

	private Author verifyExistingAuthor(Author author) {
		Optional<Author> authorSearch = authorRepository.findByAuthorName(author.getAuthorName());
		if (authorSearch.isPresent()) {
			return authorSearch.get();
		}
		return null;
	}

	private BookJson updateCount(String bookId, int countToUpdate) {
		Book existing = searchBookById(bookId);
		int currentStock = existing.getStockAvailable();
		int remainingStock = currentStock + countToUpdate;
		remainingStock = remainingStock < 1 ? 0 : remainingStock;

		existing.setStockAvailable(remainingStock);
        Book updatedBook = bookRepository.save(existing);
		return BookTransformer.transformBookToBookJson(updatedBook);
	}

	private void setBookStatus(Book book, String statusCode) {
		BookStatus status = new BookStatus();
		status.setStatusCode(statusCode);
		book.setStatus(status);
	}

	private void throwExceptionForInvalidStatus(BookStatus status) {
		List<String> validStatuses = bookStatusRepository.findAllStatus();
		throw new InvalidFieldValueException("Book Status", status.getStatus(), validStatuses);
	}
}
