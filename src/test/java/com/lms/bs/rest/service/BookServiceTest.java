package com.lms.bs.rest.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.lms.bs.rest.config.StaticDataLoader;
import com.lms.bs.rest.exception.DuplicateBookException;
import com.lms.bs.rest.exception.NoSuchBookException;
import com.lms.bs.rest.model.UploadCsvRequest;
import com.lms.bs.rest.model.entity.Author;
import com.lms.bs.rest.model.entity.Book;
import com.lms.bs.rest.model.entity.BookStatus;
import com.lms.bs.rest.model.json.AuthorJson;
import com.lms.bs.rest.model.json.BookJson;
import com.lms.bs.rest.repository.AuthorRepository;
import com.lms.bs.rest.repository.BookRepository;
import com.lms.bs.rest.repository.BookStatusRepository;
import com.lms.bs.rest.transformer.BookTransformer;
import com.lms.svc.common.exception.InvalidFieldValueException;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {
	@Mock
	private BookRepository bookRepository;
	@Mock
	private AuthorRepository authorRepository;
	@Mock
	private BookStatusRepository bookStatusRepository;
	@Mock
	private BookTransformer bookTransformer;
	@Mock
	private StaticDataLoader staticDataLoader;
	
	@InjectMocks
	private BookService bookService;
	

	@Before
	public void setup() throws Exception {
	}

	@Test
	public void testAddBook() throws Exception {
		when(bookRepository.save(any(Book.class))).thenReturn(prepareSampleBook_1());

		BookJson savedBook = bookService.addBook(prepareSampleBookJson_1());

		assertNotNull(savedBook);
		assertEquals("B0001", savedBook.getIsbn());
		assertNotNull(savedBook.getAuthor());
		assertEquals("AU001", savedBook.getAuthor().getAuthorId());
	}

	@Test
	public void testAddBookExistingAuthor() throws Exception {
		Book sampleBook = prepareSampleBook_1();
		Author sampleAuthor = prepareSampleAuthor_2();
		when(authorRepository.findByAuthorName(anyString())).thenReturn(Optional.of(sampleAuthor));

		sampleBook.setAuthor(sampleAuthor);

		when(bookRepository.save(any(Book.class))).thenReturn(sampleBook);

		BookJson savedBook = bookService.addBook(prepareSampleBookJson_1());

		assertNotNull(savedBook);
		assertEquals("B0001", savedBook.getIsbn());
		assertNotNull(savedBook.getAuthor());
		assertEquals("AU002", savedBook.getAuthor().getAuthorId());
	}

	@Test(expected = DuplicateBookException.class)
	public void testAddBookDuplicateBook() throws Exception {
		Book sampleBook = prepareSampleBook_1();
		Author sampleAuthor = prepareSampleAuthor_2();
		when(authorRepository.findByAuthorName(anyString())).thenReturn(Optional.of(sampleAuthor));
		when(bookRepository.findBookByBookNameAndAuthor(anyString(), any(Author.class)))
				.thenReturn(Optional.of(sampleBook));

		bookService.addBook(prepareSampleBookJson_1());
	}

	@Test
	public void testGetAllBooks() throws Exception {
		when(bookRepository.findAll()).thenReturn(Arrays.asList(prepareSampleBook_1(), prepareSampleBook_2()));

		List<BookJson> allBooks = bookService.getAllBooks();

		assertNotNull(allBooks);
		assertEquals(2, allBooks.size());
	}

	@Test
	public void testGetBookById() throws Exception {
		when(bookRepository.findById(anyString())).thenReturn(Optional.of(prepareSampleBook_1()));

		BookJson foundBook = bookService.getBookById("B001");
		assertNotNull(foundBook);
		assertEquals("Romeo and Juliet", foundBook.getBookName());
		assertNotNull(foundBook.getAuthor());
		assertEquals("William Shakespeare", foundBook.getAuthor().getAuthorName());
	}

	@Test(expected = NoSuchBookException.class)
	public void testGetBookByIdNoSuchBook() throws Exception {
		when(bookRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
		bookService.getBookById("B001");
	}

	@Test
	public void testUpdateBook() throws Exception {
		when(bookRepository.findById(anyString())).thenReturn(Optional.of(prepareSampleBook_1()));
		when(bookStatusRepository.findByStatus(anyString())).thenReturn(Optional.of(sampleBookStatus()));
		when(bookRepository.save(any(Book.class))).thenReturn(prepareSampleBook_1());

		BookJson updatedBook = bookService.updateBook("B001", prepareSampleBookJson_1());
		assertNotNull(updatedBook);
		assertEquals("Romeo and Juliet", updatedBook.getBookName());
	}

	@Test(expected = InvalidFieldValueException.class)
	public void testUpdateBookInvalidStatus() throws Exception {
		when(bookRepository.findById(anyString())).thenReturn(Optional.of(prepareSampleBook_1()));
		when(bookStatusRepository.findByStatus(anyString())).thenReturn(Optional.empty());

		bookService.updateBook("B001", prepareSampleBookJson_1());
	}

	private BookStatus sampleBookStatus() {
		BookStatus status = new BookStatus();
		status.setStatusCode("A");
		status.setStatus("AVAILABLE");
		return status;
	}

	@Test
	public void testDeleteBook() throws Exception {
		Book sampleBook = prepareSampleBook_1();
		when(bookRepository.findById(anyString())).thenReturn(Optional.of(sampleBook));

		bookService.deleteBook("B001");

		assertEquals("D", sampleBook.getStatus().getStatusCode());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testUploadCSV() throws Exception {
		UploadCsvRequest request = new UploadCsvRequest();
		ClassLoader classLoader = this.getClass().getClassLoader();
		String filePath = classLoader.getResource("books.csv").getFile();
		request.setCsvPath(filePath);

		when(bookRepository.saveAll(any(Iterable.class)))
				.thenReturn(Arrays.asList(prepareSampleBook_1(), prepareSampleBook_2()));

		List<BookJson> savedBooks = bookService.uploadBooksFromCSV(request);
		assertNotNull(savedBooks);
		assertEquals(2, savedBooks.size());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testUploadCSVDuplicateBookException() throws Exception {
		UploadCsvRequest request = new UploadCsvRequest();
		ClassLoader classLoader = this.getClass().getClassLoader();
		String filePath = classLoader.getResource("books.csv").getFile();
		request.setCsvPath(filePath);

		when(authorRepository.findByAuthorName(anyString())).thenReturn(Optional.of(prepareSampleAuthor_1()));
		when(bookRepository.findBookByBookNameAndAuthor(anyString(), any(Author.class)))
				.thenReturn(Optional.of(prepareSampleBook_1()), Optional.empty());

		when(bookRepository.saveAll(any(Iterable.class))).thenReturn(Arrays.asList(prepareSampleBook_2()));

		List<BookJson> savedBooks = bookService.uploadBooksFromCSV(request);
		assertNotNull(savedBooks);
		assertEquals(1, savedBooks.size());
	}

	@Test(expected = RuntimeException.class)
	public void testUploadCSVParsingException() throws Exception {
		UploadCsvRequest request = new UploadCsvRequest();
		String filePath = "some_wrong_file_path";
		request.setCsvPath(filePath);

		bookService.uploadBooksFromCSV(request);
	}

	@Test
	public void testIncreaseCount() {
		when(bookRepository.findById(anyString())).thenReturn(Optional.of(prepareSampleBook_1()));

		Book mockedResponse = prepareSampleBook_1();
		mockedResponse.setStockAvailable(3);
		when(bookRepository.save(any(Book.class))).thenReturn(mockedResponse);

		BookJson response = bookService.increaseCount("B002", 2);
		assertNotNull(response);
		assertEquals(3, response.getStockAvailable());
	}

	@Test
	public void testDecreaseCount() {
		when(bookRepository.findById(anyString())).thenReturn(Optional.of(prepareSampleBook_1()));

		Book mockedResponse = prepareSampleBook_1();
		mockedResponse.setStockAvailable(0);
		when(bookRepository.save(any(Book.class))).thenReturn(mockedResponse);

		BookJson response = bookService.decreaseCount("B002", 2);
		assertNotNull(response);
		assertEquals(0, response.getStockAvailable());
	}

	@Test
	public void testSearchBooksByName() {
		when(bookRepository.findBookByBookName(anyString()))
				.thenReturn(Arrays.asList(prepareSampleBook_1(), prepareSampleBook_2()));

		List<BookJson> foundBooks = bookService.searchBooksByName("Romeo and Juliet");
		assertNotNull(foundBooks);
		assertEquals(2, foundBooks.size());
	}

	private BookJson prepareSampleBookJson_1() {
		BookJson bookJson = new BookJson();
		bookJson.setAuthor(prepareSampleAuthorJson_1());
		bookJson.setBookDescription("Romeo and Juliet");
		bookJson.setBookName("Romeo and Juliet");
		bookJson.setGenre("ROMMANCE");
		bookJson.setLanguage("ENGLISH");
		bookJson.setAvailability("AVAILABLE");
		return bookJson;
	}

	private AuthorJson prepareSampleAuthorJson_1() {
		AuthorJson authorJson = new AuthorJson();
		authorJson.setAuthorId("AU001");
		authorJson.setAuthorName("William Shakespeare");
		authorJson.setBio("The great author!!");
		authorJson.setDateOfBirth("1-Apr-1564 @ 00:00:00");
		authorJson.setDateOfDeath("23-Apr-1616 @ 00:00:00");

		return authorJson;
	}

	private Book prepareSampleBook_1() {
		Book book = new Book();
		book.setBookId("B0001");
		book.setAuthor(prepareSampleAuthor_1());
		book.setBookDescription("Romeo and Juliet");
		book.setBookName("Romeo and Juliet");
		
		book.setGenre(bookTransformer.getGenreFromClient("ROMMANCE"));
		book.setLanguage(bookTransformer.getLanguageFromClient("ENGLISH"));
		book.setStatus(bookTransformer.getBookStatusFromClient("AVAILABLE"));
		
		book.setStockAvailable(10);
		book.setStockDate("23-Jul-2019 @ 19:45:18");

		return book;
	}

	private Author prepareSampleAuthor_1() {
		Author author = new Author();
		author.setAuthorId("AU001");
		author.setAuthorName("William Shakespeare");
		author.setBio("The great author!!");
		author.setDateOfBirth("1-Apr-1564 @ 00:00:00");
		author.setDateOfDeath("23-Apr-1616 @ 00:00:00");
		return author;
	}

	@SuppressWarnings("unused")
	private BookJson prepareSampleBookJson_2() {
		BookJson bookJson = new BookJson();
		bookJson.setAuthor(prepareSampleAuthorJson_2());
		bookJson.setBookDescription("Romeo and Juliet");
		bookJson.setBookName("Romeo and Juliet");
		bookJson.setGenre("ROMMANCE");
		bookJson.setLanguage("ENGLISH");

		return bookJson;
	}

	private AuthorJson prepareSampleAuthorJson_2() {
		AuthorJson authorJson = new AuthorJson();
		authorJson.setAuthorId("AU002");
		authorJson.setAuthorName("William Shakespeare");
		authorJson.setBio("The great author!!");
		authorJson.setDateOfBirth("1-Apr-1564 @ 00:00:00");
		authorJson.setDateOfDeath("23-Apr-1616 @ 00:00:00");

		return authorJson;
	}

	private Book prepareSampleBook_2() {
		Book book = new Book();
		book.setBookId("B0002");
		book.setAuthor(prepareSampleAuthor_1());
		book.setBookDescription("Romeo and Juliet");
		book.setBookName("Romeo and Juliet");
		
		book.setGenre(bookTransformer.getGenreFromClient("ROMMANCE"));
		book.setLanguage(bookTransformer.getLanguageFromClient("ENGLISH"));
		book.setStatus(bookTransformer.getBookStatusFromClient("AVAILABLE"));
		
		book.setStockAvailable(10);
		book.setStockDate("23-Jul-2019 @ 19:45:18");

		return book;
	}

	private Author prepareSampleAuthor_2() {
		Author author = new Author();
		author.setAuthorId("AU002");
		author.setAuthorName("Rabindranath Tegore");
		author.setBio("The Nobel Prize winner poet!!");
		author.setDateOfBirth("7-May-1861 @ 00:00:00");
		author.setDateOfDeath("7-Aug-1941 @ 00:00:00");
		return author;
	}
}
