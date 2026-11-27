package com.guerra.medview.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record RegisterDoctorRequest(
        @Schema(example = "Dr. Carlos Lima")
        @NotBlank String name,

        @Schema(example = "carlos.lima@hospital.com")
        @NotBlank @Email String email,

        @Schema(example = "529.982.247-25")
        @NotBlank @CPF String cpf,

        @Schema(example = "senha-forte-123")
        @NotBlank @Size(min = 8, max = 72) String password
) {
}
