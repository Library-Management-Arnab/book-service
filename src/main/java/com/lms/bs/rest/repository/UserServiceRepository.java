package com.lms.bs.rest.repository;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.lms.bs.rest.constants.UserRightConstants;
import com.lms.bs.rest.model.UserData;
import com.lms.svc.common.exception.InvalidCredentialsException;

@Repository
public class UserServiceRepository {
	private static final String USER_SERVICE_BASE_URL = "http://localhost:8081/api/us";

	private RestTemplate restTemplate;

	public UserServiceRepository(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public UserRightConstants authenticate(UserData userData) {
		ResponseEntity<JSONObject> response = restTemplate.postForEntity(USER_SERVICE_BASE_URL + "/login", userData,
				JSONObject.class);
		HttpStatus statusCode = response.getStatusCode();

		if (statusCode.isError()) {
			throw new InvalidCredentialsException();
		}
		JSONObject body = response.getBody();
		
		try {
			JSONObject userRight = body.getJSONObject("userRight");
			String code = userRight.getString("userRightCode");
			UserRightConstants urc = UserRightConstants.getFromCode(code);
			return urc;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		throw new InvalidCredentialsException();
	}
}
