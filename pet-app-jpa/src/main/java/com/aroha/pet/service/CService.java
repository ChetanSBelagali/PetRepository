package com.aroha.pet.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.aroha.pet.model.CPojo;
import com.aroha.pet.payload.CPayload;
import com.aroha.pet.payload.CResponse;
import com.aroha.pet.repository.CRepo;
import com.aroha.pet.security.UserPrincipal;

@Service
public class CService {

	@Autowired
	CRepo cRepo;

	StringBuffer sbuffer=null;
	StringBuffer sb=null;
	int ctr=0;

	public CResponse executeC(CPayload cpayload, UserPrincipal currentUser) throws Exception {
		// TODO Auto-generated method stub
		Path currentPath=Paths.get("");
		String projectPath=currentPath.toAbsolutePath().toString();
		String dirName=projectPath+"\\"+"CPrograms";
		String fileString=generateRandomWord(8);
		String compileFileName=fileString+"_"+currentUser.getName()+".c";
		String executableFileName=fileString+"_"+currentUser.getName();
		File newFile=new File(projectPath+"\\"+"CPrograms");
		newFile.mkdir();

		BufferedWriter writer = new BufferedWriter(new FileWriter(dirName+"\\"+compileFileName));
		String cCode="#include<stdio.h>"+"\n"+"#include<conio.h>"+"\n"
				+"#include<assert.h>"+"\n"+"#include<math.h>"+"\n"+
				"#include<stdlib.h>"+"\n"+"#include<string.h>"+"\n"+"#include<ctype.h>"+                                           
				"\n"+cpayload.getCpojo().getCstr();
		//System.out.println("C Code is: "+cCode);
		sbuffer=new StringBuffer(cCode);
		sb=new StringBuffer();
		writer.write(sbuffer.toString());
		writer.close();
		writer = null;

		String compilationCommand="C:\\TDM-GCC-64\\bin\\gcc.exe "+dirName+"\\"+compileFileName +" -o "+ " "+dirName+"\\"+executableFileName;

		DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
		Date dateobj = new Date();
		String currTimeAndDate=df.format(dateobj);

		CPojo cpojo=new CPojo();
		CResponse cResponse = new CResponse();
		JSONArray jsona = null;

		sb=runProcess(compilationCommand);		
		System.out.println("Sb is: "+sb);
		String executableCommand=dirName+"\\"+executableFileName;
		if (runProcess(executableCommand)!=null){
			//sb=runProcess(executableCommand);
			cpojo.setScenario(cpayload.getCpojo().getScenario());
			cpojo.setCstr(cCode);
			System.out.println("=========================================================================");
			System.out.println("My sb is: "+sb);
			jsona=getResultForJava(sb);
			cpojo.setQuestionId(cpayload.getQuestionId());
			cpojo.setScenario(cpayload.getCpojo().getScenario());
			cpojo.setCreatedAt(currTimeAndDate);
			cpojo.setCreatedBy(currentUser.getId());
			cpojo.setResultstr(jsona.toString());
			cResponse.setCprogram(cCode);
			cResponse.setCresult(getJsonArrayAsList(jsona));
			cResponse.setCstatus("SUCCESS");
			cRepo.save(cpojo);
			return cResponse;	
		}
		else{
			runProcess(executableCommand);
			cpojo.setScenario(cpayload.getCpojo().getScenario());
			cpojo.setCstr(cCode);
			System.out.println("=========================================================================");
			//int idx=sb.indexOf("error");
			jsona=getResultForJava(sb);
			cpojo.setQuestionId(cpayload.getQuestionId());
			cpojo.setScenario(cpayload.getCpojo().getScenario());
			cpojo.setCreatedAt(currTimeAndDate);
			cpojo.setCreatedBy(currentUser.getId());
			String error=sb.toString().substring(sb.toString().indexOf("error"));
			System.out.println("Error is: "+error);
			int r=error.toString().indexOf("\n");
			int m=error.toString().indexOf("error");
			//cpojo.setResultstr(sb.toString().substring(sb.toString().indexOf("error")));
			cpojo.setResultstr(error.toString().substring(m, r));
			cResponse.setCprogram(cCode);
			//cResponse.setCexception(sb.toString().substring(sb.toString().indexOf("error")));
			cResponse.setCexception(error.toString().substring(m, r));
			cResponse.setCstatus("ERROR");
			cRepo.save(cpojo);
			return cResponse;	
		}

	}

	private StringBuffer printLines(String cmd, InputStream ins) throws Exception {
		String line = null;

		BufferedReader in = new BufferedReader(
				new InputStreamReader(ins));
		while ((line = in.readLine()) != null) {
			sb.append(line);
			sb.append("\n");
			ctr++;

		}
		in.close();
		in=null;
		//System.out.println("Output is: "+sb);
		return sb;
	}

	private StringBuffer runProcess(String command) throws Exception {
		StringBuffer sb2=null;
		StringBuffer sb3=null;
		try {
			Process pro = Runtime.getRuntime().exec(command);
			InputStream temp=pro.getInputStream();
			//sb=new StringBuffer();
			sb2=printLines(command + " stdout:", temp);
			temp.close();
			temp=pro.getErrorStream();
			//System.out.println("I am here: "+sb);
			sb3=printLines(command + " stderr:",temp);
			temp.close();
			temp=null;
			pro.waitFor();
			System.out.println(command + " exitValue() " + pro.exitValue());
			pro.destroy();
			return sb2;
		}
		catch(Exception ex) {
			return sb3;
		}
	}


	private String generateRandomWord(int length) {
		Random r = new Random(); // Intialize a Random Number Generator with SysTime as the seed
		StringBuilder sb = new StringBuilder(length);
		for(int i = 0; i < length; i++) { // For each letter in the word
			char tmp = (char) ('a' + r.nextInt('z' - 'a')); // Generate a letter between a and z
			sb.append(tmp); // Add it to the String
		}
		return sb.toString();
	}

	private JSONArray getResultForJava(StringBuffer sb) throws Exception {
		JSONArray json = new JSONArray();
		JSONObject obj = new JSONObject();
		obj.put("output", sb);
		json.put(obj);
		return json;
	}

	private List getJsonArrayAsList(JSONArray jsona) {
		return jsona.toList();
	}

}
