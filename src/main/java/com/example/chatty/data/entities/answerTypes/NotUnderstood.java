package com.example.chatty.data.entities.answerTypes;

import com.example.chatty.data.entities.Answer;

import static com.example.chatty.data.enums.RequestType.NOTUNDERSTOOD;

public class NotUnderstood extends Answer {
    public NotUnderstood(String answer) {
        super(answer, NOTUNDERSTOOD);
    }
}
