package com.lms.bs.rest.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "book_genre")
public class Genre implements Serializable {
	private static final long serialVersionUID = -7170013837990789127L;

	@Id
    @Column(name="genre_code", length = 4)
    private String genreCode;
    private String description;
}
