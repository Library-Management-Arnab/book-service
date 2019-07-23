package com.lms.bs.rest.model;

import java.io.Serializable;

import com.lms.svc.common.model.User;

import lombok.Data;

@Data
public class UploadCsvRequest implements Serializable {

	private static final long serialVersionUID = -3023556012422170068L;
	
	private String csvPath;
	//private User user;
}
