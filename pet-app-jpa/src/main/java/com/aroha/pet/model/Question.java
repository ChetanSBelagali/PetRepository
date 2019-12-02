package com.aroha.pet.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Question implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@NotBlank
	@NotNull
	private String questionDesc;	
	@ManyToOne
	@JoinColumn(name="scenarioId")
	private Scenario scenario;
	@Lob
	private String answer;
	
	public int getId() {
		return id;
	}
	public String getQuestionDesc() {
		return questionDesc;
	}
	@JsonIgnore
	public Scenario getScenario() {
		return scenario;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setQuestionDesc(String questionDesc) {
		this.questionDesc = questionDesc;
	}
	@JsonIgnore
	public void setScenario(Scenario scenario) {
		this.scenario = scenario;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	@Override
	public String toString() {
		return "Question [id=" + id + ", questionDesc=" + questionDesc + ", scenario=" + scenario + ", answer=" + answer
				+ "]";
	}	
}
