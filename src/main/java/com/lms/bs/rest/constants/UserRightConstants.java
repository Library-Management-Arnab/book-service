package com.lms.bs.rest.constants;

public enum UserRightConstants {
	ADMIN("A"),
	USER("U");
	
	String code;
	
	private UserRightConstants(String code) {
		this.code = code;
	}
	
	public static UserRightConstants getFromCode(String code) {
		for(UserRightConstants urc : UserRightConstants.values()) {
			if(urc.code.equals(code)) {
				return urc;
			}
		}
		return null;
	}
}
