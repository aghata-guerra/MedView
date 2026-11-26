package com.guerra.medview.application.adapters.out;

import com.guerra.medview.application.adapters.out.repository.PatientRepository;
import com.guerra.medview.application.adapters.out.repository.entity.PatientEntity;
import com.guerra.medview.application.adapters.out.repository.mapper.PatientPersistenceMapper;
import com.guerra.medview.application.core.domain.Patient;
import com.guerra.medview.application.ports.out.RegisterPatientOutputPort;
import org.springframework.stereotype.Component;

@Component
public class RegisterPatientAdapter implements RegisterPatientOutputPort {

    private final PatientRepository patientRepository;
    private final PatientPersistenceMapper patientPersistenceMapper;

    public RegisterPatientAdapter(PatientRepository patientRepository,
                                  PatientPersistenceMapper patientPersistenceMapper) {
        this.patientRepository = patientRepository;
        this.patientPersistenceMapper = patientPersistenceMapper;
    }

    @Override
    public Patient register(Patient patient) {
        PatientEntity saved = patientRepository.save(patientPersistenceMapper.toEntity(patient));
        return patientPersistenceMapper.toDomain(saved);
    }
}
