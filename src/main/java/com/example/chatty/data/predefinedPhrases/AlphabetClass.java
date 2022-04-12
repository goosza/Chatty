package com.example.chatty.data.predefinedPhrases;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Class of patterns for recieved requests and collections of ready answers.
 */
public class AlphabetClass {
    private final List<Pattern> greetingPatterns = new ArrayList<>();
    private final List<Pattern> jokeRequestPatterns = new ArrayList<>();
    private final List<Pattern> convRequestPatterns = new ArrayList<>();

    private final String[] chattyJokes = {"Why was the math teacher late to work? She took the rhombus.",
                                    "What did Yoda say when he saw himself in 4K? \"HDMI.\"",
                                    "My daughter thinks I don't give her enough privacy. At least that's what she wrote in her diary.",
                                    "The guy that invented the umbrella was gonna call it the brella. But he hesitated."};
    private final String[] chattyGreetings = {"Hello my friend! How can I help you?", "Sup!", "Hi! What would you like to ask?",
                                                "Hi! Can I help you?"};


    public AlphabetClass() {
        greetingPatterns.add(Pattern.compile(".[Hh][Ee][Ll][Ll][Oo]."));
        greetingPatterns.add(Pattern.compile(".[Hh][Ii]."));
        greetingPatterns.add(Pattern.compile(".[Ss][Uu][Pp]."));
        jokeRequestPatterns.add(Pattern.compile(".[Jj][Oo][Kk][Ee]."));
        convRequestPatterns.add(Pattern.compile(".[Hh][Oo][Ww]\s[Mm][Uu][Cc][Hh].(?<num>\\d+)\s(?<cur1>[A-Za-z]).(?<cur2>[A-Z][a-z])."));
        convRequestPatterns.add(Pattern.compile(".[Cc][Oo][Nn][Vv][Ee][Rr][Tt].(?<num>\\d+)\s(?<cur1>[A-Za-z]).(?<cur2>[A-Z][a-z])."));
    }

    public List<Pattern> getGreetingPatterns() {
        return greetingPatterns;
    }

    public List<Pattern> getJokeRequestPatterns() {
        return jokeRequestPatterns;
    }

    public List<Pattern> getConvRequestPatterns() {
        return convRequestPatterns;
    }

    public String[] getChattyJokes() {
        return chattyJokes;
    }

    public String[] getChattyGreetings() {
        return chattyGreetings;
    }
}
