package com.econowapo.financiapp.service;

import com.econowapo.financiapp.exception.ResourceNotFoundException;
import com.econowapo.financiapp.model.Article;
import com.econowapo.financiapp.model.Order_Detail;
import com.econowapo.financiapp.repository.ArticleRepository;
import com.econowapo.financiapp.repository.OrderDetailRepository;
import com.econowapo.financiapp.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService{

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public Page<Article> getAllArticles(Pageable pageable) {
            return articleRepository.findAll(pageable);
    }

    @Override
    public Article getArticleById(Long articleId) {
        return articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article", "Id", articleId));
    }

    @Override
    public Page<Article> getAllArticlesByOrderId(Long orderId, Pageable pageable) {
        Page<Order_Detail> order_detailsPage = orderDetailRepository.findByOrderId(orderId, pageable);
        List<Order_Detail> order_details = order_detailsPage.toList();
        List<Article> articles = new ArrayList<>();
        for (Order_Detail od: order_details) {
            articles.add(od.getArticle());
        }
        int articlesCount = articles.size();

        return new PageImpl<>(articles, pageable, articlesCount);


    }

    @Override
    public Article createArticle(Article article) {
        return articleRepository.save(article);
    }

    @Override
    public Article updateArticle(Long articleId, Article articleRequest) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article", "Id", articleId));
        article.setDescription(articleRequest.getDescription());
        article.setState(articleRequest.getState());
        article.setName(articleRequest.getName());
        article.setPrice(articleRequest.getPrice());
        article.setUnit(articleRequest.getUnit());
        return articleRepository.save(article);


    }

    @Override
    public ResponseEntity<?> deleteArticle(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article", "Id", articleId));
        articleRepository.delete(article);
        return ResponseEntity.ok().build();
    }
}
