package com.guerra.medview.application.config;

import com.guerra.medview.application.adapters.out.DeletePatientByIdAdapter;
import com.guerra.medview.application.core.usecase.DeletePatientByIdUseCase;
import com.guerra.medview.application.core.usecase.FindPatientByIdUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeletePatientByIdConfig {

    @Bean
    public DeletePatientByIdUseCase deletePatientByIdUseCase(FindPatientByIdUseCase findPatientByIdUseCase,
                                                             DeletePatientByIdAdapter deletePatientByIdAdapter) {
        return new DeletePatientByIdUseCase(findPatientByIdUseCase, deletePatientByIdAdapter);
    }
}
