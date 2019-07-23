package com.lms.bs.rest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lms.bs.rest.model.entity.BookStatus;

@Repository
public interface BookStatusRepository extends JpaRepository<BookStatus, String> {
	public Optional<BookStatus> findByStatus(String status);

	@Query(value = "select book_status_description from book_status", nativeQuery = true)
	public List<String> findAllStatus();
}
