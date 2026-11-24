package com.guerra.medview.application.config;

import com.guerra.medview.application.adapters.out.UpdatePatientAdapter;
import com.guerra.medview.application.core.usecase.FindPatientByIdUseCase;
import com.guerra.medview.application.core.usecase.UpdatePatientUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UpdatePatientConfig {

    @Bean
    public UpdatePatientUseCase updatePatientUseCase(FindPatientByIdUseCase findPatientByIdUseCase,
                                                     UpdatePatientAdapter updatePatientAdapter) {
        return new UpdatePatientUseCase(findPatientByIdUseCase, updatePatientAdapter);
    }
}
