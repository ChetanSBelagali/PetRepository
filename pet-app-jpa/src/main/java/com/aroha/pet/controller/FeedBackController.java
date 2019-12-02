package com.aroha.pet.controller;

import com.aroha.pet.service.FeedBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/feedback")
public class FeedBackController {

    @Autowired
    private FeedBackService feedService;

    @RequestMapping("/status")
    public ResponseEntity<?> getfeedback() {
        return ResponseEntity.ok(feedService.getData());
    }
    
    @PostMapping("/analysis")
    public ResponseEntity<?> showStudentAnalysis(@RequestParam String createdAt,@RequestParam long created_by){
    	return ResponseEntity.ok(feedService.showAnalysis(created_by, createdAt));
    }
    
}
