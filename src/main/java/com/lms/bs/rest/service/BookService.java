package com.lms.bs.rest.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lms.bs.rest.exception.DuplicateBookException;
import com.lms.bs.rest.exception.NoSuchBookException;
import com.lms.bs.rest.model.Author;
import com.lms.bs.rest.model.Book;
import com.lms.bs.rest.model.BookStatus;
import com.lms.bs.rest.model.UploadCsvRequest;
import com.lms.bs.rest.model.json.BookJson;
import com.lms.bs.rest.repository.AuthorRepository;
import com.lms.bs.rest.repository.BookRepository;
import com.lms.bs.rest.repository.BookStatusRepository;
import com.lms.bs.rest.service.util.CSVUtil;
import com.lms.svc.common.constants.ApplicationCommonConstants;
import com.lms.svc.common.exception.InactiveUserException;
import com.lms.svc.common.exception.InsufficientPrivilageException;
import com.lms.svc.common.exception.InvalidFieldValueException;
import com.lms.svc.common.model.AuthenticatedUser;
import com.lms.svc.common.model.User;
import com.lms.svc.common.repository.UserServiceRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BookService {
	private BookRepository bookRepository;
	private UserServiceRepository userServiceRepository;
	private AuthorRepository authorRepository;
	private BookStatusRepository bookStatusRepository;

	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	public Book getBookById(String bookId) {
		Optional<Book> searchResult = bookRepository.findById(bookId);
		if (searchResult.isPresent()) {
			return searchResult.get();
		}
		throw new NoSuchBookException();
	}

	public Book addBook(BookJson json) {
		return addBook(json.getBook(), json.getUser());
	}

	public Book updateBook(String bookId, Book book) {
		Book existing = getBookById(bookId);
		book.setStockAvailable(existing.getStockAvailable());
		BookStatus status = book.getStatus();
		Optional<BookStatus> currentStatus = bookStatusRepository.findByStatus(status.getStatus());

		if (currentStatus.isPresent()) {
			book.setStatus(currentStatus.get());
		} else {
			throwExceptionForInvalidStatus(book.getStatus());
		}

		return bookRepository.save(book);
	}

	public void deleteBook(String bookId) {
		Book existing = getBookById(bookId);
		setBookStatus(existing, ApplicationCommonConstants.BOOK_STATUS_CODE_DELETED);

		bookRepository.save(existing);
	}

	public Book increaseCount(String bookId, int count) {
		return updateCount(bookId, count);
	}

	public Book decreaseCount(String bookId, int count) {
		return updateCount(bookId, -count);
	}

	public List<Book> searchBooksByName(String bookName) {
		return bookRepository.findBookByBookName(bookName);
	}

	public List<Book> uploadBooksFromCSV(UploadCsvRequest request) {
		try {
			List<Book> booksFromCsv = CSVUtil.readCsv(request.getCsvPath());
			return addMultipleBooks(booksFromCsv, request.getUser());
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	private Book addBook(Book book, User user) {
		AuthenticatedUser authenticatedUser = userServiceRepository.authenticate(user);
		if (!authenticatedUser.isActive()) {
			throw new InactiveUserException();
		}
		if (authenticatedUser.isAdmin()) {
			prepareBookForAdd(book);
			return bookRepository.save(book);
		}
		throw new InsufficientPrivilageException();
	}

	private List<Book> addMultipleBooks(List<Book> booksFromCsv, User user) {
		AuthenticatedUser authenticatedUser = userServiceRepository.authenticate(user);
		if (!authenticatedUser.isActive()) {
			throw new InactiveUserException();
		}

		if (authenticatedUser.isAdmin()) {
			List<Book> toBeAdded = new ArrayList<>();
			booksFromCsv.forEach(book -> {
				try {
					prepareBookForAdd(book);
					toBeAdded.add(book);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			return bookRepository.saveAll(toBeAdded);
		}
		throw new InsufficientPrivilageException();
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
			if (searchResult.isPresent()) {
				throw new DuplicateBookException(book.getBookName(), book.getAuthor().getAuthorName());
			}
		}
	}

	private Author verifyExistingAuthor(Author author) {
		Optional<Author> authorSearch = authorRepository.findByAuthorName(author.getAuthorName());
		if (authorSearch.isPresent()) {
			return authorSearch.get();
		}
		return null;
	}

	private Book updateCount(String bookId, int countToUpdate) {
		Book existing = getBookById(bookId);
		int currentStock = existing.getStockAvailable();
		int remainingStock = currentStock + countToUpdate;
		remainingStock = remainingStock < 1 ? 0 : remainingStock;

		existing.setStockAvailable(remainingStock);

		return bookRepository.save(existing);
	}

	private void setBookStatus(Book book, String statusCode) {
		BookStatus status = new BookStatus();
		status.setStatusCode(statusCode);
		book.setStatus(status);
	}

	private void throwExceptionForInvalidStatus(BookStatus status) {
		if (status == null) {
			throw new InvalidFieldValueException("Book Status");
		} else {
			List<String> validStatuses = bookStatusRepository.findAllStatus();
			throw new InvalidFieldValueException("Book Status", status.getStatus(), validStatuses);
		}
	}
}
