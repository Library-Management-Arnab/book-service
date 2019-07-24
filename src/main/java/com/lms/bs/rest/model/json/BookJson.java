package com.lms.bs.rest.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import lombok.Data;

@Data
public class BookJson {
	// This field will only be read from JSON
	@JsonProperty(access = Access.READ_ONLY)
	private String isbn;

	private String bookName;
	private String bookDescription;
	private AuthorJson author;
	
	// This field will only be read from user
	@JsonProperty(access = Access.WRITE_ONLY)
	private int quantity;

	// This field will only be read from response
	@JsonProperty(access = Access.READ_ONLY)
	private int stockAvailable;

	private String availability;

	private String language;
	private String imageUrl;
	private String wikiUrl;
	private String genre;
}
