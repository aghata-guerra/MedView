package com.guerra.medview.application.core.usecase;

import com.guerra.medview.application.core.domain.Patient;
import com.guerra.medview.application.ports.in.InsertPatientInputPort;
import com.guerra.medview.application.ports.out.InsertPatientOutputPort;

public class InsertPatientUseCase implements InsertPatientInputPort {

    private final InsertPatientOutputPort insertPatientOutputPort;

    public InsertPatientUseCase(InsertPatientOutputPort insertPatientOutputPort) {
        this.insertPatientOutputPort = insertPatientOutputPort;
    }

    @Override
    public void insert(Patient patient) {
        insertPatientOutputPort.insert(patient);
    }
}
