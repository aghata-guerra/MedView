package com.guerra.medview.application.core.usecase;

import com.guerra.medview.application.core.domain.Patient;
import com.guerra.medview.application.ports.in.InsertPatientInputPort;
import com.guerra.medview.application.ports.out.FindAddressByZipCodeOutputPort;
import com.guerra.medview.application.ports.out.InsertPatientOutputPort;

public class InsertPatientUseCase implements InsertPatientInputPort {

    private  final FindAddressByZipCodeOutputPort findAddressByZipCodeOutputPort;
    private final InsertPatientOutputPort insertPatientOutputPort;

    public InsertPatientUseCase(FindAddressByZipCodeOutputPort findAddressByZipCodeOutputPort, InsertPatientOutputPort insertPatientOutputPort){
        this.findAddressByZipCodeOutputPort = findAddressByZipCodeOutputPort;
        this.insertPatientOutputPort = insertPatientOutputPort;
    }

    @Override
    public void insert(Patient customer, String zipCode){

        var address = findAddressByZipCodeOutputPort.find(zipCode);
        customer.setAddress(address);
        insertPatientOutputPort.insert(customer);
    }
}
