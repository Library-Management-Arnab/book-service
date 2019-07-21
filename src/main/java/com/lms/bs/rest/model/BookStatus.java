package com.lms.bs.rest.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "book_status")
public class BookStatus implements Serializable {
	private static final long serialVersionUID = 8510322941597761920L;
	@Id
	@Column(name = "book_status_code", length = 2)
	@JsonIgnore
	private String statusCode;

	@Column(name = "book_status_description", length = 30)
	private String status;

}
