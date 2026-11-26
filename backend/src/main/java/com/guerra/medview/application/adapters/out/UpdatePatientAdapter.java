package com.guerra.medview.application.adapters.out;

import com.guerra.medview.application.adapters.out.repository.PatientRepository;
import com.guerra.medview.application.adapters.out.repository.entity.PatientEntity;
import com.guerra.medview.application.adapters.out.repository.mapper.PatientPersistenceMapper;
import com.guerra.medview.application.core.domain.Patient;
import com.guerra.medview.application.core.exception.PatientNotFoundException;
import com.guerra.medview.application.ports.out.UpdatePatientOutputPort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UpdatePatientAdapter implements UpdatePatientOutputPort {

    private final PatientRepository patientRepository;
    private final PatientPersistenceMapper patientPersistenceMapper;

    public UpdatePatientAdapter(PatientRepository patientRepository,
                                PatientPersistenceMapper patientPersistenceMapper) {
        this.patientRepository = patientRepository;
        this.patientPersistenceMapper = patientPersistenceMapper;
    }

    @Override
    @Transactional
    public void update(Patient patient) {
        PatientEntity entity = patientRepository.findById(patient.getId())
                .orElseThrow(() -> new PatientNotFoundException(patient.getId()));
        patientPersistenceMapper.updateEntity(entity, patient);
        patientRepository.save(entity);
    }
}
