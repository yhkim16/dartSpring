package com.yhkim.hello.controller;

import com.yhkim.hello.service.dartService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
public class dartController {
    @Autowired
    private final dartService dartservice;

    public dartController(dartService dartservice) {
        this.dartservice = dartservice;
    }

    @ResponseBody
    @RequestMapping("/getOsung")
    public JSONObject getOsung() throws ParseException, org.json.simple.parser.ParseException {

        String corp_code = "00350048"; //Osung Advanced Material
        JSONObject response = dartservice.getCompanyInfo(corp_code);
        return response;
    }
    @ResponseBody
    @RequestMapping(value = "/company/{corp_code}",method= RequestMethod.GET)
    public ResponseEntity<JSONObject> company (@PathVariable("corp_code") String corp_code) {
        JSONObject result = dartservice.getCompanyInfoFromCacheDB(corp_code);
        if (result == null){
            JSONObject error_json = new JSONObject();
            error_json.put("ErrCode", 404);
            error_json.put("response","company " + corp_code +" is Not Found");
            return new ResponseEntity<>(error_json,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/company/{corp_code}",method= RequestMethod.POST)
    public ResponseEntity<JSONObject> company_post (@PathVariable("corp_code") String corp_code) throws ParseException, org.json.simple.parser.ParseException {
        JSONObject result = dartservice.getCompanyInfo(corp_code);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/company/list",method= RequestMethod.GET)
    public JSONObject companyList() {
        JSONObject res = dartservice.getCompanyList();
        return res;
    }
    @ResponseBody
    @RequestMapping(value = "/company/list/{page_number}",method= RequestMethod.GET)
    public JSONArray companyList(@PathVariable("page_number") int page_number) {
        JSONObject CompanyList = dartservice.getCompanyList();
        JSONObject tmp = (JSONObject) CompanyList.get("result");
        JSONArray list = (JSONArray) tmp.get("list");
        JSONArray res = new JSONArray();
        for (int i = ((page_number- 1) * 10)  ; i < (page_number * 10);i++) {
            res.add(list.get(i));
        }
        return res;
    }

}
