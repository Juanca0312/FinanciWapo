package com.econowapo.financiapp.repository;

import com.econowapo.financiapp.model.Authority;
import com.econowapo.financiapp.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Authority findByUserId(Long userId);
}
