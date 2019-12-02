package com.aroha.pet.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aroha.pet.exception.ResourceNotFoundException;
import com.aroha.pet.model.Role;
import com.aroha.pet.model.RoleName;
import com.aroha.pet.model.User;
import com.aroha.pet.payload.SignUpRequest;
import com.aroha.pet.repository.RoleRepository;
import com.aroha.pet.repository.UserRepository;

/**
 *
 * @author Sony George | Date : 6 Mar, 2019 6:05:12 PM
 */
@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	public User save(User user) {
		return userRepository.save(user);
	}

	public Optional<Role> findByRoleName(RoleName roleName) {
		return roleRepository.findByName(roleName);
	}

	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
//	public Optional<User> findById(long id) {
//		return userRepository.findById(id);
//	}

	public List<User> findByRoles(String roleName) {
		return userRepository.findByRoles(roleName);
	}
	
	public User updateData(User user) {
		
		User getUserEmail=userRepository.findByEmail(user.getEmail())
				.orElseThrow(()-> new ResourceNotFoundException("User", "username", user.getEmail()));
		if(user.getName()!=null) {
		getUserEmail.setName(user.getName());}
		if(user.getAddress()!=null) {
		getUserEmail.setAddress(user.getAddress());}
		if(user.getPhoneNo()!=null) {
		getUserEmail.setPhoneNo(user.getPhoneNo());}
		if(user.getAltPhoneNo()!=null) {
		getUserEmail.setAltPhoneNo(user.getAltPhoneNo());}
		if(user.getDateOfJoin()!=null) {
		getUserEmail.setDateOfJoin(user.getDateOfJoin());}
		if(user.getEmail()!=null) {
		getUserEmail.setEmail(user.getEmail());}
		if(user.getPrimarySkills()!=null) {
		getUserEmail.setPrimarySkills(user.getPrimarySkills());}
		if(user.getSecondarySkills()!=null) {
		getUserEmail.setSecondarySkills(user.getSecondarySkills());}
		
		return userRepository.save(getUserEmail);
					
	}
}
