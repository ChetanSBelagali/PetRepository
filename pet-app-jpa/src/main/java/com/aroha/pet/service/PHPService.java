package com.aroha.pet.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aroha.pet.payload.PHPPayload;
import com.aroha.pet.payload.PHPResponse;
import com.aroha.pet.repository.PHPRepo;
import com.aroha.pet.security.UserPrincipal;

@Service
public class PHPService {
	
	@Autowired
	PHPRepo phpRepo;
	
	StringBuffer sbuffer=null;
	StringBuffer sb=null;

	public PHPResponse executePHP(PHPPayload phpPayload, UserPrincipal currentUser) throws IOException {
		// TODO Auto-generated method stub
		Path currentPath=Paths.get("");
		String projectPath=currentPath.toAbsolutePath().toString();
		String dirName=projectPath+"\\"+"PHPPrograms";
		String fileString=generateRandomWord(8);
		String compileFileName=fileString+"_"+currentUser.getName()+".php";
		File newFile=new File(projectPath+"\\"+"PHPPrograms");
		newFile.mkdir();
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(dirName+"\\"+compileFileName));
		String phpCode=phpPayload.getPhppojo().getPhpstr();
		//System.out.println("C Code is: "+cCode);
		sbuffer=new StringBuffer(phpCode);
		sb=new StringBuffer();
		writer.write(sbuffer.toString());
		writer.close();
		writer = null;
		return null;
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
	

}
