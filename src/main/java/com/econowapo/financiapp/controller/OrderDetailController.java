package com.econowapo.financiapp.controller;

import com.econowapo.financiapp.model.Article;
import com.econowapo.financiapp.model.Order_Detail;
import com.econowapo.financiapp.resource.ArticleResource;
import com.econowapo.financiapp.resource.SaveArticleResource;
import com.econowapo.financiapp.service.ArticleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping(value = "/api")
public class OrderDetailController {
    @Autowired
    private ModelMapper mapper;

    //@Autowired
    //private OrderDetailService;



    //private Article convertToEntity(SaveArticleResource resource) { return mapper.map(resource, Internship.class); }

    //private InternshipResource convertToResource(Internship entity) { return mapper.map(entity, InternshipResource.class); }

}
