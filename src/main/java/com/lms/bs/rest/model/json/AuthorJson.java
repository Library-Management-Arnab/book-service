package com.lms.bs.rest.model.json;

import lombok.Data;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Data
@ApiModel(description = "Author model for API request and response")
public class AuthorJson implements Serializable, Comparable<AuthorJson> {
	private static final long serialVersionUID = -302934094232424L;

	// This field will only be read from JSON
	@JsonProperty(access = Access.READ_ONLY)
	@ApiModelProperty(dataType = "String", notes = "Author Id will not be read from API input. It will display the value from the database.", example = "AU13456789012345", allowEmptyValue = false, readOnly = true)
	private String authorId;
	
	@ApiModelProperty(dataType = "String", notes = "Name of the author", example = "William Shakespeare", required = true, allowEmptyValue = false)
	private String authorName;
	
	@ApiModelProperty(dataType = "String", notes = "Date of birth of the author")
	private String dateOfBirth;
	
	@ApiModelProperty(dataType = "String", notes = "Date of demise of the author")
	private String dateOfDeath;
	
	@ApiModelProperty(dataType = "String", notes = "Short biography of the author")
	private String bio;
	
	@ApiModelProperty(dataType = "String", notes = "URL of the author's photograph")
	private String imageUrl;
	
	@ApiModelProperty(dataType = "String", notes = "Wikipedia link of the author's biography")
	private String wikiUrl;

	@Override
	public int compareTo(AuthorJson o) {
		if (authorName == o.authorName) {
			return 0;
		}
		if (o.authorName == null) {
			return 1;
		}
		return authorName.compareTo(o.authorName);
	}
}
