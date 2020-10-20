package com.econowapo.financiapp.service;

import com.econowapo.financiapp.exception.ResourceNotFoundException;
import com.econowapo.financiapp.model.Article;
import com.econowapo.financiapp.model.CartLineInfo;
import com.econowapo.financiapp.model.Order;
import com.econowapo.financiapp.model.Order_Detail;
import com.econowapo.financiapp.repository.ArticleRepository;
import com.econowapo.financiapp.repository.CustomerRepository;
import com.econowapo.financiapp.repository.OrderDetailRepository;
import com.econowapo.financiapp.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;



    @Override
    public Page<Order> getAllOrdersByCustomerId(Long customerId, Pageable pageable) {
        return orderRepository.findByCustomerId(customerId, pageable);
    }

    @Override
    public Order getOrderByIdAndCustomerId(Long customerId, Long orderId) {
        return orderRepository.findByIdAndCustomerId(orderId, customerId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Order not found with Id" + orderId + " and CustomerId " + customerId
                ));
    }


    @Override
    public Order createOrder(Long customerId, Order order) {
        return customerRepository.findById(customerId).map(customer -> {
            order.setCustomer(customer);
            return orderRepository.save(order);
        }).orElseThrow(() -> new ResourceNotFoundException(
                "CustomerId", "Id", customerId
        ));
    }

    @Override
    public Order updateOrder(Long customerId, Long orderId, Order orderDetails) {
        if (!customerRepository.existsById(customerId))
            throw new ResourceNotFoundException("Customer", "Id", customerId);

        return orderRepository.findById(orderId).map(order -> {
            order.setState(orderDetails.getState());
            order.setAccepted_date(orderDetails.getAccepted_date());
            order.setGenerated_date(orderDetails.getGenerated_date());
            order.setPayment_method(orderDetails.getPayment_method());
            return orderRepository.save(order);
        }).orElseThrow(() -> new ResourceNotFoundException("Order", "Id", orderId));
    }

    @Override
    public ResponseEntity<?> deleteOrder(Long customerId, Long orderId) {
        return orderRepository.findByIdAndCustomerId(orderId, customerId).map(order -> {
            orderRepository.delete(order);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(
                "Order not found with Id" + orderId + " and CustomerId " + customerId
        ));
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order assignOrderArticle(Long orderId, List<CartLineInfo> info) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "Id", orderId));

        for (CartLineInfo x : info) {
            String id = x.getId();
            long Id = Long.parseLong(id);
            Article article = articleRepository.findById(Id)
                    .orElseThrow(() -> new ResourceNotFoundException("Article", "Id",Id));
            Order_Detail od = new Order_Detail();
            od.setArticle(article);
            od.setOrder(order);
            int q = Integer.parseInt(x.getQuantity());
            od.setQuantity(q);

            article.getOrder_details().add(od);
            order.getOrder_details().add(od);

            orderDetailRepository.save(od);

        }

        return order;
    }


}
