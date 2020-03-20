package com.aroha.pet.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aroha.pet.payload.PythonPayload;
import com.aroha.pet.payload.PythonResponse;
import com.aroha.pet.security.CurrentUser;
import com.aroha.pet.security.UserPrincipal;
import com.aroha.pet.service.PythonService;

@RestController
@RequestMapping("/api/python")
public class PythonController {
	
	@Autowired
	private PythonService pythonService;
	
	@PostMapping("/executePython")
	public ResponseEntity<?> executePython(@RequestBody PythonPayload pythonPayload, @CurrentUser UserPrincipal currentUser) throws Exception{
		PythonResponse pythonResponse=pythonService.executePython(pythonPayload, currentUser);
		return ResponseEntity.ok(pythonResponse);		
	}

}
