package com.guerra.medview.controller;

import com.guerra.medview.dto.PatientRequest;
import com.guerra.medview.dto.PatientResponse;
import com.guerra.medview.model.Patient;
import com.guerra.medview.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/patients")
@Tag(name = "Patients", description = "Patient registration and management")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping
    @Operation(summary = "Register a new patient")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Patient created"),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content)
    })
    public ResponseEntity<PatientResponse> register(@Valid @RequestBody PatientRequest request) {
        Patient saved = patientService.register(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();
        return ResponseEntity.created(location).body(PatientResponse.from(saved));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find a patient by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Patient found"),
            @ApiResponse(responseCode = "404", description = "Patient not found", content = @Content)
    })
    public ResponseEntity<PatientResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(PatientResponse.from(patientService.findById(id)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a patient")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Patient updated"),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content),
            @ApiResponse(responseCode = "404", description = "Patient not found", content = @Content)
    })
    public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody PatientRequest request) {
        patientService.update(id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a patient")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Patient deleted"),
            @ApiResponse(responseCode = "404", description = "Patient not found", content = @Content)
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        patientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
