package com.econowapo.financiapp.service;

import com.econowapo.financiapp.model.CartLineInfo;
import com.econowapo.financiapp.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderService {
    Page<Order> getAllOrdersByCustomerId(Long customerId, Pageable pageable);
    Order getOrderByIdAndCustomerId(Long customerId, Long orderId);
    Order createOrder(Long customerId,Order order);
    Order updateOrder(Long customerId,Long orderId, Order orderDetails);
    ResponseEntity<?> deleteOrder(Long customerId, Long orderId);
    List<Order> getAllOrders();

    //This create the OrderDetails of the Order
    Order assignOrderArticle(Long orderId, List<CartLineInfo> info);

    //This create a CreditAccountMovement
    Order assignOrderCreditAccount(Long orderId);

}
