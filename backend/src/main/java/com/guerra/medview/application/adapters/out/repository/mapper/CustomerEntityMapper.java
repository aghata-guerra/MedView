package com.guerra.medview.application.adapters.out.repository.mapper;

import com.guerra.medview.application.adapters.out.repository.entity.PatientEntity;
import com.guerra.medview.application.core.domain.Patient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerEntityMapper {

    PatientEntity toCustomerEntity(Patient customer);

    Patient toCustomer(PatientEntity patientEntity);
}
