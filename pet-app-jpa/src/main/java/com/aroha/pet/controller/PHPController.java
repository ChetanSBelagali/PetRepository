package com.aroha.pet.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aroha.pet.payload.PHPPayload;
import com.aroha.pet.payload.PHPResponse;
import com.aroha.pet.security.CurrentUser;
import com.aroha.pet.security.UserPrincipal;
import com.aroha.pet.service.PHPService;

@RestController
@RequestMapping("/api/php")
public class PHPController {
	
	@Autowired
	private PHPService phpService;
	
	@PostMapping("/executePhp")
	public ResponseEntity<?> executePHP(@RequestBody PHPPayload phpPayload, @CurrentUser UserPrincipal currentUser) throws IOException{
		PHPResponse phpResponse=phpService.executePHP(phpPayload, currentUser);
		return null;
		
	}
}
