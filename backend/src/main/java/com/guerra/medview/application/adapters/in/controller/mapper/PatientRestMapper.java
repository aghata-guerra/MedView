package com.guerra.medview.application.adapters.in.controller.mapper;

import com.guerra.medview.application.adapters.in.controller.request.AddressRequest;
import com.guerra.medview.application.adapters.in.controller.request.PatientRequest;
import com.guerra.medview.application.adapters.in.controller.response.AddressResponse;
import com.guerra.medview.application.adapters.in.controller.response.PatientResponse;
import com.guerra.medview.application.core.domain.Address;
import com.guerra.medview.application.core.domain.Patient;
import org.springframework.stereotype.Component;

/**
 * Translates between the REST DTOs (request/response) and the domain model (core).
 */
@Component
public class PatientRestMapper {

    public Patient toDomain(PatientRequest request) {
        AddressRequest address = request.address();
        return new Patient(null, request.name(),
                new Address(address.street(), address.city(), address.state()));
    }

    public PatientResponse toResponse(Patient patient) {
        Address address = patient.getAddress();
        AddressResponse addressResponse = address == null ? null
                : new AddressResponse(address.getStreet(), address.getCity(), address.getState());
        return new PatientResponse(patient.getId(), patient.getName(), addressResponse);
    }
}
