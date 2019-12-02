package com.aroha.pet.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.aroha.pet.model.Domain;
import com.aroha.pet.model.Function;
import com.aroha.pet.payload.FunctionDataRequest;
import com.aroha.pet.repository.DomainRepository;
import com.aroha.pet.repository.FunctionRepository;

@Service
public class FunctionService {

	@Autowired
	FunctionRepository functionRepository;
	@Autowired
	DomainRepository domainRepository;
	

	
	public Function saveFunction(Function function) {
		return functionRepository.save(function);
	}
	
	public String createFunction(int domainId, Function function) {
		
		Optional<Domain> byId = domainRepository.findById(domainId);
		 if (!byId.isPresent()) {
	            throw new ResourceNotFoundException("Domain with a id "+domainId+" Not Exist");
	        }
		 Domain d=byId.get();
		 function.setDomain(d);
		 try {
		 functionRepository.save(function);}catch(Exception ex) {return ex.getMessage();}
		return "Function Saved Successfully";
		 
	}
	
	public List<FunctionDataRequest>  getAllFunctions(int domainId){
		 List<Function> list=functionRepository.findAll();
		 List<FunctionDataRequest> functionDataList=new ArrayList<FunctionDataRequest>();
		 Iterator<Function> itr=list.iterator();
		 while(itr.hasNext()) {
			   Function function=itr.next();
			   if(function.getDomain().getId() != domainId) continue;
			   FunctionDataRequest functionData=new FunctionDataRequest();
			   functionData.setFunctionId(function.getId());
			   functionData.setFunctionName(function.getFunctionName());
			   functionDataList.add(functionData);
		 }
		 return functionDataList;
	}
}
