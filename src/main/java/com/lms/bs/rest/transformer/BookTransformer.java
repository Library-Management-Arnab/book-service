package com.lms.bs.rest.transformer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.lms.bs.rest.config.StaticDataLoader;
import com.lms.bs.rest.model.entity.Book;
import com.lms.bs.rest.model.entity.BookStatus;
import com.lms.bs.rest.model.entity.Genre;
import com.lms.bs.rest.model.entity.Language;
import com.lms.bs.rest.model.json.BookJson;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class BookTransformer {
	private StaticDataLoader staticDataLoader;
	private AuthorTransformer authorTransformer;
	
    public Book transformBookJsonToBook(BookJson bookJson) {
        Book book = new Book();
        book.setBookName(bookJson.getBookName());
        book.setBookDescription(bookJson.getBookDescription());
        book.setImageUrl(bookJson.getImageUrl());
        book.setWikiUrl(bookJson.getWikiUrl());

        book.setAuthor(authorTransformer.transformAuthorJsonToAuthor(bookJson.getAuthor()));
        
        book.setGenre(getGenreFromClient(bookJson.getGenre()));
        book.setStatus(getBookStatusFromClient(bookJson.getAvailability()));
        book.setLanguage(getLanguageFromClient(bookJson.getLanguage()));

        return book;
    }

    public BookJson transformBookToBookJson(Book book) {
        BookJson bookJson = new BookJson();
        bookJson.setIsbn(book.getBookId());
        bookJson.setBookName(book.getBookName());
        bookJson.setBookDescription(book.getBookDescription());
        bookJson.setImageUrl(book.getImageUrl());
        bookJson.setWikiUrl(book.getWikiUrl());
        bookJson.setStockAvailable(book.getStockAvailable());

        bookJson.setAuthor(authorTransformer.transformAuthorToAuthorJson(book.getAuthor()));
        
        bookJson.setGenre(getGenreForClient(book.getGenre()));
        bookJson.setAvailability(getBookStatusForClient(book.getStatus()));
        bookJson.setLanguage(getLanguageForClient(book.getLanguage()));

        return bookJson;
    }

    public List<BookJson> transformBookListToBookJsonList(List<Book> books) {
        List<BookJson> bookJsonList = new ArrayList<>();
        books.forEach(book -> bookJsonList.add(transformBookToBookJson(book)));
        return  bookJsonList;
    }

    public List<Book> transformBookJsonListToBookList(List<BookJson> bookJsonList) {
        List<Book> books = new ArrayList<>();
        bookJsonList.forEach(bookJson -> books.add(transformBookJsonToBook(bookJson)));
        return  books;
    }
    
    public String getBookStatusForClient(BookStatus bookStatusInput) {
		return staticDataLoader.getBookStatusForClient(bookStatusInput);
	}

	public BookStatus getBookStatusFromClient(String clientStatus) {
		return staticDataLoader.getBookStatusFromClient(clientStatus);
	}

	public Genre getGenreFromClient(String description) {
		return staticDataLoader.getGenreFromClient(description);
	}

	public String getGenreForClient(Genre inputGenre) {
		return staticDataLoader.getGenreForClient(inputGenre);
	}

	public String getLanguageForClient(Language language) {
		return staticDataLoader.getLanguageForClient(language);
	}

	public Language getLanguageFromClient(String clientLanguage) {
		return staticDataLoader.getLanguageFromClient(clientLanguage);
	}
}
