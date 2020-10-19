package com.econowapo.financiapp.service;

import com.econowapo.financiapp.exception.ResourceNotFoundException;
import com.econowapo.financiapp.model.Order;
import com.econowapo.financiapp.repository.CustomerRepository;
import com.econowapo.financiapp.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

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
        if(!customerRepository.existsById(customerId))
            throw new ResourceNotFoundException("Customer" ,"Id", customerId);

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
}
