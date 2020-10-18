package com.econowapo.financiapp.service;

import com.econowapo.financiapp.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface ArticleService {
    Page<Article> getAllArticles(Pageable pageable);
    Article getArticleById(Long articleId);
    Article createArticle(Article article);
    Article updateArticle(Long articleId, Article articleRequest);
    ResponseEntity<?> deleteArticle(Long articleId);
}
