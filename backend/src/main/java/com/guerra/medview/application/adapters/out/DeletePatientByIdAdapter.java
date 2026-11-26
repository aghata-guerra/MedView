package com.guerra.medview.application.adapters.out;

import com.guerra.medview.application.adapters.out.repository.PatientRepository;
import com.guerra.medview.application.ports.out.DeletePatientByIdOutputPort;
import org.springframework.stereotype.Component;

@Component
public class DeletePatientByIdAdapter implements DeletePatientByIdOutputPort {

    private final PatientRepository patientRepository;

    public DeletePatientByIdAdapter(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public void delete(Long id) {
        patientRepository.deleteById(id);
    }
}
