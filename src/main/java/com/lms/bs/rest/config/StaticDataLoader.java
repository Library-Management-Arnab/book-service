package com.lms.bs.rest.config;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.lms.bs.rest.model.entity.BookStatus;
import com.lms.bs.rest.model.entity.Genre;
import com.lms.bs.rest.model.entity.Language;
import com.lms.bs.rest.repository.BookStatusRepository;
import com.lms.bs.rest.repository.GenreRepository;
import com.lms.bs.rest.repository.LanguageRepository;
import com.lms.svc.common.config.BaseDataLoader;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class StaticDataLoader extends BaseDataLoader {
	private final Logger LOG = LoggerFactory.getLogger(StaticDataLoader.class);

	private BookStatusRepository bookStatusRepository;
	private GenreRepository genreRepository;
	private LanguageRepository languageRepository;

	private List<BookStatus> bookStatusList;
	private List<Genre> genreList;
	private List<Language> languageList;

	private List<String> validBookStatuses;
	private List<String> validGenres;
	private List<String> validLanguages;

	@PostConstruct
	public void loadData() {
		LOG.info("Populating  data for book service");

		bookStatusList = bookStatusRepository.findAll();
		validBookStatuses = bookStatusList.stream().map(BookStatus::getStatus).collect(Collectors.toList());

		genreList = genreRepository.findAll();
		validGenres = genreList.stream().map(Genre::getDescription).collect(Collectors.toList());

		languageList = languageRepository.findAll();
		validLanguages = languageList.stream().map(Language::getLangName).collect(Collectors.toList());
	}

	public String getBookStatusForClient(BookStatus bookStatusInput) {
		return getClientString(bookStatusList, bookStatusInput, BookStatus::getStatus);
	}

	public BookStatus getBookStatusFromClient(String clientStatus) {
		Predicate<BookStatus> bookStatusPredicate = bookStatus -> bookStatus.getStatus().equalsIgnoreCase(clientStatus);
		return returnOrThrow(bookStatusList, bookStatusPredicate, clientStatus, validBookStatuses, "Availability");
	}

	public Genre getGenreFromClient(String description) {
		Predicate<Genre> genrePredicate = genre -> genre.getDescription().equalsIgnoreCase(description);
		return returnOrThrow(genreList, genrePredicate, description, validGenres, "Genre");
	}

	public String getGenreForClient(Genre inputGenre) {
		return getClientString(genreList, inputGenre, Genre::getDescription);
	}

	public String getLanguageForClient(Language language) {
		return getClientString(languageList, language, Language::getLangName);
	}

	public Language getLanguageFromClient(String clientLanguage) {
		Predicate<Language> languagePredicate = language -> language.getLangName().equalsIgnoreCase(clientLanguage);
		return returnOrThrow(languageList, languagePredicate, clientLanguage, validLanguages, "Language");
	}
	
}
