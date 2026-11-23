package com.guerra.medview.application.ports.out;

import com.guerra.medview.application.core.domain.Patient;

import java.util.Optional;

public interface FindPatientByIdOutputPort {

    Optional<Patient> find(String id);
}
