package com.example.chatty.data.entities;

import com.example.chatty.data.enums.RequestType;

/**
 * Answer entity for Chatty. Can be Greeting, Joke, Conversion or NotUnderstood type.
 */
public abstract class Answer {
    private String answer;
    private RequestType type;

    public Answer(String answer, RequestType type) {
        this.answer = answer;
        this.type = type;
    }

    public String getAnswer() {
        return answer;
    }
}
