package com.guerra.medview.application.adapters.out.repository.mapper;

import com.guerra.medview.application.adapters.out.repository.entity.AddressEntity;
import com.guerra.medview.application.adapters.out.repository.entity.PatientEntity;
import com.guerra.medview.application.core.domain.Address;
import com.guerra.medview.application.core.domain.Patient;
import org.springframework.stereotype.Component;

/**
 * Translates between the domain model (core) and the JPA entities (database).
 */
@Component
public class PatientPersistenceMapper {

    public PatientEntity toEntity(Patient patient) {
        PatientEntity entity = new PatientEntity();
        entity.setId(patient.getId());
        entity.setName(patient.getName());
        entity.setAddress(toAddressEntity(patient.getAddress()));
        return entity;
    }

    public Patient toDomain(PatientEntity entity) {
        return new Patient(entity.getId(), entity.getName(), toAddressDomain(entity.getAddress()));
    }

    /**
     * Copies the new values into an entity that is already stored, keeping the
     * same address row instead of creating a new one on every update.
     */
    public void updateEntity(PatientEntity target, Patient source) {
        target.setName(source.getName());

        Address newAddress = source.getAddress();
        if (newAddress == null) {
            target.setAddress(null);
            return;
        }

        AddressEntity addressEntity = target.getAddress() != null ? target.getAddress() : new AddressEntity();
        addressEntity.setStreet(newAddress.getStreet());
        addressEntity.setCity(newAddress.getCity());
        addressEntity.setState(newAddress.getState());
        target.setAddress(addressEntity);
    }

    private AddressEntity toAddressEntity(Address address) {
        if (address == null) {
            return null;
        }
        AddressEntity entity = new AddressEntity();
        entity.setStreet(address.getStreet());
        entity.setCity(address.getCity());
        entity.setState(address.getState());
        return entity;
    }

    private Address toAddressDomain(AddressEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Address(entity.getStreet(), entity.getCity(), entity.getState());
    }
}
