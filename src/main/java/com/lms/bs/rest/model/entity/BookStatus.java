package com.lms.bs.rest.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "book_status")
public class BookStatus implements Serializable {
	private static final long serialVersionUID = 8510322941597761920L;
	@Id
	@Column(name = "book_status_code", length = 2)
	private String statusCode;

	@Column(name = "book_status_description", length = 30)
	private String status;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookStatus other = (BookStatus) obj;
		if (statusCode == null) {
			if (other.statusCode != null)
				return false;
		} else if (!statusCode.equals(other.statusCode))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((statusCode == null) ? 0 : statusCode.hashCode());
		return result;
	}

}
