package com.componentprocessing.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.componentprocessing.model.ProcessResponse;

@Repository
@RepositoryRestResource(collectionResourceRel = "processResponse", path = "processResponse")
public interface ProcessResponseRepository extends JpaRepository<ProcessResponse, Long> {

}
