package com.guerra.medview.application.config;

import com.guerra.medview.application.adapters.out.FindAddressByZipCodeAdapter;
import com.guerra.medview.application.adapters.out.FindPatientByIdAdapter;
import com.guerra.medview.application.adapters.out.UpdatePatientAdapter;
import com.guerra.medview.application.core.usecase.FindPatientByIdUseCase;
import com.guerra.medview.application.core.usecase.UpdatePatientUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UpdatePatientConfig {

    @Bean
    public UpdatePatientUseCase updateCustomerUseCase(FindPatientByIdAdapter findPatientByIdAdapter, FindPatientByIdUseCase findPatientByIdUseCase, FindAddressByZipCodeAdapter findAddressByZipCodeAdapter, UpdatePatientAdapter updatePatientAdapter){

        return  new UpdatePatientUseCase(findPatientByIdUseCase, findAddressByZipCodeAdapter, updatePatientAdapter);
    }
}
