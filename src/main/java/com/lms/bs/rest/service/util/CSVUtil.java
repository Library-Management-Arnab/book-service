package com.lms.bs.rest.service.util;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.stereotype.Component;

import com.lms.bs.rest.model.entity.Author;
import com.lms.bs.rest.model.entity.Book;
import com.lms.bs.rest.transformer.BookTransformer;

import lombok.AllArgsConstructor;
@Component
@AllArgsConstructor
public class CSVUtil {
	private BookTransformer bookTransformer;
	
	public List<Book> readCsv(String path) throws IOException {
		InputStream is = new FileInputStream(path);
		Reader reader = new FileReader(path);
		CSVFormat csvFormat = CSVFormat.DEFAULT
				.withFirstRecordAsHeader()
				.withHeader("BookName", "Author", "Description", "StockAvailable", "WikiUrl", "ImageUrl", "Genre", "Language");
		CSVParser csvParser = new CSVParser(reader , csvFormat);
		
		List<Book> books = new ArrayList<>();
		csvParser.forEach (csvRecord -> {
			Book book = new Book();
			
			book.setBookName(trim(csvRecord.get("BookName")));
			
			Author author = new Author();
			author.setAuthorName(trim(csvRecord.get("Author")));
			book.setAuthor(author);
			
			book.setBookDescription(trim(csvRecord.get("Description")));
			
			String cellValue = trim(csvRecord.get("StockAvailable"));
			int stockAvailable = cellValue == null || !cellValue.matches("\\d") ? 0 : Integer.parseInt(cellValue);
			book.setStockAvailable(stockAvailable);
			
			book.setWikiUrl(trim(csvRecord.get("WikiUrl")));
			book.setImageUrl(trim(csvRecord.get("ImageUrl")));

			book.setGenre(bookTransformer.getGenreFromClient(trim(csvRecord.get("Genre"))));
			book.setLanguage(bookTransformer.getLanguageFromClient(trim(csvRecord.get("Language"))));

			books.add(book);
		});
		csvParser.close();
		is.close();
		
		return books;
	}
	private static String trim(String str) {
		return str == null ? null : str.trim();
	}
}
