package com.example.chatty.business;

import com.example.chatty.data.entities.*;
import com.example.chatty.data.entities.answerTypes.Conversion;
import com.example.chatty.data.entities.answerTypes.Greeting;
import com.example.chatty.data.entities.answerTypes.Joke;
import com.example.chatty.data.entities.answerTypes.NotUnderstood;
import com.example.chatty.data.predefinedPhrases.AlphabetClass;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.chatty.data.enums.RequestType.*;

/**
 * Class handling the message determining and creating answer to send.
 */
public class RequestHandler {

    private List<Joke> predefinedJokes = new ArrayList<>();
    private List<Greeting> predefinedGreetings = new ArrayList<>();

    public RequestHandler(){
        AlphabetClass alphabet = new AlphabetClass();
        for (String s: alphabet.getChattyJokes())
            predefinedJokes.add(new Joke(s));
        for (String s: alphabet.getChattyGreetings())
            predefinedGreetings.add(new Greeting(s));
    }

    public void setPredefinedJokes(List<Joke> predefinedJokes){
        this.predefinedJokes = predefinedJokes;
    }

    public void setPredefinedGreetings(List<Greeting> predefinedGreetings) {
        this.predefinedGreetings = predefinedGreetings;
    }

    public List<Joke> getPredefinedJokes() {
        return predefinedJokes;
    }

    public List<Greeting> getPredefinedGreetings() {
        return predefinedGreetings;
    }

    /**
     * Tells if the request was the GREETING type or not
     * @param request entity
     */
    public boolean isGreetingRequest(Request request){
        return request.getType().equals(GREETING);
    }

    /**
     * Tells if the request was the JOKE type or not
     * @param request entity
     */
    public boolean isJokeRequest(Request request){
        return request.getType().equals(JOKE);
    }

    /**
     * Tells if the request was the CONVERSION type or not
     * @param request entity
     */
    public boolean isConversionRequest(Request request){
        return request.getType().equals(CONVERSION);
    }

    /**
     * Method to return random Greeting (extends {@link Answer}) of Chatty
     * @return {@link Answer} entity
     */
    public Answer randomGreeting(){
        Random rand = new Random();
        return predefinedGreetings.get(rand.nextInt(predefinedGreetings.size()));
    }

    /**
     * Method to return random Joke (extends {@link Answer}) of Chatty
     * @return {@link Answer} entity
     */
    public Answer randomJoke(){
        Random rand = new Random();
        return predefinedJokes.get(rand.nextInt(predefinedJokes.size()));
    }

    /**
     * Method to check if currencies are valid or not.
     * @param cur1 first currency extracted from Conversion request message. Convert FROM.
     * @param cur2 second currency extracted from Conversion request message. Convert TO.
     * @param rates JSONObject representing all rates values from opensource.
     */
    public boolean checkCurrencies(String cur1, String cur2, JSONObject rates){
        if (rates.has(cur1)){
           if (rates.has(cur2)){
               return true;
           } else {
               return cur2.equals("EUR");
           }
        } else {
            if (cur1.equals("EUR")){
                if (rates.has(cur2)){
                    return true;
                } else {
                    return cur2.equals("EUR");
                }
            } else {
                return false;
            }
        }
    }

    /**
     * Method to connect to Frankfurt API and extract JSONObject from API via {@link ConnectionHandler} class.
     * @return JSONObject
     * @throws IOException
     */
    public JSONObject getRates() throws IOException {
        ConnectionHandler getRates = new ConnectionHandler("https://api.frankfurter.app/latest");
        getRates.connect();
        return getRates.getJSON();
    }

    /**
     * Method considering 4 conversion cases based on the EUR base of conversion rates
     * and then counting depending on that cases.
     * @param number amount extracted from the conversion request message, which should be converted to another currency.
     * @param currency1 first currency extracted from conversion request message. Convert FROM.
     * @param currency2 second currency extracted from conversion request message. Convert TO.
     * @param rates JSONObject got from opensource representing currency rates on base of EUR.
     * @return converted amount.
     */
    public double countConversion(String number, String currency1, String currency2, JSONObject rates) {
        double amount = Double.parseDouble(number);
        if (currency1.equals("EUR") && currency2.equals("EUR")){
            return amount;
        } else if (!currency1.equals("EUR") && currency2.equals("EUR")){
            return amount/rates.getDouble(currency1);
        } else if (currency1.equals("EUR")){
            return amount * rates.getDouble(currency2);
        } else {
            return (amount/rates.getDouble(currency1))*rates.getDouble(currency2);
        }
    }

    /**
     * Method for parsing CONVERSION type user message and creating final answer of Chatty.
     * @param request message recieved.
     * @return {@link Answer} entity
     * @throws IOException
     */
    public Answer parsingRequestAndCreatingAnswer(Request request) throws IOException {
        AlphabetClass alphabet = new AlphabetClass();
        String number = null, currency1 = null, currency2 = null;
        double convertedAmount;
        Pattern pattern = alphabet.getConvRequestPattern();
        Matcher matcher = pattern.matcher(request.getRequest());
        while (matcher.find()){
            number = matcher.group("amount");
            currency1 = matcher.group("from");
            currency2 = matcher.group("to");
        }
        JSONObject rates = getRates();
        if (number == null)
            return new NotUnderstood("I'm sorry, I don't understand your question. " +
                    "It looks there is no needed amount");
        if (currency1 == null || currency2 == null)
            return new NotUnderstood("I'm sorry, I don't understand your question.");
        currency1 = currency1.replaceAll("\\s","").toUpperCase();
        currency2 = currency2.replaceAll("\\s","").toUpperCase();
        if (!checkCurrencies(currency1, currency2, rates))
            return new NotUnderstood("I'm sorry, I don't understand your question." +
                    " It looks I don't know the currency you're asking for.");
        convertedAmount = countConversion(number, currency1, currency2, rates);
        return new Conversion("It's " + convertedAmount + " " + currency2 + ".");
    }

    /**
     * Method to call parsingRequestAndCreatingAnswer method. Case of conversion request.
     * @param request
     * @return {@link Answer} entity
     * @throws IOException
     */
    public Answer conversionAnswer(Request request) throws IOException {
        return parsingRequestAndCreatingAnswer(request);
    }

    /**
     * Method to handle and determine the type of message recieved to understand what to answer.
     * @param request
     * @return {@link Answer} entity.
     * @throws IOException
     */
    public Answer requestHandling(Request request) throws IOException {
        if (isGreetingRequest(request)){
            return randomGreeting();
        }
        else if (isJokeRequest(request)){
            return randomJoke();
        }
        else if (isConversionRequest(request)){
            return conversionAnswer(request);
        }
        return new NotUnderstood("I'm sorry, I don't understand your request.");
    }

    /**
     * Method to call handling Request method.
     * @param request
     * @return {@link Answer} final answer.
     * @throws IOException
     */

    public Answer getAnswer(Request request) throws IOException {
        return requestHandling(request);
    }
}
