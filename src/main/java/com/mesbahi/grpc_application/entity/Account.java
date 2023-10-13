package com.mesbahi.grpc_application.entity;

import com.mesbahi.grpc_application.enums.AccountStatus;
import com.mesbahi.grpc_application.enums.AccountType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Account {
    @Id
    private String id;
    private double balance;
    private long createAt;
    @Enumerated(EnumType.STRING)
    private AccountType type;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    @ManyToOne
    private Currency currency;
}
