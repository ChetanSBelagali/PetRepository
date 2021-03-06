package com.aroha.pet.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilterWriter;
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
import org.springframework.stereotype.Service;

import com.aroha.pet.model.JavascriptPojo;
import com.aroha.pet.model.PythonPojo;
import com.aroha.pet.payload.JavascriptResponse;
import com.aroha.pet.payload.PythonPayload;
import com.aroha.pet.payload.PythonResponse;
import com.aroha.pet.repository.PythonRepo;
import com.aroha.pet.security.UserPrincipal;

@Service
public class PythonService {

	@Autowired
	PythonRepo pythonRepo;

	StringBuffer sbuffer=null;
	StringBuffer sb=null;
	int ctr=0;

	public PythonResponse executePython(PythonPayload pythonPayload, UserPrincipal currentUser) throws Exception {
		// TODO Auto-generated method stub
		Path currentPath=Paths.get("");
		String projectPath=currentPath.toAbsolutePath().toString();
		String dirName=projectPath+"\\"+"PythonPrograms";
		String fileString=generateRandomWord(8);
		File newFile=new File(dirName);
		newFile.mkdir();
		String fileName=fileString+"_"+currentUser.getName()+".py";
		String pythonCode=pythonPayload.getPythonpojo().getPythonstr();
		sbuffer=new StringBuffer(pythonCode);
		sb=new StringBuffer();
		BufferedWriter writer=new BufferedWriter(new FileWriter(dirName+"/"+fileName));
		writer.write(sbuffer.toString());
		writer.close();
		writer=null;

		DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
		Date dateobj = new Date();
		String currTimeAndDate=df.format(dateobj);

		String executableCommand="python "+dirName+"/"+fileName;
		sb=runProcess(executableCommand);
		System.out.println("I am here: "+sb);

		PythonResponse pythonresponse=new PythonResponse();
		PythonPojo pythonpojo=new PythonPojo();
		JSONArray jsona = null;

		if(sb.toString().contains("AssertionError") || sb.toString().contains("AttributeError") || 
				sb.toString().contains("EOFError") || sb.toString().contains("FloatingPointError") || 
				sb.toString().contains("GeneratorExit") || sb.toString().contains("ImportError")||
				sb.toString().contains("IndexError") || sb.toString().contains("KeyError") || 
				sb.toString().contains("KeyboardInterrupt") || sb.toString().contains("MemoryError") || 
				sb.toString().contains("NameError") || sb.toString().contains("NotImplementedError") ||
				sb.toString().contains("OSError") || sb.toString().contains("OverflowError") || 
				sb.toString().contains("ReferenceError") || sb.toString().contains("RuntimeError") || 
				sb.toString().contains("StopIteration") || sb.toString().contains("SyntaxError") ||
				sb.toString().contains("IndentationError") || sb.toString().contains("TabError") || 
				sb.toString().contains("SystemError") || sb.toString().contains("SystemExit") || 
				sb.toString().contains("TypeError") || sb.toString().contains("UnboundLocalError") ||
				sb.toString().contains("UnicodeError") || sb.toString().contains("UnicodeEncodeError") || 
				sb.toString().contains("UnicodeDecodeError") || sb.toString().contains("UnicodeTranslateError") || 
				sb.toString().contains("ValueError") || sb.toString().contains("ZeroDivisionError")) {
			jsona=getResultForJava(sb);
			pythonpojo.setCreatedAt(currTimeAndDate);
			pythonpojo.setCreatedBy(currentUser.getId());
			pythonpojo.setPythonstr(pythonCode);
			pythonpojo.setQuestionId(pythonPayload.getQuestionId());
			pythonpojo.setScenario(pythonPayload.getPythonpojo().getScenario());
			String error=sb.toString().substring(sb.toString().indexOf(".py"));
			System.out.println("Error is: "+error);
			//int r=error.toString().indexOf("Error:");
			//int m=error.toString().indexOf("<module>");
			pythonpojo.setResultstr(error);		
			pythonresponse.setPythonprogram(pythonCode);
			pythonresponse.setPythonstatus("ERROR");
			pythonresponse.setPythonexception(error);
			pythonRepo.save(pythonpojo);
			return pythonresponse;
		}
		else {
			jsona=getResultForJava(sb);
			pythonpojo.setCreatedAt(currTimeAndDate);
			pythonpojo.setCreatedBy(currentUser.getId());
			pythonpojo.setPythonstr(pythonCode);
			pythonpojo.setQuestionId(pythonPayload.getQuestionId());
			pythonpojo.setScenario(pythonPayload.getPythonpojo().getScenario());
			pythonpojo.setResultstr(jsona.toString());	
			pythonresponse.setPythonprogram(pythonCode);
			pythonresponse.setPythonstatus("SUCCESS");
			pythonresponse.setPythonresult(getJsonArrayAsList(jsona));
			pythonRepo.save(pythonpojo);
			return pythonresponse;
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

	private StringBuffer printLines(String cmd, InputStream ins) throws Exception {
		String line = null;
		BufferedReader in = new BufferedReader(
				new InputStreamReader(ins));
		while ((line = in.readLine()) != null) {
			sb.append(line);
			sb.append("\n");
			ctr++;
		}
		return sb;
	}

	private StringBuffer runProcess(String command) throws Exception {
		StringBuffer sb2=new StringBuffer();
		Process pro = Runtime.getRuntime().exec(command);
		InputStream temp=pro.getInputStream();
		//sb=new StringBuffer();
		sb2=printLines(command + " stdout:", temp);
		temp.close();
		temp=pro.getErrorStream();
		//System.out.println("I am here: "+sb);
		printLines(command + " stderr:",temp);
		temp.close();
		temp=null;
		pro.waitFor();
		System.out.println(command + " exitValue() " + pro.exitValue());
		pro.destroy();
		return sb2;
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
