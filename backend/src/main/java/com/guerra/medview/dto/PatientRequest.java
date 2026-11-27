package com.guerra.medview.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PatientRequest(
        @Schema(example = "Maria Silva")
        @NotBlank String name,

        @Valid @NotNull AddressRequest address
) {
}
