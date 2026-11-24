package com.guerra.medview.application.adapters.out;

import com.guerra.medview.application.adapters.out.repository.PatientRepository;
import com.guerra.medview.application.adapters.out.repository.mapper.PatientEntityMapper;
import com.guerra.medview.application.core.domain.Patient;
import com.guerra.medview.application.ports.out.InsertPatientOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InsertPatientAdapter implements InsertPatientOutputPort {

    private final PatientRepository patientRepository;
    private final PatientEntityMapper patientEntityMapper;

    @Override
    public void insert(Patient patient) {
        patientRepository.save(patientEntityMapper.toPatientEntity(patient));
    }
}
