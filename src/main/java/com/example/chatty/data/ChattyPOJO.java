package com.example.chatty.data;

import com.example.chatty.business.RequestHandler;
import com.example.chatty.data.entities.Answer;
import com.example.chatty.data.entities.Request;

import java.io.IOException;


public class ChattyPOJO {

    RequestHandler requestHandler;

    public ChattyPOJO() {
        requestHandler = new RequestHandler();
    }

    public Answer answerTheRequest(String request) throws IOException {
        Request req = new Request(request);
        return requestHandler.getAnswer(req);
    };
}
