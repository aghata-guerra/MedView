package com.guerra.medview.application.ports.out;

import com.guerra.medview.application.core.domain.Patient;

public interface UpdatePatientOutputPort {

    void update(Patient patient);
}
