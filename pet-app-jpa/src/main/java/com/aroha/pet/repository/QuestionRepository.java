package com.aroha.pet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aroha.pet.model.Question;

public interface QuestionRepository extends JpaRepository<Question,Integer> {

}
