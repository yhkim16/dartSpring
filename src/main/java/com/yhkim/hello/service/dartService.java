package com.yhkim.hello.service;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.json.simple.parser.JSONParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;

@Service
public class dartService {
    private final String apiurl = "https://opendart.fss.or.kr/api/";
    private final String apikey = "80bae36b296bb81c49a3e1d7d8807b40e1a23214";

    public JSONObject getCompanyInfo(String corp_code) throws ParseException, org.json.simple.parser.ParseException {
        JSONObject result = null;
        String data = "";
        String text = "";
        try {
            URL url = null;
            HttpURLConnection connection = null;
            StringBuffer strbuf = null;
            BufferedReader buffreader = null;

            url = new URL(apiurl + "company.json?" + "crtfc_key=" + apikey + "&" + "corp_code=" + corp_code);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Accept","application/json");
            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode != 200) { //HTTP 200 OK 아닌 경우
                System.out.println("Http response Code : " + responseCode);
            }
            else {
                buffreader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                strbuf = new StringBuffer();
                while ((data = buffreader.readLine()) != null) {
                    strbuf.append(data);
                }
                buffreader.close();
                text = strbuf.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONParser parser = new JSONParser();
        result = (JSONObject) parser.parse(text);
        return result;
    }

    public void getcorpCode() throws ParseException {
        try {
            URL url = null;
            HttpURLConnection connection = null;
            StringBuffer strbuf = null;

            url = new URL(apiurl + "corpCode.xml?" + "crtfc_key=" + apikey);
            connection = (HttpURLConnection) url.openConnection();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
