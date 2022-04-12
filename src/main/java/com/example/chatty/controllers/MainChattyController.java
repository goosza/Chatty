package com.example.chatty.controllers;

import com.example.chatty.data.ChattyPOJO;
import com.example.chatty.data.entities.Answer;
import com.example.chatty.data.predefinedPhrases.AlphabetClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static com.example.chatty.data.enums.RequestType.NOTUNDERSTOOD;

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
        possibleQuestions.add(alphabet.getConvRequestPattern());

        List<String> possibleRequests = new ArrayList<>();
        for (Pattern pattern: possibleQuestions){
            possibleRequests.add(pattern.toString());
        }

        questions.put("questions", possibleRequests);
        return questions;
    };

    @PostMapping("/conversation")
    public ResponseEntity<Answer> getResponse(@RequestBody String request) throws Exception {
        Answer answer = chatty.answerTheRequest(request);
        if (answer.getType().equals(NOTUNDERSTOOD))
            return new ResponseEntity<>(answer, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(answer, HttpStatus.OK);
    }
}
