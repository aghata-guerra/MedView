package com.guerra.medview.application.core.usecase;

import com.guerra.medview.application.core.domain.Patient;
import com.guerra.medview.application.core.exception.PatientNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UpdatePatientUseCaseTest {

    @Test
    void updatesWhenPatientExists() {
        AtomicBoolean updated = new AtomicBoolean(false);
        UpdatePatientUseCase useCase = new UpdatePatientUseCase(
                id -> new Patient(id, "Maria", null),
                patient -> updated.set(true));

        useCase.update(new Patient(1L, "Maria Souza", null));

        assertTrue(updated.get());
    }

    @Test
    void doesNotUpdateWhenPatientDoesNotExist() {
        AtomicBoolean updated = new AtomicBoolean(false);
        UpdatePatientUseCase useCase = new UpdatePatientUseCase(
                id -> {
                    throw new PatientNotFoundException(id);
                },
                patient -> updated.set(true));

        assertThrows(PatientNotFoundException.class, () -> useCase.update(new Patient(1L, "Maria", null)));
        assertFalse(updated.get());
    }
}
