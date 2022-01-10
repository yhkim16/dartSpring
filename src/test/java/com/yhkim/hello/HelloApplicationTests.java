package com.yhkim.hello;

import com.yhkim.hello.controller.bbsController;
import com.yhkim.hello.controller.dartController;
import com.yhkim.hello.controller.userController;
import com.yhkim.hello.dto.Article;
import com.yhkim.hello.dto.User;
import com.yhkim.hello.repository.UserRepository;
import com.yhkim.hello.service.bbsService;
import com.yhkim.hello.service.dartService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
@WebAppConfiguration
@SpringBootTest
class HelloApplicationTests {
    @Autowired
    MockHttpServletRequest request;

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
        //dartController.dart(new ModelAndView(), new HttpServletRequest());
    }
    @Test
    void testcompanylist() {
        dartController.companyList(1);
    }
    @Autowired
    private bbsService bbsService;
    @Test
    void testBBSServiceSaveArticle() {
        Article article = Article.builder()
                .board("main")
                .title("Test Article")
                .author("me")
                .contents("FOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO")
                .build();
        bbsService.saveArticle(article);
    }
    @Autowired
    private bbsController bbsController;
    @Test
    void testArticlePut() {
        Article article = Article.builder()
                .board("a")
                .title("bb Article")
                .author("cc")
                .contents("cccccccccccccccccccccccccccccccccccccccccccccccccccnce//wn")
                .build();
        bbsController.put_article(article, 21);
    }
    @Autowired
    private userController userController;
    @Test
    void testinputnewuser(){
        JSONObject json = new JSONObject();
        json.put("user_name", "me");
        json.put("user_pwd", "asdf");
        userController.post_user(request,json);
    }
    @Autowired
    private UserRepository userRepository;
    @Test
    void testUserRepository() {
        User user = userRepository.findUserByUserName("me");
        System.out.println(user);
        final String pwd = "asdf";
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(16);
        //String pwd_encoded = passwordEncoder.encode(pwd);
        boolean match = passwordEncoder.matches(pwd,user.getUserPwd());
        System.out.println(match);
    }

}
