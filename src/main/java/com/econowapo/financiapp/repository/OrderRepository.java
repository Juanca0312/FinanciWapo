package com.econowapo.financiapp.repository;

import com.econowapo.financiapp.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findByCustomerId(Long customerId, Pageable pageable);
    List<Order> findByCustomerId(Long customerId);
    Optional<Order> findByIdAndCustomerId(Long id, Long customerId);
}
