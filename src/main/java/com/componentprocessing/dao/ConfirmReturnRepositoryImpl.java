package com.componentprocessing.dao;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.componentprocessing.model.ConfirmReturn;

@Repository
public class ConfirmReturnRepositoryImpl implements ConfirmReturnRepository {

	@Autowired
	EntityManager entityManager;

	@Override
	@Transactional
	public ConfirmReturn saveReturnConfirmation(ConfirmReturn confirmReturn) {
		return entityManager.merge(confirmReturn);

	}

}
