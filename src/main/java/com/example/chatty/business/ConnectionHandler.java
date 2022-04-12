package com.example.chatty.business;

import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Class handling the connection to Frankfurt opensource API and getting JSONObject of actual rates.
 */

public class ConnectionHandler {
    private URL url;

    public ConnectionHandler(String url) throws MalformedURLException {
        this.url = new URL(url);
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public void connect() throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("accept", "application/json");
        connection.connect();
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            stringBuilder.append((char) cp);
        }
        return stringBuilder.toString();
    }

    public JSONObject getJSON() throws IOException {
        InputStream inputStream = url.openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json.getJSONObject("rates");
        } finally {
            inputStream.close();
        }
    }

}
