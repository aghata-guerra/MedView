package com.guerra.medview.dto;

import com.guerra.medview.model.Patient;

public record PatientResponse(Long id, String name, AddressResponse address) {

    public static PatientResponse from(Patient patient) {
        return new PatientResponse(patient.getId(), patient.getName(), AddressResponse.from(patient.getAddress()));
    }
}
