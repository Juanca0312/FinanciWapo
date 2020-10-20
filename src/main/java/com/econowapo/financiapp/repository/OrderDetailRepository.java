package com.econowapo.financiapp.repository;

import com.econowapo.financiapp.model.Order_Detail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<Order_Detail, Long > {

}
