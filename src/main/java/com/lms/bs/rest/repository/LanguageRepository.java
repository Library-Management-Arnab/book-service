package com.lms.bs.rest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lms.bs.rest.model.entity.Language;

@Repository
public interface LanguageRepository extends JpaRepository<Language, String> {
	@Query(value = "select lang_name from book_language", nativeQuery = true)
	List<String> findAllLangNames();
}
