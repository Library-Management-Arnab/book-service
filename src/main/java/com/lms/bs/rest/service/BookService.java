package com.lms.bs.rest.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lms.bs.rest.constants.UserRightConstants;
import com.lms.bs.rest.exception.NoSuchBookException;
import com.lms.bs.rest.model.Book;
import com.lms.bs.rest.model.UserData;
import com.lms.bs.rest.repository.BookRepository;
import com.lms.bs.rest.repository.UserServiceRepository;
import com.lms.svc.common.constants.ApplicationCommonConstants;
import com.lms.svc.common.exception.InsufficientPrivilageException;
import com.lms.svc.common.exception.NotImplementedException;

@Service
public class BookService {
	private BookRepository bookRepository;
	private UserServiceRepository userServiceRepository;

	public BookService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

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

	public Book addBook(Book book, UserData user) {
		UserRightConstants right = userServiceRepository.authenticate(user);
		if (right.equals(UserRightConstants.ADMIN)) {
			book.setBookId("BK" + System.currentTimeMillis());
			if (book.getStockAvailable() < 1) {
				int stockAvailable = getAllBooks().size() + 1;
				book.setStockAvailable(stockAvailable);
			}
			if(book.getStockDate() == null) {
				book.setStockDate(ApplicationCommonConstants.getCurrentDateAsString());
			}
			return bookRepository.save(book);
		}
		throw new InsufficientPrivilageException();
	}

	public void deleteBook(String bookId) {
		throw new NotImplementedException();
	}

	public Book updateBook(String bookId, Book book) {
		throw new NotImplementedException();
	}

	public List<Book> uploadBooksFromCSV() {
		throw new NotImplementedException();
	}
}
