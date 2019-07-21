package com.lms.bs.rest.model.json;

import com.lms.bs.rest.model.Book;
import com.lms.svc.common.model.User;

import lombok.Data;

@Data
public class BookJson {
	private Book book;
	private User user;
}
