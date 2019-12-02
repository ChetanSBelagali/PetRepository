package com.aroha.pet.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.NaturalId;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Domain implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;	
	@NotBlank
	@NotNull
	private String domainName;
	@NotBlank
	@NotNull
	private String domainCode;
	@NotBlank
	@NotNull
	private String domainDesc;
	@NotBlank
	@NotNull
	private String domainWebsite;

	@OneToMany(mappedBy = "domain")
	private Set<Function> functions=new HashSet<Function>();


	@Override
	public String toString() {
		return "Domain [id=" + id + ", domainName=" + domainName + ", domainCode=" + domainCode + ", domainDesc="
				+ domainDesc + ", domainWebsite=" + domainWebsite + ", fun=" + functions + "]";
	}

	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getDomainName() {
		return domainName;
	}



	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}



	public String getDomainCode() {
		return domainCode;
	}



	public void setDomainCode(String domainCode) {
		this.domainCode = domainCode;
	}



	public String getDomainDesc() {
		return domainDesc;
	}



	public void setDomainDesc(String domainDesc) {
		this.domainDesc = domainDesc;
	}



	public String getDomainWebsite() {
		return domainWebsite;
	}



	public void setDomainWebsite(String domainWebsite) {
		this.domainWebsite = domainWebsite;
	}



	public Set<Function> getFunctions() {
		return functions;
	}



	public void setFunctions(Set<Function> functions) {
		this.functions = functions;
	}

}
