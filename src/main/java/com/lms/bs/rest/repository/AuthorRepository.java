package com.lms.bs.rest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lms.bs.rest.model.Author;

public interface AuthorRepository extends JpaRepository<Author, String> {
	public Optional<Author> findByAuthorName(String authorName);
	
}
