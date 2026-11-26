package com.guerra.medview.application.core.usecase;

import com.guerra.medview.application.core.domain.Patient;
import com.guerra.medview.application.core.exception.PatientNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DeletePatientByIdUseCaseTest {

    @Test
    void deletesWhenPatientExists() {
        AtomicBoolean deleted = new AtomicBoolean(false);
        DeletePatientByIdUseCase useCase = new DeletePatientByIdUseCase(
                id -> new Patient(id, "Maria", null),
                id -> deleted.set(true));

        useCase.delete(1L);

        assertTrue(deleted.get());
    }

    @Test
    void doesNotDeleteWhenPatientDoesNotExist() {
        AtomicBoolean deleted = new AtomicBoolean(false);
        DeletePatientByIdUseCase useCase = new DeletePatientByIdUseCase(
                id -> {
                    throw new PatientNotFoundException(id);
                },
                id -> deleted.set(true));

        assertThrows(PatientNotFoundException.class, () -> useCase.delete(1L));
        assertFalse(deleted.get());
    }
}
