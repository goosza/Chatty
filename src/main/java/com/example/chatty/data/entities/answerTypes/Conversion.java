package com.example.chatty.data.entities.answerTypes;

import com.example.chatty.data.entities.Answer;

import static com.example.chatty.data.enums.RequestType.CONVERSION;

public class Conversion extends Answer {
    public Conversion(String answer) {
        super(answer, CONVERSION);
    }
}
