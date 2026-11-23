package com.guerra.medview.application.ports.in;

import com.guerra.medview.application.core.domain.Patient;

public interface UpdatePatientInputPort {

    void update(Patient patient);
}
