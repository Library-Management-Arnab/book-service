package com.lms.bs.rest.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "book")
public class Book implements Serializable {
	private static final long serialVersionUID = -943929819823L;
	@Id
	@Column(length = 10)
	private String bookId;
	@Column(length = 30, nullable = false)
	private String bookName;
	@Column(length = 300, nullable = false)
	private String description;
	@Column(length = 30, nullable = false)
	private String author;
	@Column(length = 30, nullable = false)
	private String stockDate;
	@Column(length = 4, nullable = false)
	private int stockAvailable;
}
