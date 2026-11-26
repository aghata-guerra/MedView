package com.guerra.medview.application.adapters.out;

import com.guerra.medview.application.adapters.out.repository.PatientRepository;
import com.guerra.medview.application.adapters.out.repository.mapper.PatientPersistenceMapper;
import com.guerra.medview.application.core.domain.Patient;
import com.guerra.medview.application.ports.out.FindPatientByIdOutputPort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class FindPatientByIdAdapter implements FindPatientByIdOutputPort {

    private final PatientRepository patientRepository;
    private final PatientPersistenceMapper patientPersistenceMapper;

    public FindPatientByIdAdapter(PatientRepository patientRepository,
                                  PatientPersistenceMapper patientPersistenceMapper) {
        this.patientRepository = patientRepository;
        this.patientPersistenceMapper = patientPersistenceMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Patient> find(Long id) {
        return patientRepository.findById(id).map(patientPersistenceMapper::toDomain);
    }
}
