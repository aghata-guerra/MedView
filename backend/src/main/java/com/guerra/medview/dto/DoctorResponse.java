package com.guerra.medview.dto;

import com.guerra.medview.model.Doctor;

public record DoctorResponse(Long id, String name, String email, String cpf) {

    public static DoctorResponse from(Doctor doctor) {
        return new DoctorResponse(doctor.getId(), doctor.getName(), doctor.getEmail(), doctor.getCpf());
    }
}
