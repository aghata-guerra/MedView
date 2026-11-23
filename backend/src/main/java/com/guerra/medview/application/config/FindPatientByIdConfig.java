package com.guerra.medview.application.config;

import com.guerra.medview.application.adapters.out.FindPatientByIdAdapter;
import com.guerra.medview.application.core.usecase.FindPatientByIdUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FindPatientByIdConfig {

    @Bean
    public FindPatientByIdUseCase findPatientByIdUseCase(FindPatientByIdAdapter findPatientByIdAdapter) {
        return new FindPatientByIdUseCase(findPatientByIdAdapter);
    }
}
