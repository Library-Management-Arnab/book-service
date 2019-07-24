package com.lms.bs.rest.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "Book model for API request and response")
public class BookJson {
	// This field will only be read from JSON
	@JsonProperty(access = Access.READ_ONLY)
	@ApiModelProperty(dataType = "String", notes = "ISBN number will not be read from API input. It will display the value from the database.", example = "B123456789012345", allowEmptyValue = false, readOnly = true)
	private String isbn;

	@ApiModelProperty(dataType = "String", notes = "Name of the book", example = "The Merchant of Venice", required = true, allowEmptyValue = false)
	private String bookName;
	
	@ApiModelProperty(dataType = "String", notes = "Short description of the book", example = "The Merchant of Venice is a novel by William Shakespeare", required = true, allowEmptyValue = false)
	private String bookDescription;
	
	@ApiModelProperty(dataType = "AuthorJson", notes = "Details of the Author", required = true, allowEmptyValue = false)
	private AuthorJson author;

	// This field will only be read from user
	@JsonProperty(access = Access.WRITE_ONLY)
	@ApiModelProperty(dataType = "Integer", notes = "Quantity of books to be added to the stock. Defaults to 1 if left blank.")
	private int quantity;

	// This field will only be read from response
	@JsonProperty(access = Access.READ_ONLY)
	@ApiModelProperty(dataType = "Integer", notes = "Available stock will not be read from API input. It will display the value from the database.")
	private int stockAvailable;

	@ApiModelProperty(dataType = "String", notes = "Denotes the availability status of a requested book", allowableValues = "Available, Deleted, Temporarily Unavailable")
	private String availability;
	
	@ApiModelProperty(dataType = "String", notes = "Denotes the language the book is written", allowableValues = "ENGLISH, BENGALI, HINDI")
	private String language;

	@ApiModelProperty(dataType = "String", notes = "Denotes the genre of the book.", allowableValues = "FANTASY, SCIENCE_FICTION, WESTERN, ROMANCE, THRILLER, MYSTERY, DETECTIVE_STORY, NATURE_AND_WILDLIFE, DRAMA")
	private String genre;
	
	@ApiModelProperty(dataType = "String", notes = "URL of the cover picture")
	private String imageUrl;
	
	@ApiModelProperty(dataType = "String", notes = "Wikipedia link of the book")
	private String wikiUrl;
}
