package com.guerra.hexagonal.application.config;

import com.guerra.hexagonal.application.adapters.out.FindAddressByZipCodeAdapter;
import com.guerra.hexagonal.application.adapters.out.FindCustomerByIdAdapter;
import com.guerra.hexagonal.application.adapters.out.UpdateCustomerAdapter;
import com.guerra.hexagonal.application.core.usecase.FindCustomerByIdUseCase;
import com.guerra.hexagonal.application.core.usecase.UpdateCustomerUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UpdateCustomerConfig {

    @Bean
    public UpdateCustomerUseCase updateCustomerUseCase(FindCustomerByIdAdapter findCustomerByIdAdapter, FindCustomerByIdUseCase findCustomerByIdUseCase, FindAddressByZipCodeAdapter findAddressByZipCodeAdapter, UpdateCustomerAdapter updateCustomerAdapter){

        return  new UpdateCustomerUseCase(findCustomerByIdUseCase, findAddressByZipCodeAdapter, updateCustomerAdapter);
    }
}
