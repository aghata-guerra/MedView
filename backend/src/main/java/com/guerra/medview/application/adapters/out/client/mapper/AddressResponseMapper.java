package com.guerra.medview.application.adapters.out.client.mapper;


import com.guerra.medview.application.adapters.out.client.response.AddressResponse;
import com.guerra.medview.application.core.domain.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressResponseMapper {

    Address toAddress(AddressResponse addressResponse);
}
