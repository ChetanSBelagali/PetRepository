package com.aroha.pet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.aroha.pet.model.Domain;

public interface DomainRepository extends JpaRepository<Domain, Integer> {
	
  		
}
