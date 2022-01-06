package com.yhkim.hello;

import com.yhkim.hello.controller.dartController;
import com.yhkim.hello.service.dartService;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import java.text.ParseException;
@WebAppConfiguration
@SpringBootTest
class HelloApplicationTests {

    @Test
    void contextLoads() {
    }
    @Autowired
    private dartService dartService;
    @Test
    void getInfo() throws ParseException, org.json.simple.parser.ParseException {
        //String corp_code = "00350048"; Osung
        String corp_code = "00434456";//일산약품
        JSONObject json = dartService.getCompanyInfo(corp_code);
        System.out.println(json.toJSONString());
    }
    @Test
    void getInfoFromDB() {
        String corp_code = "00350048";// Osung
        JSONObject json = dartService.getCompanyInfoFromCacheDB(corp_code);
        System.out.println(json);
    }
    @Autowired
    private dartController dartController;
    @Test
    void testDartPage() {
        dartController.dart();
    }
}
