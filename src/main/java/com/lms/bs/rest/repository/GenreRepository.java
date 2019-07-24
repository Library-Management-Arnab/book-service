package com.lms.bs.rest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lms.bs.rest.model.entity.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, String> {
	
	@Query(value = "select description from book_genre", nativeQuery = true)
	List<String> findAllDescriptions();
}
