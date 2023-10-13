package com.mesbahi.grpc_application;

import com.mesbahi.grpc_application.entity.Account;
import com.mesbahi.grpc_application.entity.Currency;
import com.mesbahi.grpc_application.enums.AccountStatus;
import com.mesbahi.grpc_application.enums.AccountType;
import com.mesbahi.grpc_application.repository.AccountRepo;
import com.mesbahi.grpc_application.repository.CurrencyRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Random;

@SpringBootApplication
public class GrpcApplication {

    public static void main(String[] args) {
        SpringApplication.run(GrpcApplication.class, args);
    }

    @Bean
    CommandLineRunner start(CurrencyRepo currencyRepository, AccountRepo accountRepository){
        return args -> {
            //currencyRepository.save(new Currency(null,"USD","$",1));
            currencyRepository.save(Currency.builder().name("USD").symbol("$").price(1).build());
            currencyRepository.save(Currency.builder().name("MAD").symbol("DH").price(0.1).build());
            currencyRepository.save(Currency.builder().name("EUR").symbol("EUR").price(0.98).build());
            currencyRepository.findAll().forEach(currency -> {
                System.out.println(currency.toString());
            });
            List<Currency> currencyList=currencyRepository.findAll();
            for (int i = 1; i <10 ; i++) {
                Account account= Account.builder()
                        .id("CC"+i)
                        .currency(currencyList.get(new Random().nextInt(currencyList.size())))
                        .createAt(System.currentTimeMillis())
                        .balance(Math.random()*9000000)
                        .type(Math.random()>0.5? AccountType.CURRENT_ACCOUNT:AccountType.SAVING_ACCOUNT)
                        .status(AccountStatus.CREATED)
                        .build();
                accountRepository.save(account);
            }
        };
    }

}
