package com.ziya.moneymanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MoneyManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoneyManagementApplication.class, args);
    }

}
