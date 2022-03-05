package com.componentprocessing.dao;

import org.springframework.stereotype.Repository;

import com.componentprocessing.model.UserRequests;

@Repository
public interface UserRequestRepo {
	UserRequests saveUserRequest(UserRequests userRequests);
}
