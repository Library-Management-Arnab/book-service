package com.lms.bs.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lms.bs.rest.model.Book;

public interface BookRepository extends JpaRepository<Book, String> {

}
