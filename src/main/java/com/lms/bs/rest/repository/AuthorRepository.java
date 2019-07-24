package com.lms.bs.rest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lms.bs.rest.model.entity.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, String> {
	public Optional<Author> findByAuthorName(String authorName);

}
