package com.yhkim.hello.controller;

import com.yhkim.hello.service.dartService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
public class dartController {
    @Autowired
    private final dartService dartservice;

    public dartController(dartService dartservice) {
        this.dartservice = dartservice;
    }
    @ResponseBody
    @RequestMapping("/dart")
    public void dart() {
        //TODO:DART page 작성

    }
    @ResponseBody
    @RequestMapping("/getOsung")
    public JSONObject getOsung() throws ParseException, org.json.simple.parser.ParseException {

        String corp_code = "00350048"; //Osung Advanced Material
        JSONObject response = dartservice.getCompanyInfo(corp_code);
        System.out.println(response);

        return response;
    }

}
