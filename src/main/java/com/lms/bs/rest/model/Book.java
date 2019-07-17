package com.lms.bs.rest.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class Book implements Serializable {
	private static final long serialVersionUID = -943929819823L;
	private String bookId;
	private String bookName;
	private String description;
	private String author;
	private String stockDate;
	private int stockAvailable;
}
