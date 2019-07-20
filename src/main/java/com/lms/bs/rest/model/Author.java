package com.lms.bs.rest.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "author")
public class Author implements Serializable {
	private static final long serialVersionUID = -6276686600843230458L;

	public Author() {
		this.authorId = "AU" + System.currentTimeMillis();
	}

	@Id
	@Column(name = "author_id", length = 20)
	private String authorId;

	@Column(length = 100, nullable = false, unique = true)
	private String authorName;

	@Column(length = 30)
	private String dateOfBirth;

	@Column(length = 30)
	private String dateOfDeath;

	@Column(length = 10000)
	private String bio;

	@Column(length = 300)
	private String wikiUrl;

	@Column(length = 300)
	private String imageUrl;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "author")
	@JsonIgnore
	private List<Book> books;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Author other = (Author) obj;
		if (authorName == null) {
			if (other.authorName != null)
				return false;
		} else if (!authorName.equalsIgnoreCase(other.authorName))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authorName == null) ? 0 : authorName.toLowerCase().hashCode());
		return result;
	}

}
