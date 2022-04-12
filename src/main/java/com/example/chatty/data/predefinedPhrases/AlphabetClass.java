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
    private final Pattern convRequestPattern;

    private final String[] chattyJokes = {"Why was the math teacher late to work? She took the rhombus.",
                                    "What did Yoda say when he saw himself in 4K? \"HDMI.\"",
                                    "My daughter thinks I don't give her enough privacy. At least that's what she wrote in her diary.",
                                    "The guy that invented the umbrella was gonna call it the brella. But he hesitated."};
    private final String[] chattyGreetings = {"Hello my friend! How can I help you?", "Sup!", "Hi! What would you like to ask?",
                                                "Hi! Can I help you?"};


    public AlphabetClass() {
        greetingPatterns.add(Pattern.compile(".*hello.*", Pattern.CASE_INSENSITIVE));
        greetingPatterns.add(Pattern.compile(".*hi.*", Pattern.CASE_INSENSITIVE));
        greetingPatterns.add(Pattern.compile(".*sup.*", Pattern.CASE_INSENSITIVE));
        jokeRequestPatterns.add(Pattern.compile(".*joke.*", Pattern.CASE_INSENSITIVE));
        this.convRequestPattern = Pattern.compile("(.*[^\\d+])*(?<amount>\\d++)\\s++(?<from>[A-Za-z]++)\\s++(.*)\\s++(?<to>[A-Za-z]++).*",
                Pattern.CASE_INSENSITIVE);
    }

    public List<Pattern> getGreetingPatterns() {
        return greetingPatterns;
    }

    public List<Pattern> getJokeRequestPatterns() {
        return jokeRequestPatterns;
    }

    public Pattern getConvRequestPattern() {
        return convRequestPattern;
    }

    public String[] getChattyJokes() {
        return chattyJokes;
    }

    public String[] getChattyGreetings() {
        return chattyGreetings;
    }
}
