package com.guerra.medview.application.core.usecase;

import com.guerra.medview.application.core.domain.Patient;
import com.guerra.medview.application.ports.in.RegisterPatientInputPort;
import com.guerra.medview.application.ports.out.RegisterPatientOutputPort;

public class RegisterPatientUseCase implements RegisterPatientInputPort {

    private final RegisterPatientOutputPort registerPatientOutputPort;

    public RegisterPatientUseCase(RegisterPatientOutputPort registerPatientOutputPort) {
        this.registerPatientOutputPort = registerPatientOutputPort;
    }

    @Override
    public Patient register(Patient patient) {
        return registerPatientOutputPort.register(patient);
    }
}
