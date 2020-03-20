package com.aroha.pet.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.springframework.data.annotation.CreatedDate;

@Entity
public class PHPPojo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long php_id;
	
	@CreatedDate
	@Column(insertable=true, updatable=false)
	private String createdAt;
	
	private long createdBy;
	
	@Lob
	private String phpstr;
	
	@Lob
	private String resultstr;
	
	private String scenario;
	
	private int questionId;

	public long getPhp_id() {
		return php_id;
	}

	public void setPhp_id(long php_id) {
		this.php_id = php_id;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}

	public String getPhpstr() {
		return phpstr;
	}

	public void setPhpstr(String phpstr) {
		this.phpstr = phpstr;
	}

	public String getResultstr() {
		return resultstr;
	}

	public void setResultstr(String resultstr) {
		this.resultstr = resultstr;
	}

	public String getScenario() {
		return scenario;
	}

	public void setScenario(String scenario) {
		this.scenario = scenario;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	@Override
	public String toString() {
		return "PHPPojo [php_id=" + php_id + ", createdAt=" + createdAt + ", createdBy=" + createdBy + ", phpstr="
				+ phpstr + ", resultstr=" + resultstr + ", scenario=" + scenario + ", questionId=" + questionId
				+ ", getPhp_id()=" + getPhp_id() + ", getCreatedAt()=" + getCreatedAt() + ", getCreatedBy()="
				+ getCreatedBy() + ", getPhpstr()=" + getPhpstr() + ", getResultstr()=" + getResultstr()
				+ ", getScenario()=" + getScenario() + ", getQuestionId()=" + getQuestionId() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	

}
