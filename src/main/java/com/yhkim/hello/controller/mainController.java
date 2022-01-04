package com.yhkim.hello.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class mainController {
    @RequestMapping("/")
    public String index() {
        return "Hello World!";
    }
    @RequestMapping("/main")
    public ModelAndView main() { return new ModelAndView("redirect:/demo"); }
    @RequestMapping("/demo")
    public String demo(){ return "Demo"; }
}
