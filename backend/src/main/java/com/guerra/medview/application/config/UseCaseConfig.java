package com.guerra.medview.application.config;

import com.guerra.medview.application.core.usecase.DeletePatientByIdUseCase;
import com.guerra.medview.application.core.usecase.FindPatientByIdUseCase;
import com.guerra.medview.application.core.usecase.RegisterPatientUseCase;
import com.guerra.medview.application.core.usecase.UpdatePatientUseCase;
import com.guerra.medview.application.ports.in.DeletePatientByIdInputPort;
import com.guerra.medview.application.ports.in.FindPatientByIdInputPort;
import com.guerra.medview.application.ports.in.RegisterPatientInputPort;
import com.guerra.medview.application.ports.in.UpdatePatientInputPort;
import com.guerra.medview.application.ports.out.DeletePatientByIdOutputPort;
import com.guerra.medview.application.ports.out.FindPatientByIdOutputPort;
import com.guerra.medview.application.ports.out.RegisterPatientOutputPort;
import com.guerra.medview.application.ports.out.UpdatePatientOutputPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Wires each use case (plain Java, no Spring inside the core) to the output
 * ports implemented by the adapters.
 */
@Configuration
public class UseCaseConfig {

    @Bean
    public RegisterPatientInputPort registerPatientUseCase(RegisterPatientOutputPort registerPatientOutputPort) {
        return new RegisterPatientUseCase(registerPatientOutputPort);
    }

    @Bean
    public FindPatientByIdInputPort findPatientByIdUseCase(FindPatientByIdOutputPort findPatientByIdOutputPort) {
        return new FindPatientByIdUseCase(findPatientByIdOutputPort);
    }

    @Bean
    public UpdatePatientInputPort updatePatientUseCase(FindPatientByIdInputPort findPatientByIdInputPort,
                                                       UpdatePatientOutputPort updatePatientOutputPort) {
        return new UpdatePatientUseCase(findPatientByIdInputPort, updatePatientOutputPort);
    }

    @Bean
    public DeletePatientByIdInputPort deletePatientByIdUseCase(FindPatientByIdInputPort findPatientByIdInputPort,
                                                               DeletePatientByIdOutputPort deletePatientByIdOutputPort) {
        return new DeletePatientByIdUseCase(findPatientByIdInputPort, deletePatientByIdOutputPort);
    }
}
