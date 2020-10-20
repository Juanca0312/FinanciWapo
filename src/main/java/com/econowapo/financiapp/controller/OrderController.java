package com.econowapo.financiapp.controller;

import com.econowapo.financiapp.model.CartLineInfo;
import com.econowapo.financiapp.model.Order;
import com.econowapo.financiapp.resource.OrderResource;
import com.econowapo.financiapp.resource.SaveOrderResource;
import com.econowapo.financiapp.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private OrderService orderService;

    @GetMapping("/orders")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/customers/{customerId}/orders")
    public Page<OrderResource> getAllOrdersByCustomerId(
            @PathVariable(name = "customerId") Long customerId,
            Pageable pageable) {
        Page<Order> orderPage = orderService.getAllOrdersByCustomerId(customerId, pageable);
        List<OrderResource> resources = orderPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(resources, pageable, resources.size());
    }

    @GetMapping("/customers/{customerId}/orders/{orderId}")
    public OrderResource getOrderByIdAndCustomerId(@PathVariable(name = "customerId") Long customerId,
                                                     @PathVariable(name = "orderId") Long orderId) {
        return convertToResource(orderService.getOrderByIdAndCustomerId(customerId, orderId));
    }

    @PostMapping("/customers/{customerId}/orders")
    public OrderResource createOrder(@PathVariable(name = "customerId") Long customerId,
                                           @Valid @RequestBody SaveOrderResource resource) {
        return convertToResource(orderService.createOrder(customerId, convertToEntity(resource)));
    }

    @PostMapping("/orders/{orderId}/articles")
    public OrderResource ShoppingCartInfo(@PathVariable(name = "orderId") Long orderId,
            @Valid @RequestBody List<CartLineInfo> resource) {
        return convertToResource(orderService.assignOrderArticle(orderId, resource));
        //List<CartLineInfo> resourcess = resource;
        //return new ArrayList<>(resourcess);
    }

    @PutMapping("/customers/{customerId}/orders/{orderId}")
    public OrderResource updateOrder(@PathVariable(name = "customerId") Long customerId,
                                           @PathVariable(name = "orderId") Long orderId,
                                           @Valid @RequestBody SaveOrderResource resource) {
        return convertToResource(orderService.updateOrder(customerId, orderId, convertToEntity(resource)));
    }

    @DeleteMapping("/customers/{customerId}/orders/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable(name = "customerId") Long customerId,
                                            @PathVariable(name = "orderId") Long orderId) {
        return orderService.deleteOrder(customerId, orderId);
    }



    private Order convertToEntity(SaveOrderResource resource) {
        return mapper.map(resource, Order.class);
    }


    private OrderResource convertToResource(Order entity) {
        return mapper.map(entity, OrderResource.class);
    }

}
