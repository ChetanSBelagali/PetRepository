package com.aroha.pet.payload;

public class FeedBackPayload {

    //private String scenario;
    private String sqlStr;
    private String exceptionStr;
    private String result;

    public String getSqlStr() {
        return sqlStr;
    }

    public void setSqlStr(String sqlStr) {
        this.sqlStr = sqlStr;
    }

    public String getExceptionStr() {
        return exceptionStr;
    }

    public void setExceptionStr(String exceptionStr) {
        this.exceptionStr = exceptionStr;
    }

//	  public String getScenario() {
//		  return scenario;
//		  } 
//	  public void setScenario(String scenario) { 
//		  this.scenario = scenario; 
//		  }
//	 
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

}
