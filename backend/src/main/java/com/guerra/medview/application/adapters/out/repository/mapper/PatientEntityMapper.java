package com.guerra.medview.application.adapters.out.repository.mapper;

import com.guerra.medview.application.adapters.out.repository.entity.PatientEntity;
import com.guerra.medview.application.core.domain.Patient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PatientEntityMapper {

    PatientEntity toPatientEntity(Patient patient);

    Patient toPatient(PatientEntity patientEntity);
}
