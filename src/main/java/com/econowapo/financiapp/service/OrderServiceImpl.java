package com.econowapo.financiapp.service;

import com.econowapo.financiapp.exception.ResourceNotFoundException;
import com.econowapo.financiapp.model.*;
import com.econowapo.financiapp.repository.*;
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

    @Autowired
    private CreditAccountRepository creditAccountRepository;

    @Autowired
    private CreditAccountMovementRepository creditAccountMovementRepository;




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
    public List<OrderInfo> getUnpaidOrders(Long customerId) {
        List<Order> orders = orderRepository.findByCustomerId(customerId);
        List<OrderInfo> unpaidOrders = new ArrayList<>();
        for(Order o: orders){
            List<CreditAccountMovement> creditAccountMovement = o.getCreditAccountMovements();
            if(creditAccountMovement.get(0).getState() == 1){
                OrderInfo orderInfo = new OrderInfo();
                orderInfo.setTotal_amount(o.getTotal_amount());
                orderInfo.setGenerated_date(o.getGenerated_date());
                orderInfo.setAccepted_date(o.getAccepted_date());
                orderInfo.setPayment_method(o.getPayment_method());
                orderInfo.setOrderId(o.getId());
                orderInfo.setState(o.getState());
                unpaidOrders.add(orderInfo);
            }

        }
        return unpaidOrders;
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
    public List<OrderInfo> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        List<OrderInfo> orderInfos = new ArrayList<>();
        for(Order o : orders){
            OrderInfo info = new OrderInfo();
            info.setOrderId(o.getId());
            info.setAccepted_date(o.getAccepted_date());
            info.setGenerated_date(o.getGenerated_date());
            info.setPayment_method(o.getPayment_method());
            info.setState(o.getState());
            info.setTotal_amount(o.getTotal_amount());
            Customer customer = o.getCustomer();
            info.setCustomerName(customer.getName());
            orderInfos.add(info);

        }
        return orderInfos;
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

    @Override
    public Order assignOrderCreditAccount(Long orderId) {
        //crea un movimiento, resta el saldo actual el importe

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "Id", orderId));
        Long customerId = order.getCustomer().getId();
        CreditAccount creditAccount = creditAccountRepository.findByCustomerId(customerId);

        CreditAccountMovement creditAccountMovement = new CreditAccountMovement();
        //sacamos el amount de orderDetail
        List<Order_Detail> orderDetails = orderDetailRepository.findByOrderId(orderId);
        double amount2 = 0;
        for (Order_Detail od: orderDetails) {
            if(od.getQuantity() > 1) {
                amount2 += od.getArticle().getPrice() * od.getQuantity();
            }
            else {
                amount2 += od.getArticle().getPrice();
            }

        }

        if(creditAccount.getActual_balance() < amount2){
            //No pagado
            order.setState(2);
            orderRepository.save(order);
            Order orderNull = new Order();
            return orderNull;
        }
        creditAccountMovement.setAmount(amount2);

        //restamos el actual balance con el precio de la orden
        creditAccount.setActual_balance(creditAccount.getActual_balance() - amount2);

        creditAccountRepository.save(creditAccount);

        //Creamos el CreditAccountMovement
        creditAccountMovement.setCreditAccount(creditAccount);
        creditAccountMovement.setOrder(order);
        creditAccountMovement.setGenerated_date(order.getGenerated_date());
        creditAccountMovement.setState(1);
        order.getCreditAccountMovements().add(creditAccountMovement);
        order.setTotal_amount(amount2);
        orderRepository.save(order);
        creditAccount.getCreditAccountMovements().add(creditAccountMovement);
        creditAccountMovementRepository.save(creditAccountMovement);

        return order;


    }


}
