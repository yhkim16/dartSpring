package com.yhkim.hello.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class mainController {
    @RequestMapping("/")
    public ModelAndView index() { return new ModelAndView("redirect:/main"); }
    @RequestMapping("/main")
    public String main(Model model) {
        model.addAttribute("name","main");
        return "index";
    }
    @RequestMapping("/demo")
    public String demo(){ return "Demo"; }

    @RequestMapping("/dart")
    public String dart(Model model) {
        model.addAttribute("name","dart");
        return "dart";
    }
    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("name","login");
        return "login";
    }
}
