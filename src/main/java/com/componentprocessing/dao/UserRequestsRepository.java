package com.componentprocessing.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.componentprocessing.model.UserRequests;

@Repository
public interface UserRequestsRepository extends JpaRepository<UserRequests, Long> {
	List<UserRequests> findAllByName(String Name);
}
