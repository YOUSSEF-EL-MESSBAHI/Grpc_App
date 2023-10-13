package com.mesbahi.grpc_application.repository;

import com.mesbahi.grpc_application.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepo extends JpaRepository<Account, String> {
}
