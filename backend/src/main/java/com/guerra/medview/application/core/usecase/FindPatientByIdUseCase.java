package com.guerra.medview.application.core.usecase;

import com.guerra.medview.application.core.domain.Patient;
import com.guerra.medview.application.core.exception.PatientNotFoundException;
import com.guerra.medview.application.ports.in.FindPatientByIdInputPort;
import com.guerra.medview.application.ports.out.FindPatientByIdOutputPort;

public class FindPatientByIdUseCase implements FindPatientByIdInputPort {

    private final FindPatientByIdOutputPort findPatientByIdOutputPort;

    public FindPatientByIdUseCase(FindPatientByIdOutputPort findPatientByIdOutputPort) {
        this.findPatientByIdOutputPort = findPatientByIdOutputPort;
    }

    @Override
    public Patient find(Long id) {
        return findPatientByIdOutputPort.find(id)
                .orElseThrow(() -> new PatientNotFoundException(id));
    }
}
