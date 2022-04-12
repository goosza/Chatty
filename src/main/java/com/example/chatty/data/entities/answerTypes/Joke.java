package com.example.chatty.data.entities.answerTypes;

import com.example.chatty.data.entities.Answer;

import static com.example.chatty.data.enums.RequestType.JOKE;

public class Joke extends Answer {

    public Joke(String answer) {
        super(answer, JOKE);
    }

}
