package com.guerra.medview.application.adapters.in.controller.response;

import lombok.Data;

@Data
public class PatientResponse {

    private Long id;
    private String name;
    private AddressResponse address;
}
