package com.guerra.hexagonal.application.config;


import com.guerra.hexagonal.application.adapters.out.FindAddressByZipCodeAdapter;
import com.guerra.hexagonal.application.adapters.out.InsertCustomerAdapter;
import com.guerra.hexagonal.application.core.usecase.InsertCustomerUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InsertCustomerConfig {


    @Bean
    public InsertCustomerUseCase insertCustomeruseCase(FindAddressByZipCodeAdapter findAddressByZipCodeAdapter, InsertCustomerAdapter insertCustomerAdapter){

        return  new InsertCustomerUseCase(findAddressByZipCodeAdapter, insertCustomerAdapter);

    }
}
