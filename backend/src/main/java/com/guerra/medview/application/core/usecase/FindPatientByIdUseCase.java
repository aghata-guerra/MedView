package com.guerra.medview.application.core.usecase;

import com.guerra.medview.application.core.domain.Patient;
import com.guerra.medview.application.ports.in.FindPatientByIdInputPort;
import com.guerra.medview.application.ports.out.FindPatientByIdOutputPort;

public class FindPatientByIdUseCase implements FindPatientByIdInputPort {


    private final FindPatientByIdOutputPort findPatientByIdOutputPort;

    public FindPatientByIdUseCase(FindPatientByIdOutputPort findPatientByIdOutputPort){
        this.findPatientByIdOutputPort = findPatientByIdOutputPort;
    }
    public Patient find(String id){
        return findPatientByIdOutputPort.find(id).orElseThrow(() -> new RuntimeException("Customer not found!"));
    }
}
