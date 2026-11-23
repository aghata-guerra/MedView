package com.guerra.medview.application.adapters.out;

import com.guerra.medview.application.adapters.out.repository.PatientRepository;
import com.guerra.medview.application.adapters.out.repository.mapper.PatientEntityMapper;
import com.guerra.medview.application.core.domain.Patient;
import com.guerra.medview.application.ports.out.FindPatientByIdOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FindPatientByIdAdapter implements FindPatientByIdOutputPort {

    private final PatientRepository patientRepository;
    private final PatientEntityMapper patientEntityMapper;

    @Override
    public Optional<Patient> find(Long id) {
        return patientRepository.findById(id).map(patientEntityMapper::toPatient);
    }
}
