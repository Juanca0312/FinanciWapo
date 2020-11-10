package com.econowapo.financiapp.service;

import com.econowapo.financiapp.exception.ResourceNotFoundException;
import com.econowapo.financiapp.model.Article;
import com.econowapo.financiapp.model.CartLineInfo;
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
    public List<CartLineInfo> getAllArticlesByOrderId(Long orderId) {
        List<Order_Detail> order_details = orderDetailRepository.findByOrderId(orderId);
        List<CartLineInfo> cartLineInfos = new ArrayList<>();
        for (Order_Detail od: order_details) {
            CartLineInfo info = new CartLineInfo();
            Article article = od.getArticle();

            info.setName(article.getName());
            long e=od.getId();
            String j=String.valueOf(e);
            info.setId(j);
            double price = article.getPrice();
            String priceS = String.valueOf(price);
            info.setPrice(priceS);
            int i=od.getQuantity();
            String s=String.valueOf(i);
            info.setQuantity(s);


            cartLineInfos.add(info);

        }


        return cartLineInfos;


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
