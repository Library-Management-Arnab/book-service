package com.lms.bs.rest.service.util;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import com.lms.bs.rest.model.Author;
import com.lms.bs.rest.model.Book;
import com.lms.bs.rest.model.Language;

public class CSVUtil {
	
	public static List<Book> readCsv(String path) throws IOException {
		InputStream is = new FileInputStream(path);
		Reader reader = new FileReader(path);
		CSVFormat csvFormat = CSVFormat.DEFAULT
				.withFirstRecordAsHeader()
				.withHeader("BookName", "Author", "Description", "StockAvailable", "WikiUrl", "ImageUrl", "Genere", "Language");
		CSVParser csvParser = new CSVParser(reader , csvFormat);
		
		List<Book> books = new ArrayList<>();
		csvParser.forEach (csvRecord -> {
			Book book = new Book();
			
			book.setBookName(trim(csvRecord.get("BookName")));
			
			Author author = new Author();
			author.setAuthorName(trim(csvRecord.get("Author")));
			book.setAuthor(author);
			
			book.setDescription(trim(csvRecord.get("Description")));
			
			String cellValue = trim(csvRecord.get("StockAvailable"));
			int stockAvailable = cellValue == null || !cellValue.matches("\\d") ? 0 : Integer.parseInt(cellValue);
			book.setStockAvailable(stockAvailable);
			
			book.setWikiUrl(trim(csvRecord.get("WikiUrl")));
			book.setImageUrl(trim(csvRecord.get("ImageUrl")));
			book.setGenere(trim(csvRecord.get("Genere")));
			
			Language language = new Language();
			language.setLangCode(trim(csvRecord.get("Language")));
			book.setLanguage(language );
			
			books.add(book);
		});
		csvParser.close();
		is.close();
		
		return books;
	}
	private static String trim(String str) {
		return str == null ? null : str.trim();
	}
	public static void main(String[] args) throws Exception {
		String path = "C:\\Users\\Priyanka\\Desktop\\books.csv";
		System.out.println(String.join(System.lineSeparator(), readCsv(path).stream().map(book -> book.toString()).collect(Collectors.toList())));
	}
}
