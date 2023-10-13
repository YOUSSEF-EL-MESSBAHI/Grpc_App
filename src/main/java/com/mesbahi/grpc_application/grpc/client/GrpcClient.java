package com.mesbahi.grpc_application.grpc.client;

import com.mesbahi.grpc_application.grpc.stub.Bank;
import com.mesbahi.grpc_application.grpc.stub.BankServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;

public class GrpcClient {
    public static void main(String[] args) throws IOException {
        ManagedChannel managedChannel= ManagedChannelBuilder.forAddress("localhost",9999)
                .usePlaintext()
                .build();
        BankServiceGrpc.BankServiceBlockingStub blockingStub= BankServiceGrpc.newBlockingStub(managedChannel);
        Bank.ConvertCurrencyRequest.Builder builder = Bank.ConvertCurrencyRequest.newBuilder();
        builder.setCurrencyFrom("USD");
        builder.setCurrencyTo("MAD");
        builder.setAmount(98000);
        Bank.ConvertCurrencyRequest currencyRequest = builder.build();
        Bank.ConvertCurrencyResponse convertCurrencyResponse = blockingStub.convertCurrency(currencyRequest);
        System.out.println("*************************");
        System.out.println(String.format("%f en %s => %f en %s",
                convertCurrencyResponse.getAmount(),convertCurrencyResponse.getCurrencyFrom(),
                convertCurrencyResponse.getConversionResult(),convertCurrencyResponse.getCurrencyTo()));
        System.out.println("************************");

        BankServiceGrpc.BankServiceStub bankServiceStub=BankServiceGrpc.newStub(managedChannel);
        bankServiceStub.convertCurrency(currencyRequest, new StreamObserver<Bank.ConvertCurrencyResponse>() {
            @Override
            public void onNext(Bank.ConvertCurrencyResponse response) {
                System.out.println("===========================");
                System.out.println(String.format("%f en %s => %f en %s",
                        response.getAmount(),response.getCurrencyFrom(),
                        response.getConversionResult(),response.getCurrencyTo()));
                System.out.println("===============================");
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println(throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Exchange end");
            }
        });
        System.in.read();
    }
}
