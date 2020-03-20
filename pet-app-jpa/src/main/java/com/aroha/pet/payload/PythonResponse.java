package com.aroha.pet.payload;

import java.util.List;

public class PythonResponse {
	
	private String pythonprogram;
	private List pythonresult;
	private String pythonexception;
	private String pythonstatus;
	public String getPythonprogram() {
		return pythonprogram;
	}
	public void setPythonprogram(String pythonprogram) {
		this.pythonprogram = pythonprogram;
	}
	public List getPythonresult() {
		return pythonresult;
	}
	public void setPythonresult(List pythonresult) {
		this.pythonresult = pythonresult;
	}
	public String getPythonexception() {
		return pythonexception;
	}
	public void setPythonexception(String pythonexception) {
		this.pythonexception = pythonexception;
	}
	public String getPythonstatus() {
		return pythonstatus;
	}
	public void setPythonstatus(String pythonstatus) {
		this.pythonstatus = pythonstatus;
	}
	
	

}
