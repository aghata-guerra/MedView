package com.guerra.medview.application.core.usecase;

import com.guerra.medview.application.core.domain.Patient;
import com.guerra.medview.application.ports.in.FindPatientByIdInputPort;
import com.guerra.medview.application.ports.in.UpdatePatientInputPort;
import com.guerra.medview.application.ports.out.FindAddressByZipCodeOutputPort;
import com.guerra.medview.application.ports.out.UpdatePatientOutputPort;

public class UpdatePatientUseCase implements UpdatePatientInputPort {

    private  final FindPatientByIdInputPort findPatientByIdInputPort;

    private  final FindAddressByZipCodeOutputPort findAddressByZipCodeOutputPort;

    private final UpdatePatientOutputPort updatePatientOutputPort;

    public UpdatePatientUseCase(FindPatientByIdInputPort findPatientByIdInputPort, FindAddressByZipCodeOutputPort findAddressByZipCodeOutputPort, UpdatePatientOutputPort updatePatientOutputPort){
        this.findPatientByIdInputPort = findPatientByIdInputPort;
        this.findAddressByZipCodeOutputPort = findAddressByZipCodeOutputPort;
        this.updatePatientOutputPort = updatePatientOutputPort;

    }

    @Override
    public void update(Patient customer, String zipCode){

        findPatientByIdInputPort.find(customer.getId());
        var address = findAddressByZipCodeOutputPort.find(zipCode);
        customer.setAddress(address);
        updatePatientOutputPort.update(customer);

    }
}
