package com.componentprocessing.dao;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.componentprocessing.model.UserRequests;

@Repository
public class UserRequestRepoImpl implements UserRequestRepo {

	@Autowired
	EntityManager entityManager;

	@Override
	@Transactional
	public UserRequests saveUserRequest(UserRequests userRequests) {
		return entityManager.merge(userRequests);

	}

}
