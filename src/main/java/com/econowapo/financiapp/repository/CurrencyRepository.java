package com.econowapo.financiapp.repository;

import com.econowapo.financiapp.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {

}
