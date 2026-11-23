package com.guerra.medview.application.ports.out;

import com.guerra.medview.application.core.domain.Address;

public interface FindAddressByZipCodeOutputPort {

    Address find(String zipCode);
}
