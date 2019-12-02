package com.aroha.pet.service;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import com.aroha.pet.model.Domain;
import com.aroha.pet.model.Function;
import com.aroha.pet.model.Question;
import com.aroha.pet.model.Scenario;
import com.aroha.pet.payload.QuestionDataRequest;
import com.aroha.pet.repository.DomainRepository;
import com.aroha.pet.repository.FunctionRepository;
import com.aroha.pet.repository.QuestionRepository;
import com.aroha.pet.repository.ScenarioRepository;

@Service
public class QuestionService {

	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private ScenarioRepository scenarioRepository;
	
	@Autowired
	private DomainRepository domainRepository;
	
	@Autowired
	private FunctionRepository functionRepository;
	
	
	public String createQuestion(int domainId, int functionId, int scenarioId, Question question) {
		
		Optional<Domain> byDomainId= domainRepository.findById(domainId);
		Optional<Function> byFunctionId= functionRepository.findById(functionId);
		Optional<Scenario> byScenarioId= scenarioRepository.findById(scenarioId);
		
		 if (!byDomainId.isPresent()) {
	            throw new ResourceNotFoundException("Domain with  id "+domainId+" not Exist");
	        }
		 if (!byFunctionId.isPresent()) {
	            throw new ResourceNotFoundException("Function with  id "+functionId+" not Exist");
	        }
		 if(!byScenarioId.isPresent()) {
			 throw new ResourceNotFoundException("Scenario with id "+scenarioId+" not Exist");
		 }
		 
		 Domain d=byDomainId.get();
		 Function f=byFunctionId.get();
		 Scenario s=byScenarioId.get();
		 f.setDomain(d);
		 d.getFunctions().add(f);
		 s.setFunction(f);
		 f.getScenario().add(s);
		 question.setScenario(s);
		 s.getQues().add(question);
		 try {
		 questionRepository.save(question);}
		 catch(Exception ex) {return ex.getMessage();}
		 return "Question Saved Successfully";
	}
	
	public List<QuestionDataRequest> getQuestionData(int scenarioId){
		
		List<Question> listQuestion = questionRepository.findAll();
		List<QuestionDataRequest> listQuestionDataRequest = new ArrayList<QuestionDataRequest>();
		
		Iterator<Question> itr=listQuestion.iterator();
		
		while(itr.hasNext()) {
			Question question = itr.next();
			if(question.getScenario().getId() != scenarioId) continue;
			QuestionDataRequest questionData = new QuestionDataRequest();
			questionData.setQuestionId(question.getId());
			questionData.setQuestionDescription(question.getQuestionDesc());
			questionData.setAnswer(question.getAnswer());
			listQuestionDataRequest.add(questionData);
		}
		
		return listQuestionDataRequest;
		
	}
}
