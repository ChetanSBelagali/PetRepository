package com.aroha.pet.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import com.aroha.pet.exception.FileNotFoundException;
import com.aroha.pet.model.Domain;
import com.aroha.pet.model.Function;
import com.aroha.pet.model.Scenario;
import com.aroha.pet.payload.ScenarioDataRequest;
import com.aroha.pet.repository.DomainRepository;
import com.aroha.pet.repository.FunctionRepository;
import com.aroha.pet.repository.ScenarioRepository;


@Service
public class ScenarioService {

	@Autowired
	ScenarioRepository scenarioRepository;
	
	@Autowired
	DomainRepository domainRepository;
	
	@Autowired
	FunctionRepository functionRepository;

	
	public List<ScenarioDataRequest> getAllScenario(int domainId, int functionId){
		List<Scenario> scenarioList=scenarioRepository.findAll();
		List<ScenarioDataRequest> listScenarioData=new ArrayList<>();
		
		Iterator<Scenario> itr=scenarioList.iterator();
		while(itr.hasNext()) {
			Scenario sc=itr.next();
			if(sc.getFunction().getId() != functionId || sc.getFunction().getDomain().getId() != domainId) continue;
			ScenarioDataRequest scData=new ScenarioDataRequest();
			scData.setScenarioId(sc.getId());
			scData.setScenarioTitle(sc.getScenarioTitle());
			listScenarioData.add(scData);
		}
		
		return listScenarioData;
	}
	
	public String createScenario(int domainId, int functionId, Scenario scenario) {
		
		
		Optional<Domain> byIdDomain = domainRepository.findById(domainId);
		Optional<Function> byIdFunction = functionRepository.findById(functionId);
		 if (!byIdDomain.isPresent()) {
	            throw new ResourceNotFoundException("Domain with a id "+domainId+" Not Exist");
	        }
		 if (!byIdFunction.isPresent()) {
	            throw new ResourceNotFoundException("Function with a id "+functionId+" Not Exist");
	        }
		 Domain d=byIdDomain.get();
		 Function f=byIdFunction.get();
		 f.setDomain(d);
		 scenario.setFunction(f);
		 try {
		 scenarioRepository.save(scenario);}catch(Exception ex) {return ex.getMessage();}
		 return "Scenario Saved Successfully";
		
	}
	public Scenario getFile(int fileId) {
		
		//System.out.println("FieldId: "+fileId);
		
		return scenarioRepository.findById(fileId).orElseThrow(()-> new FileNotFoundException("Not found"));
	}
	
}
