package com.yhkim.hello.service;

import com.yhkim.hello.dto.Company;
import com.yhkim.hello.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;

import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.text.ParseException;
import java.util.NoSuchElementException;
import java.io.*;
import org.json.XML;

@Service
@RequiredArgsConstructor
public class dartService {
    private final String apiurl = "https://opendart.fss.or.kr/api/";
    @Value("${dart.apikey}")
    private String apikey;

    private final CompanyRepository companyRepository;

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

        final Company company = Company.builder()
                .corp_code(Integer.parseInt(corp_code))
                .json(result.toJSONString())
                .build();
        companyRepository.save(company);
        return result;
    }

    public JSONObject getCompanyInfoFromCacheDB(String corp_code) {
        JSONParser parser = new JSONParser();
        try {
            Company company = companyRepository.findById(Integer.parseInt(corp_code)).get();
            return (JSONObject) parser.parse(company.getJson());
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
        catch (NoSuchElementException e){
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public void getcorpCode() throws ParseException {
        try {
            URL url = null;
            HttpURLConnection connection = null;
            StringBuffer strbuf = null;

            url = new URL(apiurl + "corpCode.xml?" + "crtfc_key=" + apikey);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode != 200) { //HTTP 200 OK 아닌 경우
                System.out.println("Http response Code : " + responseCode);
            }
            else {
                ByteBuffer data = ByteBuffer.allocate(connection.getContentLength());
                BufferedReader buffreader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                strbuf = new StringBuffer();
                byte[] chunk = new byte[1024];
                int bytesRead = 0;

                while ((bytesRead = buffreader.read()) != -1) {
                    data.put(chunk,0,bytesRead);
                }
                buffreader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getCompanyList() {
        ClassPathResource resource = new ClassPathResource("CORPCODE.xml");
        JSONObject json = null;
        JSONParser parser = new JSONParser();
        try {
            BufferedReader buffreader = new BufferedReader(new InputStreamReader(resource.getInputStream(), "UTF-8"));
            StringBuffer strbuf = new StringBuffer();
            String data = "";
            while ((data = buffreader.readLine()) != null) {
                strbuf.append(data);
            }
            buffreader.close();
            json = (JSONObject) parser.parse(XML.toJSONObject(strbuf.toString(),true).toString());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
        return json;
    }
}
