package com.guerra.medview.application.ports.in;

import com.guerra.medview.application.core.domain.Patient;

public interface FindPatientByIdInputPort {

    Patient find(String id);
}
