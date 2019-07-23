package com.lms.bs.rest.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "book_language")
public class Language implements Serializable {
	private static final long serialVersionUID = -8199472532771678435L;
	
	@Id
	@Column(name = "lang_code", length = 3)
	private String langCode;
	
	@Column(name = "lang_name", length = 30)
	private String langName;
}
