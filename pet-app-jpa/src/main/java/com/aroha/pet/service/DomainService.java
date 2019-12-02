package com.aroha.pet.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aroha.pet.model.Domain;
import com.aroha.pet.payload.DomainDataRequest;
import com.aroha.pet.repository.DomainRepository;
import com.aroha.pet.repository.DomainTableRepository;

@Service
public class DomainService {

	@Autowired
	DomainRepository domainRepository;
		
	@Autowired
	DomainTableRepository domainTableRepo;
	
	public List<DomainDataRequest>  getAllDomains(){
		List<Domain> list=domainRepository.findAll();
		Iterator<Domain> itr=list.iterator();
		List<DomainDataRequest> DomainList=new ArrayList<DomainDataRequest>();
		while(itr.hasNext()) {
			Domain d=itr.next();
			DomainDataRequest domainData=new DomainDataRequest();
			domainData.setDomainId(d.getId());
			domainData.setDomainName(d.getDomainName());
			DomainList.add(domainData);
		}
		return DomainList;
		
	}
	
	public String saveDomain(Domain domain) {
		try { 
		domainRepository.save(domain);}catch(Exception ex) {
		return ex.getMessage();
		}
		return "Domain Saved Successfully";
	}
	
	public List getDomain(){

		 return domainTableRepo.getDomainData();
	}
	

	
}
