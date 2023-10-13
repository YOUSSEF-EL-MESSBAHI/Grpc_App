package com.mesbahi.grpc_application.grpc.service;

import com.mesbahi.grpc_application.entity.Account;
import com.mesbahi.grpc_application.entity.Currency;
import com.mesbahi.grpc_application.grpc.stub.Bank;
import com.mesbahi.grpc_application.grpc.stub.BankServiceGrpc;
import com.mesbahi.grpc_application.mapper.BankAccountMapper;
import com.mesbahi.grpc_application.repository.AccountRepo;
import com.mesbahi.grpc_application.repository.CurrencyRepo;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@GrpcService
public class BankGrpcServiceImpl extends BankServiceGrpc.BankServiceImplBase {
    @Autowired
    private CurrencyRepo currencyRepository;
    @Autowired
    private AccountRepo accountRepository;
    @Autowired
    private BankAccountMapper accountMapper;

    @Override
    public void getBankAccount(Bank.GetBankAccountRequest request, StreamObserver<Bank.GetBankAccountResponse> responseObserver) {
        String accountId=request.getAccountId();
        Account account = accountRepository.findById(accountId).orElse(null);
        if(account!=null){
            Bank.BankAccount bankAccount=accountMapper.fromBankCount(account);
            Bank.GetBankAccountResponse accountResponse= Bank.GetBankAccountResponse.newBuilder()
                    .setBankAccount(bankAccount)
                    .build();
            responseObserver.onNext(accountResponse);
            responseObserver.onCompleted();
        }
    }

    @Override
    public void getListAccounts(Bank.GetListAccountsRequest request, StreamObserver<Bank.GetListAccountsResponse> responseObserver) {
        List<Account> accountList=accountRepository.findAll();
        List<Bank.BankAccount> grpcAccountList = accountList.stream().map(account -> accountMapper.fromBankCount(account)).collect(Collectors.toList());
        Bank.GetListAccountsResponse listAccountsResponse= Bank.GetListAccountsResponse.newBuilder()
                .addAllBankAccount(grpcAccountList)
                .build();
        responseObserver.onNext(listAccountsResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void convertCurrency(Bank.ConvertCurrencyRequest request, StreamObserver<Bank.ConvertCurrencyResponse> responseObserver) {
        String from=request.getCurrencyFrom();
        String to=request.getCurrencyTo();
        double amount=request.getAmount();
        Currency currencyFrom=currencyRepository.findByName(from);
        Currency currencyTo=currencyRepository.findByName(to);
        double result=amount*currencyFrom.getPrice()/currencyTo.getPrice();
        Bank.ConvertCurrencyResponse convertCurrencyResponse= Bank.ConvertCurrencyResponse.newBuilder()
                .setCurrencyFrom(from)
                .setCurrencyTo(to)
                .setAmount(amount)
                .setConversionResult(result)
                .build();
        responseObserver.onNext(convertCurrencyResponse);
        responseObserver.onCompleted();
    }
}
