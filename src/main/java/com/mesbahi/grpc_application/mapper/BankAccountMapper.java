package com.mesbahi.grpc_application.mapper;

import com.mesbahi.grpc_application.entity.Account;
import com.mesbahi.grpc_application.enums.AccountStatus;
import com.mesbahi.grpc_application.enums.AccountType;
import com.mesbahi.grpc_application.grpc.stub.Bank;
import org.springframework.stereotype.Service;


@Service
public class BankAccountMapper {
    public Bank.BankAccount fromBankCount(Account account){
        Bank.BankAccount bankAccount= Bank.BankAccount.newBuilder()
                .setAccountId(account.getId())
                .setBalance(account.getBalance())
                .setCreatedAt(account.getCreateAt())
                .setType(Bank.AccountType.valueOf(account.getType().toString()))
                .setState(Bank.AccountState.valueOf(account.getStatus().toString()))
                .build();
        return bankAccount;
    }
    public Account fromGrpcAccount(Bank.BankAccount bankAccount){
        Account account=new Account();
        account.setId(bankAccount.getAccountId());
        account.setBalance(bankAccount.getBalance());
        account.setCreateAt(bankAccount.getCreatedAt());
        account.setType(AccountType.valueOf(bankAccount.getType().toString()));
        account.setStatus(AccountStatus.valueOf(bankAccount.getState().toString()));
        return account;
    }
}
