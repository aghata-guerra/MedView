package com.guerra.medview.application.ports.out;

import com.guerra.medview.application.core.domain.Patient;

public interface InsertPatientOutputPort {

    void insert(Patient patient);
}
