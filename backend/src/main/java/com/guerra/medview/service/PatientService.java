package com.guerra.medview.service;

import com.guerra.medview.dto.PatientRequest;
import com.guerra.medview.exception.PatientNotFoundException;
import com.guerra.medview.model.Address;
import com.guerra.medview.model.Patient;
import com.guerra.medview.repository.PatientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Transactional
    public Patient register(PatientRequest request) {
        Patient patient = new Patient();
        copyFields(patient, request);
        return patientRepository.save(patient);
    }

    @Transactional(readOnly = true)
    public Patient findById(Long id) {
        return getOrThrow(id);
    }

    @Transactional
    public void update(Long id, PatientRequest request) {
        Patient patient = getOrThrow(id);
        copyFields(patient, request);
        patientRepository.save(patient);
    }

    @Transactional
    public void delete(Long id) {
        Patient patient = getOrThrow(id);
        patientRepository.delete(patient);
    }

    private Patient getOrThrow(Long id) {
        return patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException(id));
    }

    /**
     * Copies the request into the entity. An existing address row is reused, so an
     * update does not leave orphan rows in the addresses table.
     */
    private void copyFields(Patient patient, PatientRequest request) {
        patient.setName(request.name());

        Address address = patient.getAddress() != null ? patient.getAddress() : new Address();
        address.setStreet(request.address().street());
        address.setCity(request.address().city());
        address.setState(request.address().state());
        patient.setAddress(address);
    }
}
