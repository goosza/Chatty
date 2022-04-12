package com.example.chatty.data.entities.answerTypes;

import com.example.chatty.data.entities.Answer;

import static com.example.chatty.data.enums.RequestType.GREETING;

public class Greeting extends Answer {

    public Greeting(String answer) {
        super(answer, GREETING);
    }
}
