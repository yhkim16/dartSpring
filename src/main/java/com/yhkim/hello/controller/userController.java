package com.yhkim.hello.controller;

import com.yhkim.hello.dto.User;
import com.yhkim.hello.repository.UserRepository;
import lombok.*;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
public class userController {

    private final UserRepository userRepository;

    @ResponseBody
    @RequestMapping(value = "/user",method= RequestMethod.POST)//Sign Up
    public JSONObject post_user(HttpSession session, @RequestBody String data) {
        JSONObject res = new JSONObject();
        boolean success = false;
        if(userRepository.findUserByUserName("name") instanceof User) {//user가 존재하면 fail return
            res.put("result",success);
            res.put("message","User Already Exist!");
            return res;
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(16);
        String pwd_encoded = passwordEncoder.encode("pwd");
        User user = User.builder()
                .userName("name")
                .userPwd(pwd_encoded)
                .build();
        userRepository.save(user);
        session.setAttribute("user_name",user.getUserName());
        success = true;
        res.put("session","");
        return res;
    }
    @ResponseBody
    @RequestMapping(value = "/user",method= RequestMethod.DELETE)//회원탈퇴
    public JSONObject delete_user(HttpSession session, @RequestBody String data) {
        JSONObject res = new JSONObject();
        boolean success = false;
        String user_name = (String) session.getAttribute("user_name");
        if(user_name == "") {//로그인 정보가 비어있으면 리턴
            res.put("result",success);
            return res;
        }
        userRepository.deleteByUserName("name");
        success = true;
        res.put("result",success);
        return res;
    }
    @ResponseBody
    @RequestMapping(value = "/session",method= RequestMethod.POST)//LogIn
    public JSONObject post_session(HttpSession session, @RequestBody String data) {
        JSONObject res = new JSONObject();
        boolean success = false;
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(16);
        String pwd_encoded = passwordEncoder.encode("pwd");
        User user = userRepository.findUserByUserNameAndUserPwd("name",pwd_encoded);
        session.setAttribute("user_name",user.getUserName());
        success = true;
        res.put("result",success);
        return res;
    }
    @ResponseBody
    @RequestMapping(value = "/session",method= RequestMethod.DELETE)//LogOut
    public JSONObject delete_session(HttpSession session) {
        JSONObject res = new JSONObject();
        boolean success = false;
        try {
            session.invalidate();
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
            success = false;
        } finally {
            res.put("result",success);
        }
        return res;
    }

}
