package com.lms.bs.rest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lms.bs.rest.model.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {

	@Query(value = "select * from book where book_status_code = 'A' and book_name = :bookName", nativeQuery = true)
	public List<Book> findBookByBookName(@Param("bookName") String bookName);

	public List<Book> findBookByAuthor(String author);

	@Query(value = "select * from book b inner join author on b.author_id=author.author_id where b.book_status_code='A' "
			+ "and author.author_name=:authorName having b.book_name=:bookName", nativeQuery = true)
	public Optional<Book> findBookByBookNameAndAuthor(@Param("bookName") String bookName, @Param("authorName") String authorName);

	@Query(value = "select * from book where book_status_code = 'A' and book_id = :bookId", nativeQuery = true)
	public Optional<Book> findById(@Param("bookId") String bookId);

	@Query(value = "select * from book where book_status_code = 'A'", nativeQuery = true)
	public List<Book> findAll();
}
