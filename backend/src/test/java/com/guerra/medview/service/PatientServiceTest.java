package com.guerra.medview.service;

import com.guerra.medview.dto.AddressRequest;
import com.guerra.medview.dto.PatientRequest;
import com.guerra.medview.exception.PatientNotFoundException;
import com.guerra.medview.model.Address;
import com.guerra.medview.model.Patient;
import com.guerra.medview.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;

    private static PatientRequest request(String name, String city) {
        return new PatientRequest(name, new AddressRequest("Rua A", city, "PE"));
    }

    @Test
    void registerSavesPatientWithAddress() {
        when(patientRepository.save(any(Patient.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Patient saved = patientService.register(request("Maria", "Recife"));

        assertEquals("Maria", saved.getName());
        assertEquals("Recife", saved.getAddress().getCity());
        verify(patientRepository).save(saved);
    }

    @Test
    void findByIdReturnsPatient() {
        Patient patient = new Patient();
        patient.setId(1L);
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));

        assertSame(patient, patientService.findById(1L));
    }

    @Test
    void findByIdThrowsWhenMissing() {
        when(patientRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(PatientNotFoundException.class, () -> patientService.findById(99L));
    }

    @Test
    void updateChangesFieldsAndKeepsTheSameAddressRow() {
        Address address = new Address();
        address.setCity("Recife");
        Patient existing = new Patient();
        existing.setName("Maria");
        existing.setAddress(address);
        when(patientRepository.findById(1L)).thenReturn(Optional.of(existing));

        patientService.update(1L, request("Maria Souza", "Olinda"));

        assertEquals("Maria Souza", existing.getName());
        assertSame(address, existing.getAddress());
        assertEquals("Olinda", address.getCity());
        verify(patientRepository).save(existing);
    }

    @Test
    void updateThrowsWhenMissing() {
        when(patientRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(PatientNotFoundException.class, () -> patientService.update(99L, request("Maria", "Recife")));
        verify(patientRepository, never()).save(any(Patient.class));
    }

    @Test
    void deleteRemovesPatient() {
        Patient patient = new Patient();
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));

        patientService.delete(1L);

        verify(patientRepository).delete(patient);
    }

    @Test
    void deleteThrowsWhenMissing() {
        when(patientRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(PatientNotFoundException.class, () -> patientService.delete(99L));
        verify(patientRepository, never()).delete(any(Patient.class));
    }
}
