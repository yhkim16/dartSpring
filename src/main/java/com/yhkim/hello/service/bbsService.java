package com.yhkim.hello.service;

import com.yhkim.hello.dto.Article;
import com.yhkim.hello.repository.BBSRepository;
import lombok.*;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class bbsService {

    private final BBSRepository bbsRepository;

    public JSONObject saveArticle(Article article) {
        JSONObject res = new JSONObject();
        boolean success = false;
        try {
            bbsRepository.save(article);
            success = true;
        }
        catch (Exception e) {
            e.printStackTrace();
            success = false;
        }
        finally {
            res.put("result",success);
        }
        return res;
    }
}
