package com.example.chatty.data.entities;

import com.example.chatty.data.enums.RequestType;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Answer entity for Chatty. Can be Greeting, Joke, Conversion or NotUnderstood type.
 */
public abstract class Answer {
    private String answer;
    @JsonIgnore
    private RequestType type;

    public Answer(String answer, RequestType type) {
        this.answer = answer;
        this.type = type;
    }

    public String getAnswer() {
        return answer;
    }

    public RequestType getType() {
        return type;
    }
}
