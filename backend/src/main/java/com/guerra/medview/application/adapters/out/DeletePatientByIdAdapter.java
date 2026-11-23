package com.guerra.medview.application.adapters.out;

import com.guerra.medview.application.adapters.out.repository.PatientRepository;
import com.guerra.medview.application.ports.out.DeletePatientByIdOutputPort;
import org.springframework.beans.factory.annotation.Autowired;

public class DeletePatientByIdAdapter implements DeletePatientByIdOutputPort {

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public void delete(String id){
        patientRepository.deleteById(id);
    }
}
