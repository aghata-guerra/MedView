package com.guerra.medview.application.adapters.in.controller.mapper;

import com.guerra.medview.application.adapters.in.controller.request.PatientRequest;
import com.guerra.medview.application.adapters.in.controller.response.PatientResponse;
import com.guerra.medview.application.core.domain.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    @Mapping(target = "id", ignore = true)
    Patient toPatient(PatientRequest patientRequest);

    PatientResponse toPatientResponse(Patient patient);
}
