package com.aroha.pet.payload;

import com.aroha.pet.model.CPojo;
import com.aroha.pet.model.PHPPojo;

public class PHPPayload {
	
	private int questionId;
	private PHPPojo phppojo;
	public int getQuestionId() {
		return questionId;
	}
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	public PHPPojo getPhppojo() {
		return phppojo;
	}
	public void setPhppojo(PHPPojo phppojo) {
		this.phppojo = phppojo;
	}

}
