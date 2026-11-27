package com.guerra.medview.dto;

import com.guerra.medview.model.Address;

public record AddressResponse(String street, String city, String state) {

    public static AddressResponse from(Address address) {
        if (address == null) {
            return null;
        }
        return new AddressResponse(address.getStreet(), address.getCity(), address.getState());
    }
}
