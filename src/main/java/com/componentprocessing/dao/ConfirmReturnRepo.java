package com.componentprocessing.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.componentprocessing.model.ConfirmReturn;

@RepositoryRestResource(collectionResourceRel = "confirmReturn", path = "confirmReturn")
public interface ConfirmReturnRepo extends JpaRepository<ConfirmReturn, Long> {

}
