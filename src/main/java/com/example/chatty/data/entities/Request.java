package com.example.chatty.data.entities;

import com.example.chatty.data.enums.RequestType;
import com.example.chatty.data.predefinedPhrases.AlphabetClass;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.chatty.data.enums.RequestType.*;

/**
 * Entity class of Request (message) recieved.
 */
public class Request {

    private final String request;
    private RequestType type = null;

    /**
     * Constructor to determine which Request is recieved via {@link AlphabetClass} with patterns.
     * @param request string User message.
     */
    public Request(String request) {
        this.request = request;
        AlphabetClass alphabet = new AlphabetClass();

        List<Pattern> greetingPatterns = alphabet.getGreetingPatterns();
        List<Pattern> jokePatterns = alphabet.getJokeRequestPatterns();
        List<Pattern> conversionPatterns = alphabet.getConvRequestPatterns();

        for (Pattern pattern: greetingPatterns){
            Matcher matcher = pattern.matcher(request);
            if (matcher.matches())
                this.type = GREETING;
        }
        for (Pattern pattern: jokePatterns){
            Matcher matcher = pattern.matcher(request);
            if (matcher.matches())
                this.type = JOKE;
        }
        for (Pattern pattern: conversionPatterns){
            Matcher matcher = pattern.matcher(request);
            if (matcher.matches())
                this.type = CONVERSION;
        }

    }

    public String getRequest() {
        return request;
    }

    public RequestType getType(){
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Request request1 = (Request) o;

        if (!request.equals(request1.request)) return false;
        return type == request1.type;
    }

    @Override
    public int hashCode() {
        return request.hashCode();
    }
}
