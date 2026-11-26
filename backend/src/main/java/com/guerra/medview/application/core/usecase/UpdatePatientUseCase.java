package com.guerra.medview.application.core.usecase;

import com.guerra.medview.application.core.domain.Patient;
import com.guerra.medview.application.ports.in.FindPatientByIdInputPort;
import com.guerra.medview.application.ports.in.UpdatePatientInputPort;
import com.guerra.medview.application.ports.out.UpdatePatientOutputPort;

public class UpdatePatientUseCase implements UpdatePatientInputPort {

    private final FindPatientByIdInputPort findPatientByIdInputPort;
    private final UpdatePatientOutputPort updatePatientOutputPort;

    public UpdatePatientUseCase(FindPatientByIdInputPort findPatientByIdInputPort,
                                UpdatePatientOutputPort updatePatientOutputPort) {
        this.findPatientByIdInputPort = findPatientByIdInputPort;
        this.updatePatientOutputPort = updatePatientOutputPort;
    }

    @Override
    public void update(Patient patient) {
        // Fails with PatientNotFoundException when the patient does not exist.
        findPatientByIdInputPort.find(patient.getId());
        updatePatientOutputPort.update(patient);
    }
}
