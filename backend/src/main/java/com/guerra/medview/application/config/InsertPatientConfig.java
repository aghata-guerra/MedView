package com.guerra.medview.application.config;

import com.guerra.medview.application.adapters.out.InsertPatientAdapter;
import com.guerra.medview.application.core.usecase.InsertPatientUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InsertPatientConfig {

    @Bean
    public InsertPatientUseCase insertPatientUseCase(InsertPatientAdapter insertPatientAdapter) {
        return new InsertPatientUseCase(insertPatientAdapter);
    }
}
