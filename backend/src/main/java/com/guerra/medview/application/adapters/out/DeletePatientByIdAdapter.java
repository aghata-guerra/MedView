package com.guerra.medview.application.adapters.out;

import com.guerra.medview.application.adapters.out.repository.PatientRepository;
import com.guerra.medview.application.ports.out.DeletePatientByIdOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeletePatientByIdAdapter implements DeletePatientByIdOutputPort {

    private final PatientRepository patientRepository;

    @Override
    public void delete(Long id) {
        patientRepository.deleteById(id);
    }
}
