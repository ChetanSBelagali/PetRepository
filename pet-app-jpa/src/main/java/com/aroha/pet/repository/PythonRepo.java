package com.aroha.pet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aroha.pet.model.PythonPojo;

@Repository
public interface PythonRepo extends JpaRepository<PythonPojo, Long> {

}
