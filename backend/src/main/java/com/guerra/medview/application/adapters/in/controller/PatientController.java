package com.guerra.medview.application.adapters.in.controller;

import com.guerra.medview.application.adapters.in.controller.mapper.PatientMapper;
import com.guerra.medview.application.adapters.in.controller.request.PatientRequest;
import com.guerra.medview.application.adapters.in.controller.response.PatientResponse;
import com.guerra.medview.application.core.domain.Patient;
import com.guerra.medview.application.ports.in.DeletePatientByIdInputPort;
import com.guerra.medview.application.ports.in.FindPatientByIdInputPort;
import com.guerra.medview.application.ports.in.InsertPatientInputPort;
import com.guerra.medview.application.ports.in.UpdatePatientInputPort;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
public class PatientController {

    private final InsertPatientInputPort insertPatientInputPort;
    private final FindPatientByIdInputPort findPatientByIdInputPort;
    private final UpdatePatientInputPort updatePatientInputPort;
    private final DeletePatientByIdInputPort deletePatientByIdInputPort;
    private final PatientMapper patientMapper;

    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody PatientRequest patientRequest) {
        Patient patient = patientMapper.toPatient(patientRequest);
        insertPatientInputPort.insert(patient);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponse> findById(@PathVariable Long id) {
        Patient patient = findPatientByIdInputPort.find(id);
        return ResponseEntity.ok(patientMapper.toPatientResponse(patient));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody PatientRequest patientRequest) {
        Patient patient = patientMapper.toPatient(patientRequest);
        patient.setId(id);
        updatePatientInputPort.update(patient);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deletePatientByIdInputPort.delete(id);
        return ResponseEntity.noContent().build();
    }
}