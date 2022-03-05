package com.componentprocessing.dao;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.componentprocessing.model.UserRequests;

@Repository
public class UserRequestRepoImpl implements UserRequestRepo {

	@Autowired
	EntityManager entityManager;

	@Override
	public UserRequests saveUserRequest(UserRequests userRequests) {
		return entityManager.merge(userRequests);

	}

}
