package com.guerra.medview.application.adapters.out;

import com.guerra.medview.application.adapters.out.repository.PatientRepository;
import com.guerra.medview.application.adapters.out.repository.mapper.PatientEntityMapper;
import com.guerra.medview.application.core.domain.Patient;
import com.guerra.medview.application.ports.out.UpdatePatientOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdatePatientAdapter implements UpdatePatientOutputPort {

    private final PatientRepository patientRepository;
    private final PatientEntityMapper patientEntityMapper;

    @Override
    public void update(Patient patient) {
        patientRepository.save(patientEntityMapper.toPatientEntity(patient));
    }
}
