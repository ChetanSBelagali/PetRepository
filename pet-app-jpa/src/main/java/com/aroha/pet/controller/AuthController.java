package com.aroha.pet.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.aroha.pet.exception.AppException;
import com.aroha.pet.model.DbInfo;
import com.aroha.pet.model.Domain;
import com.aroha.pet.model.Function;
import com.aroha.pet.model.QueryInfo;
import com.aroha.pet.model.Role;
import com.aroha.pet.model.RoleName;
import com.aroha.pet.model.Scenario;
import com.aroha.pet.model.User;
import com.aroha.pet.payload.ApiResponse;
import com.aroha.pet.payload.DomainDataRequest;
import com.aroha.pet.payload.DomainRequest;
import com.aroha.pet.payload.JwtAuthenticationResponse;
import com.aroha.pet.payload.LoginRequest;
import com.aroha.pet.payload.SignUpRequest;
import com.aroha.pet.repository.DomainRepository;
import com.aroha.pet.repository.FunctionRepository;
import com.aroha.pet.repository.ScenarioRepository;
import com.aroha.pet.security.CurrentUser;
import com.aroha.pet.security.JwtTokenProvider;
import com.aroha.pet.security.UserPrincipal;
import com.aroha.pet.service.DBService;
import com.aroha.pet.service.DomainService;
import com.aroha.pet.service.UserService;

/**
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	JwtTokenProvider tokenProvider;

	@Autowired
	UserService userService;
	
	@Autowired
    DBService dbService;
	
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						loginRequest.getUsernameOrEmail(),
						loginRequest.getPassword()
				)
		);

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = tokenProvider.generateToken(authentication);
		return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest, @CurrentUser UserPrincipal currentUser) {

		if (userService.existsByEmail(signUpRequest.getEmail())) {
			return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
					HttpStatus.BAD_REQUEST);
		}

		// Creating user's account
		User user = new User(signUpRequest.getName(),
				signUpRequest.getEmail(), signUpRequest.getPassword());

		user.setPassword(passwordEncoder.encode(user.getPassword()));
                System.out.println("SignUp request: "+signUpRequest.getUserType());
		Role userRole = null;
		if (signUpRequest.getUserType().equals("learner")) {
                 
			userRole = userService.findByRoleName(RoleName.ROLE_USER)
					.orElseThrow(() -> new AppException("User Role not set."));
                
		}
		if (signUpRequest.getUserType().equals("mentor")) {
			if (currentUser.isAdminRole()) {
				userRole = userService.findByRoleName(RoleName.ROLE_MENTOR)
						.orElseThrow(() -> new AppException("User Role not set."));
			} else {
				throw new RuntimeException("Only admin can create Menter");
			}
		}
		if (signUpRequest.getUserType().equals("admin")) {
			if (currentUser.isAdminRole()) {
				userRole = userService.findByRoleName(RoleName.ROLE_ADMIN)
						.orElseThrow(() -> new AppException("User Role not set."));
			} else {
	
				throw new RuntimeException("Only admin can create another admin");
			}
		}

		user.setPhoneNo(signUpRequest.getPhoneNo());
		user.setAltPhoneNo(signUpRequest.getAltPhoneNo());
		user.setPrimarySkills(signUpRequest.getPrimarySkills());
		user.setSecondarySkills(signUpRequest.getSecondarySkills());
		user.setAddress(signUpRequest.getAddress());
		user.setDateOfJoin(signUpRequest.getDateOfJoin());
		user.setSoe(signUpRequest.getSoe());
		user.setSoeRef(signUpRequest.getSoeRef());
		user.setRoles(Collections.singleton(userRole));
		Optional<DbInfo> db=dbService.getDbInfoById(signUpRequest.getDbId());
		if(db.isPresent()) {
			DbInfo dd=db.get();
			user.getDbs().add(dd);
		}
		User result = userService.save(user);
		URI location = ServletUriComponentsBuilder
				.fromCurrentContextPath().path("/users/{username}")
				.buildAndExpand(result.getName().replaceAll(" ", "")).toUri();
		return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
	}
}
