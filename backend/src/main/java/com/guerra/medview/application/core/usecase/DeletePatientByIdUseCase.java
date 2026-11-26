package com.guerra.medview.application.core.usecase;

import com.guerra.medview.application.ports.in.DeletePatientByIdInputPort;
import com.guerra.medview.application.ports.in.FindPatientByIdInputPort;
import com.guerra.medview.application.ports.out.DeletePatientByIdOutputPort;

public class DeletePatientByIdUseCase implements DeletePatientByIdInputPort {

    private final FindPatientByIdInputPort findPatientByIdInputPort;
    private final DeletePatientByIdOutputPort deletePatientByIdOutputPort;

    public DeletePatientByIdUseCase(FindPatientByIdInputPort findPatientByIdInputPort,
                                    DeletePatientByIdOutputPort deletePatientByIdOutputPort) {
        this.findPatientByIdInputPort = findPatientByIdInputPort;
        this.deletePatientByIdOutputPort = deletePatientByIdOutputPort;
    }

    @Override
    public void delete(Long id) {
        // Fails with PatientNotFoundException when the patient does not exist.
        findPatientByIdInputPort.find(id);
        deletePatientByIdOutputPort.delete(id);
    }
}
