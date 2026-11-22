package com.guerra.hexagonal.application.adapters.out.client.mapper;


import com.guerra.hexagonal.application.adapters.out.client.response.AddressResponse;
import com.guerra.hexagonal.application.core.domain.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressResponseMapper {

    Address toAddress(AddressResponse addressResponse);
}
