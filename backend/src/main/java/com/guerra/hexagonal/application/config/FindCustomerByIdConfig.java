package com.guerra.hexagonal.application.config;

import com.guerra.hexagonal.application.adapters.out.FindCustomerByIdAdapter;
import com.guerra.hexagonal.application.core.usecase.FindCustomerByIdUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FindCustomerByIdConfig {


    @Bean
    public FindCustomerByIdUseCase findCustomerByIdUseCase(FindCustomerByIdAdapter findCustomerByIdAdapter){

        return  new FindCustomerByIdUseCase(findCustomerByIdAdapter);

    }
}
