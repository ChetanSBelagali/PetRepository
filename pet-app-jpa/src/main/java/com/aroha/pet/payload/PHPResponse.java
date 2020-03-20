package com.aroha.pet.payload;

import java.util.List;

public class PHPResponse {
	
	private String phpprogram;
	private List phpresult;
	private String phpexception;
	private String phpstatus;
	public String getPhpprogram() {
		return phpprogram;
	}
	public void setPhpprogram(String phpprogram) {
		this.phpprogram = phpprogram;
	}
	public List getPhpresult() {
		return phpresult;
	}
	public void setPhpresult(List phpresult) {
		this.phpresult = phpresult;
	}
	public String getPhpexception() {
		return phpexception;
	}
	public void setPhpexception(String phpexception) {
		this.phpexception = phpexception;
	}
	public String getPhpstatus() {
		return phpstatus;
	}
	public void setPhpstatus(String phpstatus) {
		this.phpstatus = phpstatus;
	}
	
	

}
