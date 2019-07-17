package com.lms.bs.rest.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserData implements Serializable {
	private static final long serialVersionUID = 2762927081237537400L;
	
	private String userName;
	private String password;
	private String userRight;

}
