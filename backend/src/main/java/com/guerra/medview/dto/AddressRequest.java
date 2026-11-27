package com.guerra.medview.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record AddressRequest(
        @Schema(example = "Rua da Aurora, 100")
        @NotBlank String street,

        @Schema(example = "Recife")
        @NotBlank String city,

        @Schema(example = "PE")
        @NotBlank String state
) {
}
