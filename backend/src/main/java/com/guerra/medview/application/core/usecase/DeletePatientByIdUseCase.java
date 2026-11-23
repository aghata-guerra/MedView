package com.guerra.medview.application.core.usecase;

import com.guerra.medview.application.ports.in.DeletePatientByIdInputPort;
import com.guerra.medview.application.ports.in.FindPatientByIdInputPort;

public class DeletePatientByIdUseCase implements DeletePatientByIdInputPort {

    private final FindPatientByIdInputPort findPatientByIdInputPort;

    private  final DeletePatientByIdUseCase deletePatientByIdUseCase;

    public DeletePatientByIdUseCase(FindPatientByIdInputPort findPatientByIdInputPort, DeletePatientByIdUseCase deletePatientByIdUseCase){
        this.findPatientByIdInputPort = findPatientByIdInputPort;
        this.deletePatientByIdUseCase = deletePatientByIdUseCase;
    }

    @Override
    public void delete(String id){
        findPatientByIdInputPort.find(id);
        deletePatientByIdUseCase.delete(id);
    }
}
