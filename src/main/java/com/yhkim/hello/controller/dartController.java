package com.yhkim.hello.controller;

import com.yhkim.hello.service.dartService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.io.*;
import java.text.ParseException;

@RestController
public class dartController {
    @Autowired
    private final dartService dartservice;
    @Autowired
    private WebApplicationContext webApplicationContext;

    public dartController(dartService dartservice) {
        this.dartservice = dartservice;
    }
    @ResponseBody
    @RequestMapping("/dart")
    public void dart() {
        ClassPathResource resource = new ClassPathResource("CORPCODE.xml");
        try {
            BufferedReader buffreader = new BufferedReader(new InputStreamReader(resource.getInputStream(), "UTF-8"));
            StringBuffer strbuf = new StringBuffer();
            String data = "";
            while ((data = buffreader.readLine()) != null) {
                strbuf.append(data);
            }
            buffreader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //TODO:DART page 작성

    }
    @ResponseBody
    @RequestMapping("/getOsung")
    public JSONObject getOsung() throws ParseException, org.json.simple.parser.ParseException {

        String corp_code = "00350048"; //Osung Advanced Material
        JSONObject response = dartservice.getCompanyInfo(corp_code);
        //System.out.println(response);

        return response;
    }

}
