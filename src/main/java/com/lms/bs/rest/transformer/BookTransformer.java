package com.lms.bs.rest.transformer;

import java.util.ArrayList;
import java.util.List;

import com.lms.bs.rest.model.entity.Book;
import com.lms.bs.rest.model.json.BookJson;

public final class BookTransformer {

    public static Book transformBookJsonToBook(BookJson bookJson) {
        Book book = new Book();
        book.setBookName(bookJson.getBookName());
        book.setBookDescription(bookJson.getBookDescription());
        book.setImageUrl(bookJson.getImageUrl());
        book.setWikiUrl(bookJson.getWikiUrl());

        book.setAuthor(AuthorTransformer.transformAuthorJsonToAuthor(bookJson.getAuthor()));
        book.setGenre(GenreTransformer.getGenre(bookJson.getGenre()));
        book.setStatus(BookStatusTransformer.getBookStatusFromClient(bookJson.getAvailability()));
        book.setLanguage(LanguageTransformer.getLanguageFromClientLanguage(bookJson.getLanguage()));

        return book;
    }

    public static BookJson transformBookToBookJson(Book book) {
        BookJson bookJson = new BookJson();
        bookJson.setIsbn(book.getBookId());
        bookJson.setBookName(book.getBookName());
        bookJson.setBookDescription(book.getBookDescription());
        bookJson.setImageUrl(book.getImageUrl());
        bookJson.setWikiUrl(book.getWikiUrl());
        bookJson.setStockAvailable(book.getStockAvailable());

        bookJson.setAuthor(AuthorTransformer.transformAuthorToAuthorJson(book.getAuthor()));
        bookJson.setGenre(GenreTransformer.getGenreDescription(book.getGenre()));
        bookJson.setAvailability(BookStatusTransformer.getBookStatusForClient(book.getStatus()));
        bookJson.setLanguage(LanguageTransformer.getClientLanguage(book.getLanguage()));

        return bookJson;
    }

    public static List<BookJson> transformBookListToBookJsonList(List<Book> books) {
        List<BookJson> bookJsonList = new ArrayList<>();
        books.forEach(book -> bookJsonList.add(transformBookToBookJson(book)));
        return  bookJsonList;
    }

    public static List<Book> transformBookJsonListToBookList(List<BookJson> bookJsonList) {
        List<Book> books = new ArrayList<>();
        bookJsonList.forEach(bookJson -> books.add(transformBookJsonToBook(bookJson)));
        return  books;
    }
}
