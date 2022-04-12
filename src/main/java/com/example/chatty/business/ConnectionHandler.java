package com.example.chatty.business;

import org.apache.catalina.connector.InputBuffer;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Class handling the connection to Frankfurt opensource API and getting JSONObject of actual rates.
 */
public class ConnectionHandler {
    private final URL url;

    public ConnectionHandler(String url) throws MalformedURLException {
        this.url = new URL(url);
    }

    public HttpURLConnection connect() throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("accept", "application/json");
        return connection;
    }

    public JSONObject getJSON(HttpURLConnection connection) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = reader.readLine()) != null ){
            response.append(inputLine);
        }
        reader.close();
        JSONObject jsonObject = new JSONObject(response.toString());
        return new JSONObject(jsonObject.getJSONObject("rates"));
    }

}