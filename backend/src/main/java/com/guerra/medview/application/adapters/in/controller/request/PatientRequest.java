package com.guerra.medview.application.adapters.in.controller.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PatientRequest {

    @NotBlank
    private String name;

    @Valid
    @NotNull
    private AddressRequest address;
}
