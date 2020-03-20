package com.aroha.pet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aroha.pet.model.PHPPojo;

@Repository
public interface PHPRepo extends JpaRepository<PHPPojo, Long> {

}
