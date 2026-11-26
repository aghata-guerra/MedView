package com.guerra.medview.application.core.usecase;

import com.guerra.medview.application.core.domain.Patient;
import com.guerra.medview.application.core.exception.PatientNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FindPatientByIdUseCaseTest {

    @Test
    void returnsPatientWhenFound() {
        Patient patient = new Patient(1L, "Maria", null);
        FindPatientByIdUseCase useCase = new FindPatientByIdUseCase(id -> Optional.of(patient));

        assertSame(patient, useCase.find(1L));
    }

    @Test
    void throwsWhenNotFound() {
        FindPatientByIdUseCase useCase = new FindPatientByIdUseCase(id -> Optional.empty());

        assertThrows(PatientNotFoundException.class, () -> useCase.find(99L));
    }
}
