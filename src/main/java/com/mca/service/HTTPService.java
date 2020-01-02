package com.mca.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class HTTPService {

    public Map<String,Object> postRequest(String url, Map<String, String> params) throws IOException {

        Map<String, Object> httpResult = new HashMap<>();

        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String,String> param:params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

        URL requestURL = new URL(url);
        HttpURLConnection conn = (HttpURLConnection)requestURL.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.setDoOutput(true);
        conn.getOutputStream().write(postDataBytes);

        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));
        String inputLine;

        while ((inputLine = bufferedReader.readLine()) != null) {
            System.out.println(inputLine);
            String arr[] = inputLine.split("=");
            httpResult.put(arr[0], arr[1]);
        }

        bufferedReader.close();

        return httpResult;
    }

}
