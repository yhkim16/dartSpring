package com.yhkim.hello.repository;

import com.yhkim.hello.dto.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BBSRepository extends JpaRepository<Article, Integer> {
    Page<Article> findAll(Pageable page_number);
    Page<Article> findArticlesByBoard(String board, Pageable page_number);
}
