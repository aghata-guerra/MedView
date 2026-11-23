package com.guerra.medview.application.adapters.in.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddressRequest {

    @NotBlank
    private String street;
    @NotBlank
    private String city;
    @NotBlank
    private String state;
}
