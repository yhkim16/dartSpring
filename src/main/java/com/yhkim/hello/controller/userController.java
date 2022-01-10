package com.yhkim.hello.controller;

import com.yhkim.hello.dto.User;
import com.yhkim.hello.repository.UserRepository;
import lombok.*;
import org.json.simple.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@RestController
@RequiredArgsConstructor
public class userController {

    private final UserRepository userRepository;

    @ResponseBody
    @RequestMapping(value = "/user",method= RequestMethod.POST)//Sign Up
    public JSONObject post_user(HttpServletRequest request, @RequestBody JSONObject data) {
        JSONObject res = new JSONObject();
        HttpSession session = request.getSession(true);
        boolean success = false;
        System.out.println(data);
        if(userRepository.findUserByUserName((String)data.get("user_name")) instanceof User) {//user가 존재하면 fail return
            res.put("result",success);
            res.put("message","User Already Exist!");
            return res;
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(16);
        String pwd_encoded = passwordEncoder.encode((String)data.get("user_pwd"));
        User user = User.builder()
                .userName((String)data.get("user_name"))
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
    public JSONObject delete_user(HttpServletRequest request, @RequestBody String data) {
        JSONObject res = new JSONObject();
        HttpSession session = request.getSession(false);
        boolean success = false;
        String user_name = (String) session.getAttribute("user_name");
        if(user_name == "") {//로그인 정보가 비어있으면 리턴
            res.put("result",success);
            return res;
        }
        userRepository.deleteByUserName(user_name);
        session.invalidate();
        success = true;
        res.put("result",success);
        return res;
    }
    @ResponseBody
    @RequestMapping(value = "/session",method= RequestMethod.POST)//LogIn
    public JSONObject post_session(HttpServletRequest request, @RequestBody JSONObject data) {
        JSONObject res = new JSONObject();
        HttpSession session = request.getSession(true);
        System.out.println(data);
        boolean success = false;
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(16);
        //String pwd_encoded = passwordEncoder.encode((String)data.get("user_pwd"));
        User user = userRepository.findUserByUserName((String)data.get("user_name"));
        System.out.println(user);
        if (passwordEncoder.matches((String)data.get("user_pwd"),user.getUserPwd())) {
            session.setAttribute("user_name",user.getUserName());
            success = true;
            res.put("result",success);
        }
        return res;
    }

    @ResponseBody
    @RequestMapping(value = "/session",method= RequestMethod.DELETE)//LogOut
    public JSONObject delete_session(HttpServletRequest request) {
        JSONObject res = new JSONObject();
        HttpSession session = request.getSession(false);
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
