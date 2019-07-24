package com.lms.bs.rest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lms.bs.rest.model.entity.Author;
import com.lms.bs.rest.model.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
	
	public List<Book> findBookByBookName(String bookName);
	public List<Book> findBookByAuthor(String author);
	public Optional<Book> findBookByBookNameAndAuthor(String bookName, Author author);
	
}
