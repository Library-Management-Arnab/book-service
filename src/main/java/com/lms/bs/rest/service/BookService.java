package com.lms.bs.rest.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lms.bs.rest.exception.NoSuchBookException;
import com.lms.bs.rest.model.Author;
import com.lms.bs.rest.model.Book;
import com.lms.bs.rest.repository.AuthorRepository;
import com.lms.bs.rest.repository.BookRepository;
import com.lms.svc.common.constants.ApplicationCommonConstants;
import com.lms.svc.common.exception.InactiveUserException;
import com.lms.svc.common.exception.InsufficientPrivilageException;
import com.lms.svc.common.exception.NotImplementedException;
import com.lms.svc.common.model.LoginResponse;
import com.lms.svc.common.model.UserData;
import com.lms.svc.common.repository.UserServiceRepository;

@Service
public class BookService {
	private BookRepository bookRepository;
	private UserServiceRepository userServiceRepository;
	private AuthorRepository authorRepository;

	public BookService(BookRepository bookRepository, UserServiceRepository userServiceRepository,
			AuthorRepository authorRepository) {
		this.bookRepository = bookRepository;
		this.userServiceRepository = userServiceRepository;
		this.authorRepository = authorRepository;
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
		LoginResponse loginResponse = userServiceRepository.authenticate(user);

		if (!loginResponse.isActive()) {
			throw new InactiveUserException();
		}

		if (loginResponse.isAdmin()) {
			Book existingBook = verifyBookStoreForExistingBook(book);
			if (existingBook == null) {
				if (book.getStockAvailable() < 1) {
					book.setStockAvailable(1);
				}
			} else {
				int stockToAdd = book.getStockAvailable();
				stockToAdd = stockToAdd < 1 ? 1 : stockToAdd;
				book = existingBook;
				book.setStockAvailable(book.getStockAvailable() + stockToAdd);
			}
			if (book.getStockDate() == null) {
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

	public List<Book> searchBooksByName(String bookName) {
		return bookRepository.findBookByBookName(bookName);
	}

	private Book verifyBookStoreForExistingBook(Book book) {
		Author foundAuthor = verifyExistingAuthor(book.getAuthor());
		if (foundAuthor != null) {
			Optional<Book> searchResult = bookRepository.findBookByBookNameAndAuthor(book.getBookName(), foundAuthor);
			if (searchResult.isPresent()) {
				return searchResult.get();
			}
			book.setAuthor(foundAuthor);
			return book;
		}
		return null;
	}
	private Author verifyExistingAuthor(Author author) {
		Optional<Author> authorSearch = authorRepository.findByAuthorName(author.getAuthorName());
		if (authorSearch.isPresent()) {
			return authorSearch.get();
		}
		return null;
	}
}
