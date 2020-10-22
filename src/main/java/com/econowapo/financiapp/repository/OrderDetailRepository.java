package com.econowapo.financiapp.repository;

import com.econowapo.financiapp.model.Order_Detail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<Order_Detail, Long > {
    Page<Order_Detail> findByOrderId(Long orderId, Pageable pageable);
}
