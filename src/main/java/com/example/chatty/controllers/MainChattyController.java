package com.example.chatty.controllers;

import com.example.chatty.data.ChattyPOJO;
import com.example.chatty.data.entities.Answer;
import com.example.chatty.data.predefinedPhrases.AlphabetClass;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@RestController
public class MainChattyController {

    private final ChattyPOJO chatty = new ChattyPOJO();
    private final AlphabetClass alphabet = new AlphabetClass();

    @GetMapping("/questions")
    public Map<String, List<String>> possibleQuestions(){
        Map<String, List<String>> questions = new HashMap<>();
        List<Pattern> possibleQuestions = new ArrayList<>();
        possibleQuestions.addAll(alphabet.getGreetingPatterns());
        possibleQuestions.addAll(alphabet.getJokeRequestPatterns());
        possibleQuestions.addAll(alphabet.getConvRequestPatterns());
        List<String> possibleRequests = new ArrayList<>();
        for (Pattern pattern: possibleQuestions){
            possibleRequests.add(pattern.toString());
        }
        questions.put("questions", possibleRequests);
        return questions;
    };

    @PostMapping("/conversation")
    public Map<String, String> getResponse(String request) throws Exception {
        Map<String, String> message = new HashMap<>();
        message.put("message", chatty.answerTheRequest(request).getAnswer());
        return message;
    }
}
