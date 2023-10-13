package com.mesbahi.grpc_application.repository;

import com.mesbahi.grpc_application.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepo extends JpaRepository<Currency, Long> {
    Currency findByName(String name);
}
