package com.yhkim.hello.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class mainController {
    @RequestMapping("/")
    public String index() {
        return "Hello World!";
    }
    @RequestMapping("/main")
    public ModelAndView main() { return new ModelAndView("redirect:/demo"); }
    @RequestMapping("/demo")
    public String demo(){ return "Demo"; }

    @RequestMapping("/dart")
    public String dart(Model model) {
        model.addAttribute("name","dart");
        return "dart";
    }
}
