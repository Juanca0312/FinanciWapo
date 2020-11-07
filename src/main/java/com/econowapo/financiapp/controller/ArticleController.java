package com.econowapo.financiapp.controller;

import com.econowapo.financiapp.model.Article;
import com.econowapo.financiapp.model.CartLineInfo;
import com.econowapo.financiapp.resource.ArticleResource;
import com.econowapo.financiapp.resource.SaveArticleResource;
import com.econowapo.financiapp.service.ArticleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ArticleController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ArticleService articleService;

    @GetMapping("/articles")
    public Page<ArticleResource> getAllUsers(Pageable pageable){
        Page<Article> articlePage = articleService.getAllArticles(pageable);
        List<ArticleResource> resources = articlePage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(resources, pageable, resources.size());
    }

    @GetMapping("/articles/{id}")
    public ArticleResource getArticleById(@PathVariable(name = "id") Long articleId){
        return convertToResource(articleService.getArticleById(articleId));
    }

    @GetMapping("/orders/{orderId}/articles")
    public List<CartLineInfo> getAllArticlesByOrderId(@PathVariable(name = "orderId") Long orderId) {
        return articleService.getAllArticlesByOrderId(orderId);
    }

    @PostMapping("/articles")
    public ArticleResource createArticle(@Valid @RequestBody SaveArticleResource resource)  {
        Article article = convertToEntity(resource);
        return convertToResource(articleService.createArticle(article));
    }

    @PutMapping("/articles/{id}")
    public ArticleResource updateArticle(@PathVariable(name = "id") Long articleId, @Valid @RequestBody SaveArticleResource resource) {
        Article article = convertToEntity(resource);
        return convertToResource(articleService.updateArticle(articleId, article));
    }


    @DeleteMapping("/articles/{id}")
    public ResponseEntity<?> deleteArticle(@PathVariable(name = "id") Long articleId) {
        return articleService.deleteArticle(articleId);
    }

    private Article convertToEntity(SaveArticleResource resource) { return mapper.map(resource, Article.class); }

    private ArticleResource convertToResource(Article entity) { return mapper.map(entity, ArticleResource.class); }

}
