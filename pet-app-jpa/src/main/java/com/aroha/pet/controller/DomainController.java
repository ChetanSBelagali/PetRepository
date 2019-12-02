package com.aroha.pet.controller;

import java.io.File;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.aroha.pet.model.Domain;
import com.aroha.pet.model.Function;
import com.aroha.pet.model.Question;
import com.aroha.pet.model.Scenario;
import com.aroha.pet.payload.CSV;
import com.aroha.pet.payload.DomainRequest;
import com.aroha.pet.service.DBService;
import com.aroha.pet.service.DomainService;
import com.aroha.pet.service.FunctionService;
import com.aroha.pet.service.QuestionService;
import com.aroha.pet.service.ScenarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/domain")
public class DomainController {

    @Autowired
    FunctionService functionService;

    @Autowired
    DomainService domainService;

    @Autowired
    ScenarioService scenarioService;

    @Autowired
    QuestionService questionService;

    @Autowired
    DBService dbService;

    @PostMapping("/savedomain")
    public ResponseEntity<?> saveDomainData(@RequestBody Domain domain) {
        return ResponseEntity.ok(domainService.saveDomain(domain));
    }

    @GetMapping("/getDomains")
    public ResponseEntity<?> getDomainData() {
        return ResponseEntity.ok(domainService.getAllDomains());
    }

    @PostMapping("/saveFunction")
    public ResponseEntity<?> createFunction(@RequestBody DomainRequest domainData) {
        int domainId = domainData.getDomainId();
        Function function = domainData.getFunction();
        return ResponseEntity.ok(functionService.createFunction(domainId, function));
    }

    @PostMapping("/getFunctions")
    public ResponseEntity<?> getAllFunctions(@RequestBody DomainRequest domainData) {
        int domainId = domainData.getDomainId();
        return ResponseEntity.ok(functionService.getAllFunctions(domainId));
    }

    @PostMapping("/saveScenario")
    public ResponseEntity<?> createScenario(@RequestParam("model") String model, @RequestPart(name = "file", required = false) MultipartFile file) {

        ObjectMapper mapper = new ObjectMapper();
        Scenario scenario = null;
        try {
            DomainRequest domainData = mapper.readValue(model, DomainRequest.class);
            int domainId = domainData.getDomainId();
            int functionId = domainData.getFunctionId();
            scenario = domainData.getScenario();
            if (file != null) {
                scenario.setImage(file.getBytes());
                scenario.setFileName(file.getOriginalFilename());
            }
            return ResponseEntity.ok(scenarioService.createScenario(domainId, functionId, scenario));
        } catch (Exception ex) {
            return ResponseEntity.ok(ex.getMessage());
        }

    }

    @PostMapping(value = "/getScenarioImage")
    public ResponseEntity<?> getScenario(@RequestBody DomainRequest domain) {

        int scenarioId = domain.getScenarioId();
        Scenario scenario = scenarioService.getFile(scenarioId);
        byte[] encoded = Base64.getEncoder().encode(scenario.getImage());
        return ResponseEntity.ok(new String(encoded));

    }

    @PostMapping("/getScenarios")
    public ResponseEntity<?> getAllScenarios(@RequestBody DomainRequest domainData) {
        int domainId = domainData.getDomainId();
        int functionId = domainData.getFunctionId();
        return ResponseEntity.ok(scenarioService.getAllScenario(domainId, functionId));
    }

    /*
	@PostMapping("/saveQuestion")
	public ResponseEntity<?> createQuestion(@RequestBody DomainRequest domainData){
		int domainId=domainData.getDomainId();
		int functionId=domainData.getFunctionId();
		int scenarioId=domainData.getScenarioId();
		Question question=domainData.getQuestion();
		//questionService.createQuestion(domainId, functionId, scenarioId, question);
		return ResponseEntity.ok(questionService.createQuestion(domainId, functionId, scenarioId, question));
	}
     */
    @PostMapping("/saveQuestion")
    public ResponseEntity<?> createQuestion(@RequestParam("model") String model, @RequestPart(name = "file", required = false) MultipartFile file) {
        ObjectMapper mapper = new ObjectMapper();
        Question question = null;
        try {
            DomainRequest domainData = mapper.readValue(model, DomainRequest.class);
            int domainId = domainData.getDomainId();
            int functionId = domainData.getFunctionId();
            int scenarioId = domainData.getScenarioId();
            question = domainData.getQuestion();
          if (file != null) {
                String path = file.getOriginalFilename();
                File csv1 = new File(path);
                try (InputStream in = new FileInputStream(csv1)) {
			CSV csv = new CSV(true, ',', in );
			List < String > fieldNames = null;
			if (csv.hasNext()) fieldNames = new ArrayList < > (csv.next());
			List < Map < String, String >> list = new ArrayList < > ();
			while (csv.hasNext()) {
				List < String > x = csv.next();
				Map < String, String > obj = new LinkedHashMap < > ();
				for (int i = 0; i < fieldNames.size(); i++) {
					obj.put(fieldNames.get(i), x.get(i));
				}
				list.add(obj);
			}
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			//mapper.writeValue(System.out, list);
                        question.setAnswer(mapper.writeValueAsString(list).toString());
		}
            } 
            return ResponseEntity.ok(questionService.createQuestion(domainId, functionId, scenarioId, question));
        } catch (Exception ex) {
            return ResponseEntity.ok(ex.getMessage());
        }

    }

    @PostMapping("/getQuestions")
    public ResponseEntity<?> getAllQuestion(@RequestBody DomainRequest domainData) {
        int scenarioId = domainData.getScenarioId();
        return ResponseEntity.ok(questionService.getQuestionData(scenarioId));
    }

    @GetMapping("/getDomain")
    public ResponseEntity<?> getDomain() {
        return ResponseEntity.ok(domainService.getDomain());
    }
}
