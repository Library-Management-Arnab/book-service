package com.lms.bs.rest.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "book")
public class Book implements Serializable {
	public Book() {
		this.bookId = "BK" + System.currentTimeMillis();
	}

	private static final long serialVersionUID = -943929819823L;
	@Id
	@Column(name = "book_id", length = 20)
	private String bookId;

	@Column(length = 150, nullable = false)
	private String bookName;

	@Column(length = 3000, nullable = false)
	private String description;

	@Column(length = 30, nullable = false)
	private String stockDate;

	@Column(length = 4, nullable = false)
	private int stockAvailable;

	@Column(length = 300)
	private String wikiUrl;

	@Column(length = 300)
	private String imageUrl;

	@Column(length = 500)
	private String genere;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "author_id")
	private Author author;
	
	@OneToOne
	@JoinColumn(name = "lang_code", referencedColumnName = "lang_code")
	private Language language;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (bookName == null) {
			if (other.bookName != null)
				return false;
		} else if (!bookName.equalsIgnoreCase(other.bookName))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((bookName == null) ? 0 : bookName.toLowerCase().hashCode());
		return result;
	}

}
