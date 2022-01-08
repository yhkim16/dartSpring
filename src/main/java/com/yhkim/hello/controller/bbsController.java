package com.yhkim.hello.controller;

import com.yhkim.hello.dto.Article;
import com.yhkim.hello.repository.BBSRepository;
import com.yhkim.hello.service.bbsService;
import jdk.nashorn.internal.parser.JSONParser;
import lombok.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class bbsController {

    private final BBSRepository bbsRepository;

    @Autowired
    private final bbsService bbsService;

    @ResponseBody
    @RequestMapping(value = "/article/list",method= RequestMethod.GET)
    public JSONArray get_article_list() {
        JSONArray res = new JSONArray();
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Article> articles = bbsRepository.findAll(pageRequest);
        articles.forEach(e -> res.add(e));
        return res;
    }
    @ResponseBody
    @RequestMapping(value = "/article/list/{page_number}",method= RequestMethod.GET)
    public JSONArray get_article_list(@PathVariable("page_number") int page_number) {
        JSONArray res = new JSONArray();
        PageRequest pageRequest = PageRequest.of((page_number) * 10 , (page_number + 1) * 10);
        Page<Article> articles = bbsRepository.findAll(pageRequest);
        articles.forEach(e -> res.add(e));
        return res;
    }
    @ResponseBody
    @RequestMapping(value = "/article",method= RequestMethod.GET)
    public JSONArray get_article() {
        JSONArray res = new JSONArray();
        PageRequest pageRequest = PageRequest.of(0, 1);
        Page<Article> articles = bbsRepository.findAll(pageRequest);
        articles.forEach(e -> res.add(e));
        return res;
    }
    @ResponseBody
    @RequestMapping(value = "/article/{id}",method= RequestMethod.GET)
    public JSONObject get_article(@PathVariable("id") int id) {
        Article article = bbsRepository.findById(id).get();
        JSONObject res = new JSONObject();
        res.put("id", article.getId());
        res.put("title",article.getTitle());
        res.put("author",article.getAuthor());
        res.put("contents",article.getContents());
        res.put("created_date",article.getCreated_date());
        return res;
    }
    @ResponseBody
    @RequestMapping(value = "/article",method= RequestMethod.POST)
    public JSONObject post_article(@RequestBody Article article) {
        return bbsService.saveArticle(article);
    }
    @ResponseBody
    @RequestMapping(value = "/article",method= RequestMethod.POST, consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public JSONObject post_article_octet_stream(@RequestBody Article article) {
        return bbsService.saveArticle(article);
    }
    @ResponseBody
    @RequestMapping(value = "/article",method= RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public JSONObject post_article_json(@RequestBody Article article) {
        return bbsService.saveArticle(article);
    }
    @ResponseBody
    @RequestMapping(value = "/article",method= RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public JSONObject post_article_form(@RequestBody Article article) {
        return bbsService.saveArticle(article);
    }
    @ResponseBody
    @RequestMapping(value = "/article/{id}",method= RequestMethod.DELETE)
    public JSONObject delete_article(@PathVariable("id") int id) {
        JSONObject res = new JSONObject();
        boolean success = false;
        try {
            bbsRepository.deleteById(id);
            success = true;
        } catch (Exception e){
            e.printStackTrace();
            success = false;
        } finally {
            res.put("result",success);
        }
        return res;
    }

}
