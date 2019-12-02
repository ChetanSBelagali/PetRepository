package com.aroha.pet.payload;

public class DomainTable {
	
	private String domainName;
	private String functionName;
	private String scenarioTitle;
	private String questionDesc;
	
	public DomainTable(String domainName, String functionName, String scenarioTitle, String questionDesc) {
		super();
		this.domainName = domainName;
		this.functionName = functionName;
		this.scenarioTitle = scenarioTitle;
		this.questionDesc = questionDesc;
	}
	public String getDomainName() {
		return domainName;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	public String getFunctionName() {
		return functionName;
	}
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	public String getScenarioTitle() {
		return scenarioTitle;
	}
	public void setScenarioTitle(String scenarioTitle) {
		this.scenarioTitle = scenarioTitle;
	}
	public String getQuestionDesc() {
		return questionDesc;
	}
	public void setQuestionDesc(String questionDesc) {
		this.questionDesc = questionDesc;
	}
	
}
