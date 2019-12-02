package com.aroha.pet.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.aroha.pet.model.JavaPojo;
import com.aroha.pet.model.QueryInfo;
import com.aroha.pet.payload.JavaPayload;
import com.aroha.pet.payload.JavaResponse;
import com.aroha.pet.repository.JavaDataRepo;
import com.aroha.pet.security.UserPrincipal;

@Service
public class JavaService {

	@Autowired
	JavaDataRepo javadatarepo;
    StringBuffer sb=null;
	StringBuffer sb1=null;
	public JavaService() {
		super();
	}

	public JavaResponse executeJava(final UserPrincipal currentUser,JavaPayload payload) throws IOException
	{
		DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
		Date dateobj = new Date();
		String currTimeAndDate=df.format(dateobj);
		System.out.println(currTimeAndDate);
		
        System.out.println("Read Specific Enviornment Variable");
        String my_path= System.getenv("My_FilePath");
        System.out.println("MY_FilePath Value:- " + my_path);
		
		int n=20;
		String random=getAlphaNumericString(n);
		
		int qId=payload.getQuestionId();
		sb1=new StringBuffer();
		String text=payload.getJavapojo().getJavastr();
		System.out.println("Text is: "+text);
		
		sb=new StringBuffer(text);
		int endIdx=sb.indexOf("{");
		int startIdx=sb.indexOf("public");
		sb.replace(startIdx,endIdx, "class "+random);
		
		String name="/"+random+".java";
		String name1="/  "+random;
		//String dirName = "d:/Java";
		String dirName = my_path;
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(dirName+name));
		writer.write(sb.toString());
		writer.close();
		writer = null;
		
		String result="javac "+dirName+name;
		String result1="java -cp "+dirName+name1;
		
		JavaResponse javaresponse=new JavaResponse();
		JavaPojo javapojo=new JavaPojo();

		try {
			//sb=new StringBuffer();
			runProcess(result);
			System.out.println("After java compilation");
			System.out.println("****************************************************************");

			if(!(sb1.toString().contains("error"))) {
			runProcess(result1);}
			//runProcess("notepad.exe");
			System.out.println("After java execution");
			
			//int numMinutes =2;
			//deleteFile(numMinutes);

			System.out.println("****************************************************************");
			JSONArray jsona = null;
			if(!(sb1.toString().contains("error") || sb1.toString().contains("Exception"))){

				javapojo.setJavastr(text);
				javaresponse.setJava(text);
				jsona=getResultForJava(sb1);
				javaresponse.setJavaresult(getJsonArrayAsList(jsona));
				javapojo.setResultstr(jsona.toString());
				javaresponse.setJavastatus("SUCCESS");
				javapojo.setQuestionId(qId);
				javapojo.setScenario(payload.getJavapojo().getScenario());
				javapojo.setCreatedAt(currTimeAndDate);
				javadatarepo.save(javapojo);
			}
			else if(sb1.toString().contains("error")) {
				javapojo.setScenario(payload.getJavapojo().getScenario());
				javapojo.setJavastr(text);
				jsona=getResultForJava(sb1);
				javaresponse.setJava(text);
				javaresponse.setJavaresult(getJsonArrayAsList(jsona));
				javapojo.setResultstr(jsona.toString());
				javaresponse.setJavastatus("ERROR");
				javapojo.setQuestionId(qId);
				javapojo.setCreatedAt(currTimeAndDate);
				javadatarepo.save(javapojo);
			}
			else
			{
				javapojo.setScenario(payload.getJavapojo().getScenario());
				javapojo.setJavastr(text);
				jsona=getResultForJava(sb1);
				javaresponse.setJava(text);
				javaresponse.setJavaresult(getJsonArrayAsList(jsona));
				javaresponse.setJavaexception(sb1.toString());
				//javaresponse.setJavaexception("EXCEPTION");
				javaresponse.setJavastatus("EXCEPTION");
				javapojo.setExceptionstr(javaresponse.getJavaexception());
				//javapojo.setResultstr(javaresponse.getJavaresult().toString());
				javapojo.setResultstr(jsona.toString());
				javapojo.setQuestionId(qId);
				javapojo.setCreatedAt(currTimeAndDate);
				javadatarepo.save(javapojo);
			}
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			//javaresponse.setJavaexception(e.getMessage());
			javaresponse.setJavastatus("ERROR");
		}
		javapojo.setExceptionstr(javaresponse.getJavaexception());
		return javaresponse;
	}
	private StringBuffer printLines(String cmd, InputStream ins) throws Exception {
		int ctr=0;
		String line = null;
		BufferedReader in = new BufferedReader(
				new InputStreamReader(ins));
		while ((line = in.readLine()) != null) {
			sb1.append(line);
			sb1.append("\n");
			ctr++;
		}
		in.close();
		in=null;
		return sb1;
	}

	private StringBuffer runProcess(String command) throws Exception {
		Process pro = Runtime.getRuntime().exec(command);
		InputStream temp = pro.getInputStream();
		sb1=printLines(command + " stdout:", temp);
		temp.close();
		temp = pro.getErrorStream();
		StringBuffer sb2=printLines(command + " stderr:", temp);
		temp.close();
		temp = null;
		pro.waitFor();
		System.out.println(command + " exitValue() " + pro.exitValue());
		pro.destroy();		
		return sb1;
	}

	private JSONArray getResultForJava(StringBuffer sb1) throws Exception {
		JSONArray json = new JSONArray();
		JSONObject obj = new JSONObject();
		obj.put("output", sb1);
		json.put(obj);
		return json;
	}

	private List getJsonArrayAsList(JSONArray jsona) {
		return jsona.toList();
	}

	public static String getAlphaNumericString(int n) 
	{ 

		// chose a Character random from this String 
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
				+ "0123456789"
				+ "abcdefghijklmnopqrstuvxyz"; 

		// create StringBuffer size of AlphaNumericString 
		StringBuffer sb1 = new StringBuffer(n); 

		for (int i = 0; i < n; i++) { 
			int index = (int)(AlphaNumericString.length() * Math.random()); 
			// add Character one by one in end of sb 
			sb1.append(AlphaNumericString .charAt(index)); 
		}    
		return sb1.toString(); 
	}

	/*
	 * public void deleteFile(int numMinutes) { String dir = "d:/Java"; File
	 * directory = new File(dir); File[] fList = directory.listFiles();
	 * 
	 * if (fList != null){ for (File file : fList){ if (file.isFile()) { long diff =
	 * new Date().getTime() - file.lastModified(); long cutoff = (numMinutes * (60 *
	 * 1000));
	 * 
	 * if (diff > cutoff) { file.delete(); } } } } }
	 */
}

